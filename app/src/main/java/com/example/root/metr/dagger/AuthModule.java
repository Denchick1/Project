package com.example.root.metr.dagger;

import com.example.root.metr.ScopeD;
import com.example.root.metr.screens.auth.business.AuthInteractor;
import com.example.root.metr.data.IRepositoryAuth;

import dagger.Module;
import dagger.Provides;

@Module()
public class AuthModule {

    @Provides
    @ScopeD
    public AuthInteractor getAuthInteractor(IRepositoryAuth iRepositoryAuth){
        return new AuthInteractor();
    }

}
