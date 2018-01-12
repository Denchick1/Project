package com.example.root.metr.screens.auth.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.example.root.metr.R;
import com.example.root.metr.databinding.ActivityStartLoginBinding;
import com.example.root.metr.databinding.DialogDismissOrderBinding;
import com.example.root.metr.root.BaseActivity;
import com.example.root.metr.utils.PermissionManager;

public class StartLoginActivity extends BaseActivity {

    ActivityStartLoginBinding binding;
    AgreementFragment agreementFragment;

    boolean openedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_start_login);
        new PermissionManager().checkPermissionAndRequestEnabled(this);
        binding.btStartLogin.setOnClickListener(view -> startActivity(SendPhoneActivity.class));
        binding.btStartRegistr.setOnClickListener(view -> replaceToAgreementFragment());
        binding.cstrLinkPlayMarket.setOnClickListener(view -> {});
    }

    @Override
    public void onBackPressed() {
        if(openedFragment) removeAgreementFragment();
        else makeDialogOnExite();
    }

    private void makeDialogOnExite(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog alertDialog = builder.create();
        DialogDismissOrderBinding dialogDismissOrderBinding = DataBindingUtil.inflate(alertDialog.getLayoutInflater(), R.layout.dialog_dismiss_order, binding.cstrRoot, false);
        alertDialog.setView(dialogDismissOrderBinding.getRoot());
        dialogDismissOrderBinding.textView2.setText("Вы уверены, что хотите выйти?");
        dialogDismissOrderBinding.btCancelDialogOrder2.setText("Отмена");
        dialogDismissOrderBinding.btConfirmDialogOrder2.setText("Выход");
        alertDialog.show();
        dialogDismissOrderBinding.btConfirmDialogOrder2.setOnClickListener(v -> finish());
        dialogDismissOrderBinding.btCancelDialogOrder2.setOnClickListener(v -> alertDialog.dismiss());
    }

    private void removeAgreementFragment(){
        openedFragment=false;
        getSupportFragmentManager().
                beginTransaction()
                .remove(agreementFragment)
                .commit();
    }

    private void replaceToAgreementFragment(){
        openedFragment=true;
        agreementFragment=new AgreementFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.cstr_root,agreementFragment)
                .commit();
    }

    private void startActivity(Class clazz){
        startActivity(new Intent(this,clazz));
    }
}
