package com.example.root.metr.dagger;

import com.example.root.metr.ScopeD;
import com.example.root.metr.screens.garage.business.GarageInteractor;

import dagger.Module;
import dagger.Provides;

@Module
public class CarModule {

    @Provides
    @ScopeD
    public GarageInteractor garageInteractor() {
        return new GarageInteractor();
    }
}
