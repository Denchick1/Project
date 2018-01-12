package com.example.root.metr.screens.garage.presentation;

import com.example.root.metr.data.IUserRepository;
import com.example.root.metr.models.DTO.User;
import com.example.root.metr.models.DTO.Wrecker;
import com.example.root.metr.models.view_model.Full_car;
import com.example.root.metr.screens.garage.business.GarageInteractor;
import com.example.root.metr.screens.garage.interfaces.GarageView;
import com.example.root.metr.utils.ExeptionManager;
import com.example.root.metr.utils.ShedulerProvider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;

public class GaragePresenterTest {

    GaragePresenter garagePresenter;

    private final int PHISIK_USER=0;
    private final int JURIDIK_USER=0;

    @Mock
    ShedulerProvider shedulerProvider;

    ExeptionManager exeptionManager;
    @Mock
    IUserRepository iUserRepository;
    @Mock
    GarageInteractor garageInteractor;
    @Mock
    GarageView garageView;
    @Mock
    Full_car full_car;
    TestScheduler testScheduler;
    Exception exception;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        exeptionManager = new ExeptionManager();
        garagePresenter = new GaragePresenter(iUserRepository, shedulerProvider, exeptionManager, garageInteractor);

        testScheduler = new TestScheduler();
        garagePresenter.attachView(garageView);

        Mockito.when(shedulerProvider.computation()).thenReturn(testScheduler);
        Mockito.when(shedulerProvider.io()).thenReturn(testScheduler);
        Mockito.when(shedulerProvider.main()).thenReturn(testScheduler);

        exception = new Exception("failed to connect to");
    }

    @Test
    public void errorConnectOnLoadListWreckers() {
        Mockito.when(garageInteractor.takeWreckersList()).thenReturn(Observable.error(exception));
        garagePresenter.onLoadListWreckers();
        testScheduler.advanceTimeBy(3, TimeUnit.SECONDS);
        Mockito.verify(garageView).showConnectError();
    }

    @Test
    public void successOnLoadListWreckersPhisikUserWithEmptyWreckers() {
        List<Wrecker> list = new ArrayList<>();
        initTestLoadListWrecker(PHISIK_USER,list);
        Mockito.verify(garageView).showFab();
    }

    @Test
    public void successOnLoadListWreckersPhisikUserWithNotEmptyWreckers() {
        Wrecker wrecker = Mockito.mock(Wrecker.class);
        List<Wrecker> list = new ArrayList<>();
        list.add(wrecker);
        initTestLoadListWrecker(PHISIK_USER,list);
        Mockito.verify(garageView).hideFab();
    }

    @Test
    public void successOnLoadListWreckersJuridicUser() {
        List<Wrecker> list =  new ArrayList<>();
        initTestLoadListWrecker(JURIDIK_USER,list);
        Mockito.verify(garageView).setupRecycler(list);

    }

    private void initTestLoadListWrecker(int typeUser, List<Wrecker> list){
        User user = new User();
        user.setType_user(typeUser);
        Mockito.when(iUserRepository.getUser()).thenReturn(user);
        Mockito.when(garageInteractor.takeWreckersList()).thenReturn(Observable.just(list));
        Mockito.when(iUserRepository.getUser()).thenReturn(user);
        garagePresenter.onLoadListWreckers();
        testScheduler.advanceTimeBy(3, TimeUnit.SECONDS);
    }


}