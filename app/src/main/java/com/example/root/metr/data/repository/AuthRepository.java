package com.example.root.metr.data.repository;

import com.example.root.metr.data.IRepositoryAuth;
import com.example.root.metr.data.MetrRetrofit;
import com.example.root.metr.data.request_models.BodyAuth;
import com.example.root.metr.data.request_models.BodyRegistration;
import com.example.root.metr.data.request_models.BodyRequestCodeSms;
import com.example.root.metr.models.DTO.User;
import com.example.root.metr.models.business_model.RegistrationModel;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class AuthRepository implements IRepositoryAuth{

    @Inject
    public AuthRepository() {
    }

    @Override
    public Observable<RegistrationModel> sendPhone(String phone) {
        return MetrRetrofit.getApiService()
                .authorisation(new BodyAuth(phone))
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<RegistrationModel> registration(String name,String phone,String email){
        return MetrRetrofit.getApiService()
                .registration(new BodyRegistration(phone,name,email))
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<User> sendSms(String uuid, String code) {
        return MetrRetrofit.getApiService()
                .confirm_sms(new BodyRequestCodeSms(uuid,String.valueOf(code)))
                .observeOn(AndroidSchedulers.mainThread());
    }

}
