package com.example.root.metr.dagger;

import javax.inject.Singleton;

@Singleton
@dagger.Component(modules = RepositoryModule.class)
public interface RepositoryComponent {
    Component component(AuthModule authModule,CoreModule coreModule);

}
