package com.example.root.metr.screens.auth.presentation;

import com.example.root.metr.models.business_model.RegistrationModel;
import com.example.root.metr.screens.auth.business.AuthInteractor;
import com.example.root.metr.screens.auth.interfaces.IRegistrationActivity;
import com.example.root.metr.throwable.ThrowableManager;
import com.example.root.metr.utils.ShedulerProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;


@RunWith(PowerMockRunner.class)
public class RegistrationActivityPresenterTest {

    RegistrationActivityPresenter registrationActivityPresenter;

    @Mock
    AuthInteractor authInteractor;

    @Mock
    ShedulerProvider shedulerProvider;

    @Mock
    IRegistrationActivity iRegistrationActivity;

    @Mock
    RegistrationModel registrationModel;

    TestScheduler testScheduler;

    ThrowableManager throwableManager;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        registrationActivityPresenter = new RegistrationActivityPresenter(authInteractor, shedulerProvider, iRegistrationActivity);
        registrationActivityPresenter.attachView(iRegistrationActivity);

        testScheduler = new TestScheduler();
        throwableManager = new ThrowableManager();

        Mockito.when(shedulerProvider.computation()).thenReturn(testScheduler);
        Mockito.when(shedulerProvider.io()).thenReturn(testScheduler);
        Mockito.when(shedulerProvider.main()).thenReturn(testScheduler);

        Mockito.when(authInteractor.startRegistration("", "+7(111)111-11-11", "aaa@gmail.com"))
                .thenReturn(Observable.error(throwableManager.getErrorValidateNameExeption()));
        Mockito.when(authInteractor.startRegistration("aaa", "", "aaa@gmail.com"))
                .thenReturn(Observable.error(throwableManager.getErrorValidePhoneExeption()));
        Mockito.when(authInteractor.startRegistration("aaa", "+7(111)111-11-11", ""))
                .thenReturn(Observable.error(throwableManager.getErrorValideEmailExeption()));
        Mockito.when(authInteractor.startRegistration("aaa", "+7(111)111-11-11", "aaa@gmail.com"))
                .thenReturn(Observable.just(registrationModel));


    }


    @Test
    public void onRegistrationError() throws Exception {
        registrationActivityPresenter.onRegistration("", "+7(111)111-11-11", "aaa@gmail.com");
        Mockito.verify(iRegistrationActivity).showProgressBar();
        testScheduler.advanceTimeBy(10, TimeUnit.SECONDS);
        Mockito.verify(iRegistrationActivity).showErrorValidName();
        Mockito.verify(iRegistrationActivity).hideProgressBar();

        registrationActivityPresenter.onRegistration("aaa", "", "aaa@gmail.com");
        Mockito.verify(iRegistrationActivity,Mockito.times(2)).showProgressBar();
        testScheduler.advanceTimeBy(10, TimeUnit.SECONDS);
        Mockito.verify(iRegistrationActivity).showErrorValidPhone();
        Mockito.verify(iRegistrationActivity,Mockito.times(2)).hideProgressBar();

        registrationActivityPresenter.onRegistration("aaa", "+7(111)111-11-11", "");
        Mockito.verify(iRegistrationActivity,Mockito.times(3)).showProgressBar();
        testScheduler.advanceTimeBy(10, TimeUnit.SECONDS);
        Mockito.verify(iRegistrationActivity).showErrorValidEmail();
        Mockito.verify(iRegistrationActivity,Mockito.times(3)).hideProgressBar();

    }

    @Test
    public void onRegistrationSucces() throws Exception {
        registrationActivityPresenter.onRegistration("aaa", "+7(111)111-11-11", "aaa@gmail.com");
        Mockito.verify(iRegistrationActivity).showProgressBar();
        testScheduler.advanceTimeBy(10, TimeUnit.SECONDS);
        Mockito.verify(iRegistrationActivity).successRegistration(registrationModel);
        Mockito.verify(iRegistrationActivity).hideProgressBar();
    }

}