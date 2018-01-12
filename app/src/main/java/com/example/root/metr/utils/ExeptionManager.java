package com.example.root.metr.utils;


import android.util.Log;

import com.example.root.metr.throwable.ErrorValidateMarkExeption;
import com.example.root.metr.throwable.ErrorValidateModelExeption;
import com.example.root.metr.throwable.ErrorValidateNumberExeption;
import com.example.root.metr.throwable.ErrorValidateTypeExeption;

import java.io.IOException;

import okhttp3.ResponseBody;
import polanski.option.Option;
import retrofit2.HttpException;

public class ExeptionManager {

   static String TAG="TAG";

    public  final String NAME_ERROR_SMS = "sms code not valid";
    public  final String NAME_ERROR_PHONE = "user not found";
    public  final String NAME_ERROR_CONNECT = "failed to connect to";
    public  final String NAME_ERROR_REPEAT_SMS_SEND = "sms already sent in number";
    public  final String ERROR_USER_EXIST = "error user exist";

    public  final int SEND_SMS_FAILURE = 4240;
    public  final int SEND_PHONE_FAILURE = 4241;
    public  final int SEND_SMS_REPEAT_FAILURE = 4242;

    public static final int ERROR_VALIDATE_MARK=3230;
    public static final int ERROR_VALIDATE_TYPE=3231;
    public static final int ERROR_VALIDATE_NUMBER=3232;
    public static final int ERROR_VALIDATE_MODEL=3233;
    public static final int ERROR_VALIDATE_USER=3234;

    public  final int ERROR_CONNECTION = -1;

    public int getfailure(Throwable throwable) throws IOException, NullPointerException {
        if (throwable instanceof HttpException) {
            ResponseBody responseBody = ((HttpException) throwable).response().errorBody();
            if (((HttpException) throwable).response().code() == 424) {
                switch (responseBody.string().toLowerCase()) {
                    case NAME_ERROR_SMS:
                        Log.d(TAG, "getfailure: "+SEND_SMS_FAILURE);
                        return SEND_SMS_FAILURE;
                    case NAME_ERROR_PHONE:
                        Log.d(TAG, "getfailure: " +SEND_PHONE_FAILURE);
                        return SEND_PHONE_FAILURE;
                    case NAME_ERROR_REPEAT_SMS_SEND:
                        return SEND_SMS_REPEAT_FAILURE;
                    case ERROR_USER_EXIST:
                        return ERROR_VALIDATE_USER;
                    default:
                        return 111;
                }
            }else return 111;
        }else if( throwable instanceof ErrorValidateMarkExeption) return ERROR_VALIDATE_MARK;
        else if( throwable instanceof ErrorValidateModelExeption) return ERROR_VALIDATE_MODEL;
        else if( throwable instanceof ErrorValidateNumberExeption) return ERROR_VALIDATE_NUMBER;
        else if( throwable instanceof ErrorValidateTypeExeption) return ERROR_VALIDATE_TYPE;
        else {
            if (Option.ofObj(throwable.getMessage()).orDefault(() -> "").toLowerCase().contains(NAME_ERROR_CONNECT)) {
                Log.d(TAG, "getfailure: "+ERROR_CONNECTION);
                return ERROR_CONNECTION;
            } else return 111;
        }
    }

}
