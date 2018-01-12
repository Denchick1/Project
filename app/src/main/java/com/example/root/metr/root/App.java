package com.example.root.metr.root;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.crashlytics.android.Crashlytics;
import com.example.root.metr.dagger.AuthModule;
import com.example.root.metr.dagger.Component;
import com.example.root.metr.dagger.CoreModule;
import com.example.root.metr.dagger.DaggerRepositoryComponent;
import com.example.root.metr.dagger.RepositoryComponent;
import com.example.root.metr.dagger.RepositoryModule;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

import io.fabric.sdk.android.Fabric;
import polanski.option.Option;

public class App extends Application {

    private Component component;
    private RepositoryComponent repositoryComponent;

    private static String token;

    public static void setToken(String token) {
        App.token = token;
    }

    public static String getToken() {
        return Option.ofObj(token).orDefault(() -> "");
    }

    public static App INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();

        LeakCanary.install(this);
        Fabric.with(this, new Crashlytics());

        INSTANCE = this;

        Stetho.InitializerBuilder initializerBuilder =
                Stetho.newInitializerBuilder(this);

        initializerBuilder.enableWebKitInspector(
                Stetho.defaultInspectorModulesProvider(this)
        );

        initializerBuilder.enableDumpapp(
                Stetho.defaultDumperPluginsProvider(this)
        );

        Stetho.Initializer initializer = initializerBuilder.build();
        Stetho.initialize(initializer);

        repositoryComponent = DaggerRepositoryComponent.builder()
                .repositoryModule(new RepositoryModule())
                .build();
    }

    public Component getComponent() {
        if (component == null) {
            return component = repositoryComponent.component(new AuthModule(), new CoreModule());
        } else return component;
    }

    public void clearComponent() {
        component = null;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
