package com.example.root.metr.dagger;

import com.example.root.metr.data.IRepositoryAuth;
import com.example.root.metr.data.IRepositoryCar;
import com.example.root.metr.data.IRepositoryWrecker;
import com.example.root.metr.data.IUserRepository;
import com.example.root.metr.data.repository.AuthRepository;
import com.example.root.metr.data.repository.CarRepository;
import com.example.root.metr.data.repository.UserRepository;
import com.example.root.metr.data.repository.WreckerRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module()
public class RepositoryModule {

    @Provides
    @Singleton
    public IRepositoryAuth iRepositoryAuth(){
        return new AuthRepository();
    }

    @Provides
    @Singleton
    public IRepositoryCar iRepositoryCar(){return new CarRepository();}

    @Provides
    @Singleton
    public IUserRepository iUserRepository(){return new UserRepository();}

    @Provides
    @Singleton
    public IRepositoryWrecker iRepositoryWrecker(){return new WreckerRepository();}

}
