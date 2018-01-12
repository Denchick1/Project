package com.example.root.metr.screens.garage.presentation;

import com.example.root.metr.models.view_model.Tarif;
import com.example.root.metr.root.App;
import com.example.root.metr.screens.garage.interfaces.AddTarifView;
import com.example.root.metr.utils.ShedulerProvider;

import javax.inject.Inject;

public class AddTarifPresenter {

    @Inject
    ShedulerProvider shedulerProvider;

    private AddTarifView addTarifView;

    public AddTarifPresenter() {
        App.INSTANCE.getComponent().AddTarifPresenter(this);
    }

    public void attachView(AddTarifView addTarifView) {
        this.addTarifView = addTarifView;
    }

    public void addTarif(Tarif tarif){
        //show / hide progress
        addTarifView.successAddTarif();
    }

}