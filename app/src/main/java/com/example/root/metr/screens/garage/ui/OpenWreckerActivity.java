package com.example.root.metr.screens.garage.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.root.metr.CoordinateService;
import com.example.root.metr.R;
import com.example.root.metr.databinding.ActivityOpenWreckerBinding;
import com.example.root.metr.models.DTO.Wrecker;
import com.example.root.metr.models.view_model.Tarif;
import com.example.root.metr.root.BaseActivityWithToolbar;
import com.example.root.metr.root.ConnectErrorDialog;
import com.example.root.metr.screens.garage.interfaces.OpenWreckerView;
import com.example.root.metr.screens.garage.presentation.OpenWreckerPresenter;

public class OpenWreckerActivity extends BaseActivityWithToolbar implements OpenWreckerView {

    private final int REQ_CODE_EDIT_TARIF = 129;

    private OpenWreckerPresenter mPresenter;

    private ConnectErrorDialog connectErrorDialog;
    private Wrecker wrecker;
    ActivityOpenWreckerBinding binding;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_open_wrecker);

        initToolbar(binding.toolbarOpenWrecker, "Empty");
        setColorHomeButton(binding.toolbarOpenWrecker,R.color.white);

        connectErrorDialog = new ConnectErrorDialog(this, binding.cstrActivityOpenWrecker,
                view -> connectErrorDialog.dismiss());

        mPresenter = new OpenWreckerPresenter();
        mPresenter.attachView(this);

        initParamsWrecker();

        wrecker=getIntent().getParcelableExtra("wrecker");
        binding.btEditTarif.setOnClickListener(view -> startEditTarif());

        intent=new Intent(this, CoordinateService.class);
        binding.cstrEnt.setOnClickListener(view -> {
           // startService(intent);
        });

    }

    private void startEditTarif(){
        Intent intent=new Intent(this,AddTarifActivity.class);
        intent.putExtra("edit",true);
        startActivityForResult(intent,REQ_CODE_EDIT_TARIF);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQ_CODE_EDIT_TARIF){
            if(data!=null) {
                Tarif tarif = data.getParcelableExtra("tarif");
            }
        }
    }

    private void initParamsWrecker(){
        String[] params=new String[]{"Кран лебедки","Жесткая сцепка","Элементы крепления","Застрахован"};

        for (String s:params){
            TextView textView=(TextView) LayoutInflater.from(this).inflate(R.layout.list_item, null);
            textView.setText(s);
            binding.llHor.addView(textView);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_wrecker,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.edit_wrecker:
                startAddOrEditWrecker();
                finish();
                return true;
            default:
                return false;
        }
    }

    private void startAddOrEditWrecker(){
        Intent intent=new Intent(this,AddWreckerActivity.class);
        intent.putExtra("wrecker",wrecker);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void errorConnect() {
        connectErrorDialog.show();
    }

}
