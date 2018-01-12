package com.example.root.metr.utils;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ShedulerProvider {

    public Scheduler io(){
        return Schedulers.io();
    }

    public Scheduler computation(){
        return Schedulers.computation();
    }

    public Scheduler main(){return AndroidSchedulers.mainThread();}

}
