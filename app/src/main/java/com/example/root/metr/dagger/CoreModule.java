package com.example.root.metr.dagger;


import com.example.root.metr.ScopeD;
import com.example.root.metr.throwable.ThrowableManager;
import com.example.root.metr.utils.EditTextValidator;
import com.example.root.metr.utils.ExeptionManager;
import com.example.root.metr.utils.ImageUtil;
import com.example.root.metr.utils.ShedulerProvider;
import com.example.root.metr.utils.TimeUtil;

import dagger.Module;
import dagger.Provides;

@Module()
public class CoreModule {

    @Provides
    @ScopeD
    public EditTextValidator provideValidator(){
        return new EditTextValidator();
    }

    @Provides
    @ScopeD
    public ShedulerProvider shedulerProvider(){
        return new ShedulerProvider();
    }

    @Provides
    @ScopeD
    public ExeptionManager exeptionManager(){
        return new ExeptionManager();
    }

    @Provides
    @ScopeD
    public ImageUtil imageUtil(){return new ImageUtil();}

    @Provides
    @ScopeD
    public ThrowableManager throwableManager(){
        return new ThrowableManager();
    }

    @Provides
    @ScopeD
    public TimeUtil timeUtil(){return new TimeUtil();}
}
