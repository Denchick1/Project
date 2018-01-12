package com.example.root.metr.screens.auth.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.root.metr.R;
import com.example.root.metr.databinding.ActivityLoginBinding;
import com.example.root.metr.models.business_model.RegistrationModel;
import com.example.root.metr.root.BaseActivity;
import com.example.root.metr.root.ConnectErrorDialog;
import com.example.root.metr.screens.auth.interfaces.ISendPhoneActivity;
import com.example.root.metr.screens.auth.interfaces.LiveCiclePresenter;
import com.example.root.metr.screens.auth.presentation.SendPhoneActivityPresenter;

import ru.tinkoff.decoro.MaskImpl;
import ru.tinkoff.decoro.slots.PredefinedSlots;
import ru.tinkoff.decoro.watchers.FormatWatcher;
import ru.tinkoff.decoro.watchers.MaskFormatWatcher;

public class SendPhoneActivity extends BaseActivity implements ISendPhoneActivity {

    @InjectPresenter
    SendPhoneActivityPresenter authPresenter;

    ActivityLoginBinding loginBinding;
    LiveCiclePresenter liveCiclePresenter;
    ConnectErrorDialog connectErrorDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        liveCiclePresenter = authPresenter;
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        MaskImpl mask = MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER);
        FormatWatcher watcher = new MaskFormatWatcher(mask);
        watcher.installOn(loginBinding.etEnterPhone);
        EditText edt=loginBinding.etEnterPhone;
        edt.setSelection(edt.getText().length());
        initChangeTextListnerEtEnterPhone();
        initVisibleButtonNext(View.INVISIBLE);
        loginBinding.btNext.setOnClickListener(view -> authPresenter.onSendPhone(edt.getText().toString()));

        connectErrorDialog=new ConnectErrorDialog(this,loginBinding.cstrLogin, view -> {
            authPresenter.onSendPhone(edt.getText().toString());
            connectErrorDialog.dismiss();
        });

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        loginBinding.etEnterPhone.postDelayed(() -> showKeyboard(loginBinding.etEnterPhone),100);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        authPresenter.onDestroy();
    }

    @Override
    public void initVisibleButtonNext(int visibilityBtNext) {
        loginBinding.btNext.setVisibility(visibilityBtNext);
        if(visibilityBtNext==View.VISIBLE)hideKeyboard(loginBinding.etEnterPhone);
    }


    @Override
    public void initVisivileProgressBar(int visibilityProgressBar) {
        loginBinding.progressBar.setVisibility(visibilityProgressBar);
    }

    @Override
    public void showErrorConnect() {
        connectErrorDialog.show();
    }

    @Override
    public void onVisibleSendInfo() {
        loginBinding.tvInfoSendPhone.setText("Данный номер не зарегистрирован");
        loginBinding.btNext.setVisibility(View.INVISIBLE);
        loginBinding.tvInfoSendPhone.setVisibility(View.VISIBLE);
        loginBinding.tvInfoSendPhone.postDelayed(() ->
                loginBinding
                .tvInfoSendPhone
                .setVisibility(View.INVISIBLE),3000);
    }

    @Override
    public void smsAlready(RegistrationModel registrationModel, long time) {
        Intent intent=new Intent(this,SendSmsActivity.class);
        intent.putExtra("name","АВТОРИЗАЦИЯ");
        intent.putExtra("model",registrationModel);
        intent.putExtra("newSms",true);
        startActivity(intent);
        finish();
    }

    @Override
    public void startSmsEnter(RegistrationModel registrationModel) {
        Intent intent=new Intent(this,SendSmsActivity.class);
        intent.putExtra("name","АВТОРИЗАЦИЯ");
        intent.putExtra("model",registrationModel);
        startActivity(intent);
        finish();
    }

    private void initChangeTextListnerEtEnterPhone(){
        loginBinding.etEnterPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                authPresenter.phoneValidate(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


}
