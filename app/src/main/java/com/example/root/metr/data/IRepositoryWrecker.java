package com.example.root.metr.data;

import android.net.Uri;

import com.example.root.metr.data.request_models.BodyAddWrecker;
import com.example.root.metr.models.DTO.Wrecker;
import com.example.root.metr.models.DTO.WreckerParams;

import java.io.File;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import retrofit2.http.Body;

public interface IRepositoryWrecker {

    Observable<Wrecker> addWrecker(@Body BodyAddWrecker bodyAddWrecker);

    Observable<List<Wrecker>> getWreckers();

    Completable sendPhotoWrecker(int id, File file, Uri uri);

    Observable<WreckerParams> getWreckerParams();
}
