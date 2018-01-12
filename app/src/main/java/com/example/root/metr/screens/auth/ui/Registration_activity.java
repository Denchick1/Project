package com.example.root.metr.screens.auth.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;

import com.example.root.metr.R;
import com.example.root.metr.databinding.ActivityRegistrationBinding;
import com.example.root.metr.databinding.DialogDismissOrderBinding;
import com.example.root.metr.models.business_model.RegistrationModel;
import com.example.root.metr.root.BaseActivityWithToolbar;
import com.example.root.metr.root.ConnectErrorDialog;
import com.example.root.metr.screens.auth.interfaces.IRegistrationActivity;
import com.example.root.metr.screens.auth.presentation.RegistrationActivityPresenter;
import com.example.root.metr.utils.TextInputLayoutErrorUtils;

import ru.tinkoff.decoro.MaskImpl;
import ru.tinkoff.decoro.slots.PredefinedSlots;
import ru.tinkoff.decoro.watchers.FormatWatcher;
import ru.tinkoff.decoro.watchers.MaskFormatWatcher;

public class Registration_activity extends BaseActivityWithToolbar implements IRegistrationActivity {

    ActivityRegistrationBinding binding;
    RegistrationActivityPresenter registrationActivityPresenter;
    ConnectErrorDialog connectErrorDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration);
        initToolbar(binding.toolbarReg, "Регистрация");
        setColorHomeButton(binding.toolbarReg, R.color.white);
        setViewGroup(R.id.cstt);
        attachKeyboardListeners();
        registrationActivityPresenter = new RegistrationActivityPresenter();
        registrationActivityPresenter.attachView(this);
        connectErrorDialog = new ConnectErrorDialog(this, binding.cstt, view -> {
            sendRegistration();
            connectErrorDialog.dismiss();
        });
        MaskImpl mask = MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER);
        FormatWatcher watcher = new MaskFormatWatcher(mask);
        watcher.installOn(binding.tietPhone);

        binding.btGo.setOnClickListener(v -> sendRegistration());

        binding.tietPhone.setText("+7 (");
        binding.tietPhone.addTextChangedListener(textWatcher);

        binding.tietName.addTextChangedListener(textWatcher);

        binding.tietMeil.addTextChangedListener(textWatcher);
    }

    TextWatcher textWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            registrationActivityPresenter.validateField(
                    binding.tietName.getText().toString(),
                    binding.tietPhone.getText().toString(),
                    binding.tietMeil.getText().toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    @Override
    protected void onShowKeyboard(int keyboardHeight) {
        float btHeight= (float) (binding.btGo.getHeight()*1.5);
        binding.btGo.setTranslationY(-((float)keyboardHeight+btHeight));
    }

    @Override
    protected void onHideKeyboard() {
        binding.btGo.setTranslationY(0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                backPress();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onBackPressed() {
        backPress();
    }

    private void backPress(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        AlertDialog alertDialog=builder.create();
        DialogDismissOrderBinding dialogDismissOrderBinding=DataBindingUtil.inflate(alertDialog.getLayoutInflater(),R.layout.dialog_dismiss_order,binding.cstt,false);
        alertDialog.setView(dialogDismissOrderBinding.getRoot());
        dialogDismissOrderBinding.textView2.setText("Вы уверены, что хотите прервать регистрацию?");
        dialogDismissOrderBinding.btCancelDialogOrder2.setText("Отмена");
        dialogDismissOrderBinding.btConfirmDialogOrder2.setText("Выход");
        alertDialog.show();
        dialogDismissOrderBinding.btConfirmDialogOrder2.setOnClickListener(v ->{
            startActivity(new Intent(this,StartLoginActivity.class));
            finish();
        });
        dialogDismissOrderBinding.btCancelDialogOrder2.setOnClickListener(v -> alertDialog.dismiss());
    }

    private void sendRegistration() {
        String name = binding.tietName.getText().toString();
        String phone = binding.tietPhone.getText().toString();
        String email = binding.tietMeil.getText().toString();
        registrationActivityPresenter.onRegistration(name, phone, email);
        hideKeyboard(binding.tilMeil);
    }

    @Override
    public void successRegistration(RegistrationModel registrationModel) {
        Intent intent = new Intent(this, SendSmsActivity.class);
        intent.putExtra("name", "РЕГИСТРАЦИЯ");
        intent.putExtra("model", registrationModel);
        startActivity(intent);
        finish();
    }

    @Override
    public void showProgressBar() {
        binding.progressReg.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        binding.progressReg.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showErrorValidEmail() {
        TextInputLayoutErrorUtils.showError(binding.tilMeil, "Неверный email");
    }

    @Override
    public void showErrorValidPhone() {
        TextInputLayoutErrorUtils.showError(binding.tilPhone, "Неверный телефон");
    }

    @Override
    public void showErrorValidName() {
        TextInputLayoutErrorUtils.showError(binding.tilName, "Неверно введено имя");
    }

    @Override
    public void onEnableBtGo() {
        /*binding.btGo.setBackgroundColor(getResources().getColor(R.color.dark_blue));
        binding.btGo.setOnClickListener(v -> sendRegistration());*/
        binding.btGo.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDisableBtGo() {
      /*  binding.btGo.setBackgroundColor(getResources().getColor(R.color.grey_disable));
        binding.btGo.setOnClickListener(v -> {});*/
        binding.btGo.setVisibility(View.INVISIBLE);

    }

    @Override
    public void showConnectError() {
        connectErrorDialog.show();
    }

    @Override
    public void showErrorUserExist() {
        binding.tvErrorUserExist.setVisibility(View.VISIBLE);
        binding.tvErrorUserExist.postDelayed(() -> {
            binding.tvErrorUserExist.setVisibility(View.INVISIBLE);
        },3000);
    }
}
