package com.example.root.metr.screens.auth.business;

import com.example.root.metr.data.IRepositoryAuth;
import com.example.root.metr.data.IUserRepository;
import com.example.root.metr.models.business_model.RegistrationModel;
import com.example.root.metr.root.App;
import com.example.root.metr.throwable.ThrowableManager;
import com.example.root.metr.utils.EditTextValidator;
import com.example.root.metr.utils.TimeUtil;

import java.util.regex.Pattern;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class AuthInteractor {

    @Inject
    IRepositoryAuth iRepositoryAuth;
    @Inject
    IUserRepository iUserRepository;
    @Inject
    EditTextValidator editTextValidator;
    @Inject
    ThrowableManager throwableManager;
    @Inject
    TimeUtil timeUtil;

    public AuthInteractor() {
        App.INSTANCE.getComponent().AuthInteractor(this);
    }

    public AuthInteractor(IRepositoryAuth iRepositoryAuth, EditTextValidator editTextValidator,
                          ThrowableManager throwableManager, TimeUtil timeUtil) {
        this.iRepositoryAuth = iRepositoryAuth;
        this.editTextValidator = editTextValidator;
        this.throwableManager = throwableManager;
        this.timeUtil=timeUtil;
    }

    public io.reactivex.Observable<String> startChronometer(long time, Scheduler schedulers) {
        return timeUtil.startChronometer(time, schedulers);
    }

    public Observable<RegistrationModel> sendPhone(String phone) {
        return iRepositoryAuth.sendPhone(phone);
    }

    public Completable sendSms(String uuid, String code) {
        return iRepositoryAuth
                .sendSms(uuid, code)
                .doOnNext(iUserRepository::addUser)
                .flatMapCompletable(user -> Completable.complete());
    }

    public Observable<RegistrationModel> startRegistration(String name, String phone, String email) {
        if (!validateName(name)) {
            return Observable.error(throwableManager.getErrorValidateNameExeption());
        } else if (!validateEmail(email)) {
            return Observable.error(throwableManager.getErrorValideEmailExeption());
        } else return iRepositoryAuth.registration(name, phone, email);
    }

    public rx.Observable<Boolean> validateField(String name,String phone,String email){
        boolean valide = validateEmail(email)&& validateName(name)&&validatePhone(phone);
        return rx.Observable.just(valide);
    }

    private boolean validateEmail(String email) {
        return editTextValidator.isValidEmailAddress(email);
    }

    private boolean validateName(String name) {
        Pattern pattern = Pattern.compile("\\s*(\\s|,|!|\\.)\\s*");
        String[] words = pattern.split(name);

        return name != null && name.length() != 0 && words.length<=3 && !name.startsWith(" ");
    }

    private boolean validatePhone(String phone){
        return editTextValidator.phoneValidateNew(phone);
    }
}
