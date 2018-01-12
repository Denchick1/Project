package com.example.root.metr.data.repository;

import android.net.Uri;

import com.example.root.metr.data.IRepositoryWrecker;
import com.example.root.metr.data.MetrRetrofit;
import com.example.root.metr.data.request_models.BodyAddWrecker;
import com.example.root.metr.models.DTO.Wrecker;
import com.example.root.metr.models.DTO.WreckerParams;

import java.io.File;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class WreckerRepository implements IRepositoryWrecker {

    @Override
    public Observable<Wrecker> addWrecker(BodyAddWrecker bodyAddWrecker) {
        return MetrRetrofit.getApiServiceIntercept().addWrecker(bodyAddWrecker)
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Wrecker>> getWreckers() {
        return MetrRetrofit.getApiServiceIntercept().getWreckers()
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Completable sendPhotoWrecker(int id, File file, Uri uri) {

        if(file!=null) {
            return MetrRetrofit.getApiServiceFile().sendPhoto(id, MultipartBody.Part.createFormData(
                    "file",
                    file.getName(),
                    RequestBody.create(MediaType.parse("image"), file)))
                    .concatMapCompletable(responseBody -> Completable.complete())
                    .observeOn(AndroidSchedulers.mainThread());
        }else return Completable.complete();
    }

    @Override
    public Observable<WreckerParams> getWreckerParams() {
        return MetrRetrofit.getApiService().getWreckerParams()
                .observeOn(AndroidSchedulers.mainThread());
    }


}
