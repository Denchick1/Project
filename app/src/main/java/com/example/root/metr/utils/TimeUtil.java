package com.example.root.metr.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;

public class TimeUtil {

    private long finalTime;

    private Disposable disposable= Disposables.empty();

    public io.reactivex.Observable<String> startChronometer(long time, Scheduler schedulers) {
        finalTime = time <= 0 ? 1 : time;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss", Locale.ROOT);
        return Observable
                .intervalRange(1, finalTime, 0, 1, TimeUnit.SECONDS, schedulers)
                .map(aLong -> simpleDateFormat.format((finalTime - aLong) * 1000))
                .doOnSubscribe(disposable1 -> disposable=disposable1);
    }

    public void dispose(){
        disposable.dispose();
    }

}
