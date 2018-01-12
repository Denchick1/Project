package com.example.root.metr.screens.auth.presentation;

import com.example.root.metr.screens.auth.business.AuthInteractor;
import com.example.root.metr.screens.auth.interfaces.ISendSmsActivity;
import com.example.root.metr.models.business_model.RegistrationModel;
import com.example.root.metr.utils.ExeptionManager;
import com.example.root.metr.utils.ShedulerProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

@RunWith(PowerMockRunner.class)
public class SendSmsActivityPresenterTest {

    @Mock
    AuthInteractor authInteractor;
    @Mock
    ISendSmsActivity iLoginActivity;
    @Mock
    ShedulerProvider shedulerProvider;
    @Mock
    ExeptionManager exeptionManager;
    @Mock
    RegistrationModel registrationModel;

    SendSmsActivityPresenter sendSmsActivityPresenter;
    TestScheduler testScheduler;
    @Mock
    Throwable throwableErrorSms;

   @Before
   public void setup() throws IOException {
       MockitoAnnotations.initMocks(this);
       throwableErrorSms = new Throwable();
       testScheduler=new TestScheduler();
       sendSmsActivityPresenter=new SendSmsActivityPresenter(authInteractor,shedulerProvider,exeptionManager);
       sendSmsActivityPresenter.attachView(iLoginActivity);
       Mockito.when(exeptionManager.getfailure(throwableErrorSms)).thenReturn(4240);
       Mockito.when(authInteractor.sendSms("s", "s")).thenReturn(Completable.error(throwableErrorSms));
       Mockito.when(authInteractor.sendSms("1", "1")).thenReturn(Completable.complete());
       Mockito.when(shedulerProvider.computation()).thenReturn(testScheduler);
       Mockito.when(shedulerProvider.io()).thenReturn(testScheduler);
       Mockito.when(shedulerProvider.main()).thenReturn(testScheduler);
   }

    @Test
    public void sendSmsPassword() throws Exception {
        Mockito.when(registrationModel.getCode()).thenReturn("1");
        Mockito.when(registrationModel.getUuid()).thenReturn("1");
        sendSmsActivityPresenter.sendSmsPassword(registrationModel,"1");
        testScheduler.advanceTimeTo(10, TimeUnit.SECONDS);
        Mockito.verify(iLoginActivity, times(1)).startMainActivity();

    }

    @Test
    public void startChronometr() {
        Mockito.when(authInteractor.startChronometer(10, testScheduler)).thenReturn(Observable.intervalRange(1, 10, 0, 1, TimeUnit.SECONDS, testScheduler)
                .map(aLong -> new SimpleDateFormat("mm:ss").format((10 - aLong) * 1000)));
        sendSmsActivityPresenter.startChronometr(10);
        testScheduler.advanceTimeTo(11, TimeUnit.SECONDS);
        Mockito.verify(iLoginActivity, times(1)).disableClickSendInfo();
        Mockito.verify(iLoginActivity, times(1)).setTextInfo(anyInt());
        Mockito.verify(iLoginActivity).onVisibleSendInfo();
        Mockito.verify(iLoginActivity, times(11)).setTextChronometr(anyString());
    }

    @Test
    public void sendSmsError() {
        Mockito.when(registrationModel.getCode()).thenReturn("s");
        Mockito.when(registrationModel.getUuid()).thenReturn("s");
        sendSmsActivityPresenter.sendSmsPassword(registrationModel,"s");
        testScheduler.advanceTimeTo(1, TimeUnit.SECONDS);
        Mockito.verify(iLoginActivity).setTextInfo(anyInt());
    }

}