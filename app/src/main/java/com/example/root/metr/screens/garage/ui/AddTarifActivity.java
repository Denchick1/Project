package com.example.root.metr.screens.garage.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.root.metr.R;
import com.example.root.metr.databinding.ActivityAddTarifBinding;
import com.example.root.metr.models.DTO.Wrecker;
import com.example.root.metr.models.view_model.Tarif;
import com.example.root.metr.root.BaseActivityWithToolbar;
import com.example.root.metr.screens.garage.interfaces.AddTarifView;
import com.example.root.metr.screens.garage.presentation.AddTarifPresenter;

public class AddTarifActivity extends BaseActivityWithToolbar implements AddTarifView {

    private final int REQ_CODE_TARIF = 119;
    private final int REQ_CODE_EDIT_TARIF = 129;

    ActivityAddTarifBinding binding;
    AddTarifPresenter addTarifPresenter;

    //TODO уже не важно редактирование ли это
    boolean edit;

    Wrecker wrecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_add_tarif);
        addTarifPresenter=new AddTarifPresenter();
        addTarifPresenter.attachView(this);
        initToolbar(binding.toolbarAddTarif,"Тариф");
        setColorHomeButton(binding.toolbarAddTarif,R.color.white);
        edit=getIntent().getBooleanExtra("edit",false);
        wrecker=getIntent().getParcelableExtra("wrecker");
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
                //TODO показать диалоговое окно
                returnToEditWrecker();
                finish();
                return true;
            case R.id.menu_checkable:
           /*     if(!edit)initResult(REQ_CODE_TARIF);
                else initResult(REQ_CODE_EDIT_TARIF);*/
             addTarifPresenter.addTarif(initTarif());
                finish();
                return true;
            default:
                return false;
        }
    }

    private void returnToEditWrecker(){
        Intent intent=new Intent(this,AddWreckerActivity.class);
        intent.putExtra("wrecker",wrecker);
        startActivity(intent);
    }

    private void initResult(int reqCode){
       /* Intent intent=new Intent();
        intent.putExtra("tarif",initTarif());
        setResult(reqCode,intent);*/
    }

    private Tarif initTarif(){
        int basePrice=Integer.parseInt(binding.tietBasePrice.getText().toString());
        int kmHolost=Integer.parseInt(binding.tietPriceKmHolost.getText().toString());
        int kmWreckering=Integer.parseInt(binding.tietPriceKmWreckering.getText().toString());
        int minHolost=Integer.parseInt(binding.tietPriceMinHolost.getText().toString());
        int minWreckering=Integer.parseInt(binding.tietPriceMinWreckering.getText().toString());
        int pogrus=Integer.parseInt(binding.tietPriceMinPogruz.getText().toString());
        int call=Integer.parseInt(binding.tietPriceCall.getText().toString());
        return new Tarif(basePrice,kmHolost,kmWreckering,minHolost,minWreckering,pogrus,call);
    }


    @Override
    public void successAddTarif() {
        Intent intent=new Intent(this,OpenWreckerActivity.class);
        intent.putExtra("wrecker",wrecker);
        startActivity(intent);
        finish();
    }

    @Override
    public void showProgressPar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void showConnectError() {

    }
}
