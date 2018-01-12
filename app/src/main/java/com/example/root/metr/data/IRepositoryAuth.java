package com.example.root.metr.data;

import com.example.root.metr.models.DTO.User;
import com.example.root.metr.models.business_model.RegistrationModel;

import io.reactivex.Observable;

public interface IRepositoryAuth {
    Observable<RegistrationModel> sendPhone(String phone);
    Observable<RegistrationModel> registration (String name,String phone,String email);
    Observable<User> sendSms(String uuid, String code);
}
