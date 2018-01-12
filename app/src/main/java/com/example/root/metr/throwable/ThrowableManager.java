package com.example.root.metr.throwable;

public class ThrowableManager {

    ErrorValidateTypeExeption errorValidateTypeExeption;
    ErrorValidateNumberExeption errorValidateNumberExeption;
    ErrorValidateModelExeption errorValidateModelExeption;
    ErrorValidateNameExeption errorValidateNameExeption;
    ErrorValideEmailExeption errorValideEmailExeption;
    ErrorValidePhoneExeption errorValidePhoneExeption;
    ErrorValidateMarkExeption errorValidateMarkExeption;

    public ThrowableManager() {
        errorValidateMarkExeption=new ErrorValidateMarkExeption("error_mark");
        errorValidateModelExeption=new ErrorValidateModelExeption("error_model");
        errorValidateNameExeption=new ErrorValidateNameExeption("error_name");
        errorValidateNumberExeption=new ErrorValidateNumberExeption("error_number");
        errorValidateTypeExeption=new ErrorValidateTypeExeption("error_type");
        errorValideEmailExeption=new ErrorValideEmailExeption("error_email");
        errorValidePhoneExeption=new ErrorValidePhoneExeption("error_phone");
    }

    public ErrorValidateTypeExeption getErrorValidateTypeExeption() {
        return errorValidateTypeExeption;
    }

    public ErrorValidateNumberExeption getErrorValidateNumberExeption() {
        return errorValidateNumberExeption;
    }

    public ErrorValidateModelExeption getErrorValidateModelExeption() {
        return errorValidateModelExeption;
    }

    public ErrorValidateNameExeption getErrorValidateNameExeption() {
        return errorValidateNameExeption;
    }

    public ErrorValideEmailExeption getErrorValideEmailExeption() {
        return errorValideEmailExeption;
    }

    public ErrorValidePhoneExeption getErrorValidePhoneExeption() {
        return errorValidePhoneExeption;
    }

    public ErrorValidateMarkExeption getErrorValidateMarkExeption() {
        return errorValidateMarkExeption;
    }
}
