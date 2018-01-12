package com.example.root.metr.data;


import com.example.root.metr.data.request_models.BodyAddWrecker;
import com.example.root.metr.data.request_models.BodyAuth;
import com.example.root.metr.data.request_models.BodyRegistration;
import com.example.root.metr.data.request_models.BodyRequestCodeSms;
import com.example.root.metr.models.DTO.Car;
import com.example.root.metr.models.DTO.TypeCar;
import com.example.root.metr.models.DTO.User;
import com.example.root.metr.models.DTO.Wrecker;
import com.example.root.metr.models.DTO.WreckerCarrying;
import com.example.root.metr.models.DTO.WreckerParams;
import com.example.root.metr.models.business_model.RegistrationModel;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface IMetrApi {

    @POST()
    Observable<RegistrationModel> registration(@Body BodyRegistration bodyRegistration);

    @POST()
    Observable<User> confirm_sms(@Body BodyRequestCodeSms bodyRequestCodeSms);

    @POST()
    Observable<RegistrationModel> authorisation(@Body BodyAuth bodyAuth);

    @GET()
    Observable<List<Car>> getCars();

    @GET()
    Observable<List<TypeCar>> getTypeCar();

    @GET()
    Observable<List<WreckerCarrying>> getWreckersCarring();

    @POST()
    Observable<Wrecker> addWrecker(@Body BodyAddWrecker bodyAddWrecker);

    @GET()
    Observable<List<Wrecker>> getWreckers();

    @Multipart
    @POST()
    Observable<ResponseBody> sendPhoto(@Part("id") int id, @Part MultipartBody.Part file);

    @GET()
    Observable<WreckerParams> getWreckerParams();


}
