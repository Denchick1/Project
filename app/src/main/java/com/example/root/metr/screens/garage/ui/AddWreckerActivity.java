package com.example.root.metr.screens.garage.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.root.metr.R;
import com.example.root.metr.databinding.ActivityAddEvacuatorBinding;
import com.example.root.metr.databinding.DialogListAddWreckerBinding;
import com.example.root.metr.databinding.ItemSpinnerAddWreckerBinding;
import com.example.root.metr.databinding.ItemSwitchAddWreckerBinding;
import com.example.root.metr.models.DTO.Wrecker;
import com.example.root.metr.models.DTO.WreckerParams;
import com.example.root.metr.models.view_model.Full_car;
import com.example.root.metr.models.view_model.Tarif;
import com.example.root.metr.root.BaseActivityWithToolbar;
import com.example.root.metr.root.ConnectErrorDialog;
import com.example.root.metr.screens.garage.interfaces.AddEvacuatorView;
import com.example.root.metr.screens.garage.presentation.AddEvacuatorPresenter;
import com.example.root.metr.utils.FileUtils;
import com.example.root.metr.utils.PermissionManager;
import com.example.root.metr.utils.TextInputLayoutErrorUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import polanski.option.Option;

public class AddWreckerActivity extends BaseActivityWithToolbar implements AddEvacuatorView {

    private final int REQ_CODE_TARIF = 119;

    private AddEvacuatorPresenter mPresenter;

    private ConnectErrorDialog connectErrorDialog;

    private ActivityAddEvacuatorBinding binding;

    ProgressDialog progressDialog;
    Wrecker wrecker;
    File file;
    Uri uri;
    List<String> listTypesAndParamsWrecker;
    ArrayMap<String, Spinner> stringSpinnerArrayMap;
    ArrayMap<String, Switch> stringSwitchArrayMap;

    boolean editScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_evacuator);
        wrecker = getIntent().getParcelableExtra("wrecker");
        new PermissionManager().verifyStoragePermissions(this);
        connectErrorDialog = new ConnectErrorDialog(this, binding.cstrEvac, view -> {
            if (editScreen) onActionWithWrecker(String.valueOf(wrecker.getId()));
            else onActionWithWrecker("");
            connectErrorDialog.dismiss();
        });
        mPresenter = new AddEvacuatorPresenter();
        mPresenter.attachView(this);
        mPresenter.onEditWrecker(wrecker);
        stringSpinnerArrayMap = new ArrayMap<>();
        stringSwitchArrayMap = new ArrayMap<>();
        initToolbar(binding.toolbarAddEvacuator, Option.ofObj(getIntent().getStringExtra("title")).orDefault(() -> "Новый эвакуатор"));
        setColorHomeButton(binding.toolbarAddEvacuator, R.color.white);
        mPresenter.onLoadCars();

        binding.ivAddPhoto.setOnClickListener(clickImageAdd);
        binding.ivPhotoEvacuatorAdd.setOnClickListener(clickImageAdd);
        binding.tvAddTypeWrecker.setSingleLine(true);

        resetFocus();

        mPresenter.onLoadParamsWrecker();

    }

    View.OnClickListener clickImageAdd = v -> {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 111);
    };

    @Override
    public void inflatingView(WreckerParams wreckerParams) {

        for (WreckerParams.Spinner spinner : wreckerParams.getSpinner()) {
            ItemSpinnerAddWreckerBinding itemSpinnerAddWreckerBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.item_spinner_add_wrecker, null, false);
            binding.llContainerAddWrecker.addView(itemSpinnerAddWreckerBinding.getRoot());
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item_spiner_add_wrecker, spinner.getNamesItemSpinner());
            itemSpinnerAddWreckerBinding.spinner2.setAdapter(adapter);
          //  stringSpinnerArrayMap.put(spinner.getLabel(),itemSpinnerAddWreckerBinding.spinner2);
        }

        for (WreckerParams.Switch switc : wreckerParams.getMswitch()) {
            ItemSwitchAddWreckerBinding itemSwitchAddWreckerBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.item_switch_add_wrecker, null, false);
            binding.llContainerAddWrecker.addView(itemSwitchAddWreckerBinding.getRoot());
            itemSwitchAddWreckerBinding.switch1.setText(switc.getLabel());
           // stringSwitchArrayMap.put(switc.getLabel(),itemSwitchAddWreckerBinding.switch1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 111 && data != null) {
            Uri imageUr = data.getData();
            uri = imageUr;
            file = FileUtils.getFile(this, imageUr);
            Observable.fromCallable(() -> decodeImage(data))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(bitmap -> {
                        binding.ivAddPhoto.setVisibility(View.INVISIBLE);
                        binding.ivPhotoEvacuatorAdd.setImageBitmap(bitmap);
                    })
                    .observeOn(Schedulers.io())
                    .subscribe(t -> {
                    }, Throwable::printStackTrace);
        }

        if (requestCode == REQ_CODE_TARIF) {
            if (data != null) {
                Tarif tarif = data.getParcelableExtra("tarif");
                if (!editScreen) onActionWithWrecker("");
                else onActionWithWrecker(String.valueOf(wrecker.getId()));
            }
        }
    }

    private Bitmap decodeImage(Intent data) throws IOException {
        //TODO вынести в интерактор
        Uri imageUri = data.getData();
        InputStream imageStream;
        imageStream = getContentResolver().openInputStream(imageUri);
        Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
        if (imageStream != null) {
            imageStream.close();
        }
        return selectedImage;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_registration, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (!editScreen) finish();
                //TODO добавить выход на экран открытого эвакуатора
                else startOpenWreckerActivity(wrecker);
                return true;
            case R.id.menu_checkable:
                if (!editScreen) onActionWithWrecker("");
                else onActionWithWrecker(String.valueOf(wrecker.getId()));
                // startActivityForResult(new Intent(this,AddTarifActivity.class),REQ_CODE_TARIF);
                return true;
            default:
                return false;
        }
    }

    private void onActionWithWrecker(String id) {
      /* mPresenter.onAddOrEditWrecker(
                id,
                binding.tvAddTypeWrecker.getText().toString(),
                binding.tvMarkWrecker.getText().toString(),
                binding.tvModelWrecker.getText().toString(),
                binding.tietGosNumberCar.getText().toString(),
                binding.spinner.getSelectedItem().toString(),
                binding.swCranLebed.isChecked(),
                binding.swJestkScep.isChecked(),
                binding.swElKrep.isChecked(),
                binding.swStrachovka.isChecked(), file, uri);*/

       ArrayMap<String,String> arrayMap = new ArrayMap<>();
       arrayMap.put("id",id);
       //arrayMap.put()
        //пока заглушка
        onSuccesActionWithWrecker(wrecker);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setViewGroup(binding.cstrEvac.getId());
    }

    @Override
    public void showErrorConnectBottomSheet() {
        connectErrorDialog.show();
    }

    @Override
    public void initTypeWreckerAdapter(String[] types) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item_spiner_add_wrecker, types);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog alertDialog = builder.create();
        DialogListAddWreckerBinding dialogListAddWreckerBinding = DataBindingUtil.inflate(alertDialog.getLayoutInflater(), R.layout.dialog_list_add_wrecker, null, false);
        alertDialog.setView(dialogListAddWreckerBinding.getRoot());
        dialogListAddWreckerBinding.list.setAdapter(adapter);
        dialogListAddWreckerBinding.searchView.setVisibility(View.GONE);
        binding.tvAddTypeWrecker.setOnClickListener(view -> alertDialog.show());
        dialogListAddWreckerBinding.list.setOnItemClickListener((adapterView, view, i, l) -> {
            alertDialog.dismiss();
            binding.tvAddTypeWrecker.setText(((TextView) view).getText());
        });

    }

    @Override
    public void initMarkWreckerAdapter(String[] types) {
        //TODO вынести в презентер
        listTypesAndParamsWrecker = Arrays.asList(types);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item_spiner_add_wrecker, listTypesAndParamsWrecker);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog alertDialog = builder.create();
        DialogListAddWreckerBinding dialogListAddWreckerBinding = DataBindingUtil.inflate(alertDialog.getLayoutInflater(), R.layout.dialog_list_add_wrecker, null, false);
        alertDialog.setView(dialogListAddWreckerBinding.getRoot());
        dialogListAddWreckerBinding.list.setAdapter(adapter);
        dialogListAddWreckerBinding.searchView.setVisibility(View.VISIBLE);
        binding.tvMarkWrecker.setOnClickListener(view -> alertDialog.show());

        dialogListAddWreckerBinding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                listTypesAndParamsWrecker = com.annimon.stream.Stream.of(types).filter(value -> value.toLowerCase().startsWith(s.toLowerCase())).toList();
                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddWreckerActivity.this, R.layout.list_item_spiner_add_wrecker, listTypesAndParamsWrecker);
                dialogListAddWreckerBinding.list.setAdapter(adapter);
                return true;
            }
        });
        dialogListAddWreckerBinding.list.setOnItemClickListener((adapterView, view, i, l) -> {
            alertDialog.dismiss();
            binding.tvMarkWrecker.setText(((TextView) view).getText());
            mPresenter.onClickMark(binding.tvMarkWrecker.getText().toString());
        });
    }

    @Override
    public void initModelWreckerAdapter(String[] types) {
        listTypesAndParamsWrecker = Arrays.asList(types);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item_spiner_add_wrecker, types);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog alertDialog = builder.create();
        DialogListAddWreckerBinding dialogListAddWreckerBinding = DataBindingUtil.inflate(alertDialog.getLayoutInflater(), R.layout.dialog_list_add_wrecker, null, false);
        alertDialog.setView(dialogListAddWreckerBinding.getRoot());
        dialogListAddWreckerBinding.list.setAdapter(adapter);
        dialogListAddWreckerBinding.searchView.setVisibility(View.VISIBLE);
        binding.tvModelWrecker.setOnClickListener(view -> alertDialog.show());
        dialogListAddWreckerBinding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                listTypesAndParamsWrecker = com.annimon.stream.Stream.of(types).filter(value -> value.toLowerCase().startsWith(s.toLowerCase())).toList();
                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddWreckerActivity.this, R.layout.list_item_spiner_add_wrecker, listTypesAndParamsWrecker);
                dialogListAddWreckerBinding.list.setAdapter(adapter);
                return true;
            }
        });
        dialogListAddWreckerBinding.list.setOnItemClickListener((adapterView, view, i, l) -> {
            alertDialog.dismiss();
            binding.tvModelWrecker.setText(((TextView) view).getText());
        });
    }

    @Override
    public void initCarryingWreckerAdapter(String[] types) {
    /*    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,types);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(spinnerAdapter);*/
    }

    @Override
    public void showProgressBar() {
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
    }

    @Override
    public void hideProgressBar() {
        progressDialog.cancel();
    }

    @Override
    public void showErrorInputNumber() {
        TextInputLayoutErrorUtils.showError(binding.tilGosNumberCar, "Поле не может быть пустым");
    }

    @Override
    public void showConnectError() {
        connectErrorDialog.show();
    }

    @Override
    public void fillField(Full_car full_car, Wrecker wrecker) {
        editScreen = true;
        binding.tvAddTypeWrecker.setText(full_car.getType(wrecker.getWrecker_type_id()));
        List<String> list = Arrays.asList(full_car.getCarrying());
        int position = 0;
        String nameCarrying = full_car.getValueCarrying(wrecker.getWreckers_carrying_id());
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(nameCarrying)) {
                position = i;
            }
        }

/*
        binding.spinner.setSelection(position);
        binding.tietGosNumberCar.setText(wrecker.getNumber_auto());
        binding.swCranLebed.setChecked(wrecker.getCrane_winch() == 1);
        binding.swStrachovka.setChecked(wrecker.getInsured() == 1);
        binding.swElKrep.setChecked(wrecker.getFastening_elements() == 1);
        binding.swJestkScep.setChecked(wrecker.getRigid_coupling() == 1);*/
    }

    @Override
    public void showErrorFillField() {
        makeToast("Поля заполнены не полностью");
    }

    @Override
    public void onSuccesActionWithWrecker(Wrecker wrecker) {
        startAddTarifActivity(wrecker);
}

    private void startAddTarifActivity(Wrecker wrecker) {
        Intent intent = new Intent(this, AddTarifActivity.class);
        intent.putExtra("wrecker", wrecker);
        startActivity(intent);
        finish();
    }

    private void startOpenWreckerActivity(Wrecker wrecker){
        Intent intent = new Intent(this, OpenWreckerActivity.class);
        intent.putExtra("wrecker", wrecker);
        startActivity(intent);
        finish();
    }

}
