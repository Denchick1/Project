package com.example.root.metr.screens.auth.business;

import com.example.root.metr.data.IRepositoryAuth;
import com.example.root.metr.models.business_model.RegistrationModel;
import com.example.root.metr.throwable.ThrowableManager;
import com.example.root.metr.utils.EditTextValidator;
import com.example.root.metr.utils.TimeUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.mockito.ArgumentMatchers.anyString;


@RunWith(PowerMockRunner.class)
public class AuthInteractorTest {

    AuthInteractor authInteractor;

    SimpleDateFormat dateTimeUtils;

    @Mock
    RegistrationModel registrationModel;

    @Mock
    IRepositoryAuth iRepositoryAuth;

    ThrowableManager throwableManager;

    @Before
    public void setUp() throws Exception {
        throwableManager=new ThrowableManager();
        dateTimeUtils = new SimpleDateFormat("mm:ss");
        authInteractor = new AuthInteractor(iRepositoryAuth,new EditTextValidator(),throwableManager,new TimeUtil());
        Mockito.when(iRepositoryAuth.sendPhone("")).thenReturn(Observable.error(new Throwable("error")));
        Mockito.when(iRepositoryAuth.sendPhone("79111111111")).thenReturn(Observable.just(registrationModel));
        Mockito.when(iRepositoryAuth.registration(anyString(),anyString(),anyString())).thenReturn(Observable.just(registrationModel));
    }

    @Test
    public void startChronometer() {
        io.reactivex.schedulers.TestScheduler testScheduler = new io.reactivex.schedulers.TestScheduler();
        TestObserver<String> testObserver = authInteractor.startChronometer(4, testScheduler).test();
        testScheduler.advanceTimeBy(10, TimeUnit.SECONDS);
        testObserver.assertSubscribed();
        //FIXME делится на два, после исправления сервера сделать
        String[] values={"00:03","00:02","00:01","00:00"};
        testObserver.assertValues(values);
        testObserver.assertComplete();
    }

    @Test
    public void startChronometerRevertValue(){
        io.reactivex.schedulers.TestScheduler testScheduler = new io.reactivex.schedulers.TestScheduler();
        TestObserver<String> testObserver = authInteractor.startChronometer(-4, testScheduler).test();
        testScheduler.advanceTimeBy(10, TimeUnit.SECONDS);
        //FIXME делится на два, после исправления сервера сделать
        String[] values={"00:00"};
        testObserver.assertValues(values);
        testObserver.assertComplete();
    }

    @Test
    public void startRegistrationError() {
        io.reactivex.schedulers.TestScheduler testScheduler = new io.reactivex.schedulers.TestScheduler();

        TestObserver<RegistrationModel> testObserver = getTestObserver(testScheduler,"den", "79111111111", "");
        testScheduler.advanceTimeBy(3,TimeUnit.SECONDS);
        testObserver.assertError(throwableManager.getErrorValideEmailExeption());

        testObserver = getTestObserver(testScheduler,"", "79111111111", "staalk195@gmail.com");
        testScheduler.advanceTimeBy(3,TimeUnit.SECONDS);
        testObserver.assertError(throwableManager.getErrorValidateNameExeption());

        testObserver=getTestObserver(testScheduler,"den den den den", "79111111111", "staalk195@gmail.com");
        testScheduler.advanceTimeBy(3,TimeUnit.SECONDS);
        testObserver.assertError(throwableManager.getErrorValidateNameExeption());

        testObserver=getTestObserver(testScheduler," den den den", "79111111111", "staalk195@gmail.com");
        testScheduler.advanceTimeBy(3,TimeUnit.SECONDS);
        testObserver.assertError(throwableManager.getErrorValidateNameExeption());

        testObserver=getTestObserver(testScheduler," den den", "79111111111", "staalk195@gmail.com");
        testScheduler.advanceTimeBy(3,TimeUnit.SECONDS);
        testObserver.assertError(throwableManager.getErrorValidateNameExeption());
    }

    @Test
    public void successRegistration(){
        io.reactivex.schedulers.TestScheduler testScheduler = new io.reactivex.schedulers.TestScheduler();

        TestObserver<RegistrationModel> testObserver=getTestObserver(testScheduler,"den", "79111111111", "staalk195@gmail.com");
        testScheduler.advanceTimeBy(3,TimeUnit.SECONDS);
        testObserver.assertValue(registrationModel);

        testObserver=getTestObserver(testScheduler,"den den den", "79111111111", "staalk195@gmail.com");
        testScheduler.advanceTimeBy(3,TimeUnit.SECONDS);
        testObserver.assertValue(registrationModel);

    }

    private TestObserver<RegistrationModel> getTestObserver(io.reactivex.schedulers.TestScheduler testScheduler,String name,String phone,String email){
        return  authInteractor.startRegistration(name, phone, email)
                .observeOn(testScheduler)
                .test();
    }



}