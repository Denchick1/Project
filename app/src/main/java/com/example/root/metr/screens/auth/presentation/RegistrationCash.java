package com.example.root.metr.screens.auth.presentation;

import com.example.root.metr.models.business_model.RegistrationModel;
import com.example.root.metr.utils.TimeUtil;

import io.reactivex.schedulers.Schedulers;

public class RegistrationCash {

    private static RegistrationCash registrationCash;

    private String time;

    private RegistrationModel registrationModel;
    private TimeUtil timeUtil;

    public static RegistrationCash getInstance() {
        if (registrationCash == null) {
            registrationCash = new RegistrationCash();
        }
        return registrationCash;
    }

    public RegistrationCash() {
        timeUtil=new TimeUtil();
    }

    public void clearReference() {
        registrationCash = null;
        registrationModel = null;
    }

    public void setRegistrationModel(RegistrationModel registrationModel) {
        this.registrationModel = registrationModel;
    }

    public RegistrationModel getRegistrationModel() {
        return registrationModel;
    }

    public void setTimeChronometr(Long timeChronometr) {
        timeUtil.startChronometer(timeChronometr, Schedulers.computation())
                .subscribe(s -> time = s, Throwable::printStackTrace);
    }

    public void disposeChronometr(){
        timeUtil.dispose();
    }

    public int getTime() {
        int times = Integer.parseInt(time.substring(0, 2));
        times *= 60;
        times += Integer.parseInt(time.substring(3, 5));
        return times;
    }
}
