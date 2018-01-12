package com.example.root.metr.screens.auth.presentation;


import android.text.TextUtils;

import com.example.root.metr.models.business_model.RegistrationModel;
import com.example.root.metr.screens.auth.business.AuthInteractor;
import com.example.root.metr.screens.auth.interfaces.ISendPhoneActivity;
import com.example.root.metr.utils.EditTextValidator;
import com.example.root.metr.utils.ExeptionManager;
import com.example.root.metr.utils.ShedulerProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.TestScheduler;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;


@RunWith(PowerMockRunner.class)
@PrepareForTest({TextUtils.class, AndroidSchedulers.class})
public class AuthPresenterTest {

    @Mock
    AuthInteractor authInteractor;
    @Mock
    ISendPhoneActivity iLoginActivity;
    @Mock
    ShedulerProvider shedulerProvider;
    @Mock
    RegistrationModel registrationModel;

    ExeptionManager exeptionManager;


    EditTextValidator editTextValidator;
    SendPhoneActivityPresenter loginActivityPresenter;
    TestScheduler testScheduler;
    TextUtils textUtils = PowerMockito.mock(TextUtils.class);
    String phone = "+7 (911) 111-11-11";
    String phoneConvert = "79111111111";


    @Before
    public void initobject() {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(TextUtils.class);
        PowerMockito.mockStatic(AndroidSchedulers.class);
        exeptionManager=new ExeptionManager();
        testScheduler = new TestScheduler();
        editTextValidator = new EditTextValidator(textUtils);
        loginActivityPresenter = new SendPhoneActivityPresenter(authInteractor, new EditTextValidator(), shedulerProvider, exeptionManager);
        loginActivityPresenter.attachView(iLoginActivity);

        RxJavaPlugins.setComputationSchedulerHandler(ignore -> testScheduler);
    }

    @Before
    public void initMethod() throws IOException {
        PowerMockito.when(textUtils.isEmpty("")).thenReturn(true);
        PowerMockito.when(textUtils.isEmpty(anyString())).thenReturn(false);
        Mockito.when(shedulerProvider.computation()).thenReturn(testScheduler);
        Mockito.when(shedulerProvider.io()).thenReturn(testScheduler);
        Mockito.when(shedulerProvider.main()).thenReturn(testScheduler);
        Mockito.when(authInteractor.sendPhone(anyString())).thenReturn(Observable.just(registrationModel));


    }

    @After
    public void after() {
        RxJavaPlugins.setComputationSchedulerHandler(null);
    }

    @Test
    public void errorEditTextValidation() {
        assertEquals(false, editTextValidator.phoneValidateNew("+7"));
        assertEquals(true, editTextValidator.phoneValidateNew(phone));
        assertEquals(phoneConvert, editTextValidator.phoneConvertation("+7 (911) 111-11-11"));
    }


    @Test
    public void sendSuccessPhone() {
        loginActivityPresenter.onSendPhone(phone);
        testScheduler.advanceTimeBy(10, TimeUnit.SECONDS);
        Mockito.verify(iLoginActivity, times(2)).initVisibleButtonNext(anyInt());
        Mockito.verify(iLoginActivity, times(2)).initVisivileProgressBar(anyInt());
    }





}
