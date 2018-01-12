package com.example.root.metr.screens.auth.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.root.metr.R;
import com.example.root.metr.databinding.ActivitySendSmsBinding;
import com.example.root.metr.databinding.DialogDismissOrderBinding;
import com.example.root.metr.models.business_model.RegistrationModel;
import com.example.root.metr.root.App;
import com.example.root.metr.root.BaseActivity;
import com.example.root.metr.root.ConnectErrorDialog;
import com.example.root.metr.root.MainActivity;
import com.example.root.metr.screens.auth.interfaces.ISendSmsActivity;
import com.example.root.metr.screens.auth.presentation.RegistrationCash;
import com.example.root.metr.screens.auth.presentation.SendSmsActivityPresenter;

public class SendSmsActivity extends BaseActivity implements ISendSmsActivity {

    @InjectPresenter
    SendSmsActivityPresenter sendSmsActivityPresenter;

    ActivitySendSmsBinding binding;
    RegistrationModel registrationModel;
    ConnectErrorDialog connectErrorDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_send_sms);
        binding.tvTitleSendSms.setText(getIntent().getStringExtra("name"));
        registrationModel = getIntent().getParcelableExtra("model");
        if (getIntent().getBooleanExtra("newSms", false)) {
            sendSmsActivityPresenter.startChronometr(RegistrationCash.getInstance().getTime());
            RegistrationCash.getInstance().disposeChronometr();

        } else sendSmsActivityPresenter.startChronometr(registrationModel.getExpires_after());
        connectErrorDialog = new ConnectErrorDialog(this, binding.cstrSms, listener);
        onVisibleBtnNext(View.INVISIBLE);
        binding.btNext2.setOnClickListener(listener);
        binding.tvEnterSms.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sendSmsActivityPresenter.onValidateCode(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    View.OnClickListener listener = view -> {
        sendSmsActivityPresenter.sendSmsPassword(registrationModel, binding.tvEnterSms.getText().toString());
        connectErrorDialog.dismiss();
    };

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog alertDialog = builder.create();
        DialogDismissOrderBinding dialogDismissOrderBinding = DataBindingUtil.inflate(alertDialog.getLayoutInflater(), R.layout.dialog_dismiss_order, binding.cstrSms, false);
        alertDialog.setView(dialogDismissOrderBinding.getRoot());
        dialogDismissOrderBinding.textView2.setText("Вы уверены, что хотите прервать авторизацию?");
        dialogDismissOrderBinding.btCancelDialogOrder2.setText("Отмена");
        dialogDismissOrderBinding.btConfirmDialogOrder2.setText("Выход");
        alertDialog.show();
        dialogDismissOrderBinding.btConfirmDialogOrder2.setOnClickListener(v -> {
            startActivity(new Intent(this, StartLoginActivity.class));
            sendSmsActivityPresenter.putTimeInCash(binding.tvClock2.getText().toString());
            finish();
        });
        dialogDismissOrderBinding.btCancelDialogOrder2.setOnClickListener(v -> alertDialog.dismiss());
    }

    @Override
    public void setTextChronometr(String time) {
        binding.tvClock2.setText(time);
    }

    @Override
    public void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        App.INSTANCE.clearComponent();
        RegistrationCash.getInstance().clearReference();
        finish();
    }

    @Override
    public void enableClickSendInfo() {
        binding.tvClock2.setOnClickListener(view -> {
            sendSmsActivityPresenter.retrySendPhone(registrationModel.getPhone());
            sendSmsActivityPresenter.startChronometr(registrationModel.getExpires_after());
            RegistrationCash.getInstance().clearReference();
        });
    }

    @Override
    public void disableClickSendInfo() {
        binding.tvClock2.setOnClickListener(null);
    }

    @Override
    public void onVisibleSendInfo() {
        binding.tvInfoSendSms.setVisibility(View.VISIBLE);
    }

    @Override
    public void onInvisibleSendInfo() {
        binding.tvInfoSendSms.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showErrorConnect() {
        connectErrorDialog.show();
    }

    @Override
    public void onVisibleBtnNext(int visible) {
        binding.btNext2.setVisibility(visible);
        if (visible == View.VISIBLE)
            hideKeyboard(binding.tvEnterSms);
    }

    @Override
    public void setTextInfo(int error_sms_code) {
        binding.tvEnterSms.setText("");
        binding.btNext2.setVisibility(View.INVISIBLE);
        binding.tvInfoSendSms.setText(error_sms_code);
        binding.tvInfoSendSms.postDelayed(() -> binding.tvInfoSendSms.setText(R.string.repeated_request), 3000);
    }

    @Override
    public void replaceRegistrationModel(RegistrationModel registrationModel) {
        this.registrationModel = registrationModel;
    }
}
