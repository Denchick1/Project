package com.example.root.metr.screens.garage.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.root.metr.R;
import com.example.root.metr.adapters.EvacuatorRecyclerAdapter;
import com.example.root.metr.databinding.GarageFragmentBinding;
import com.example.root.metr.models.DTO.Wrecker;
import com.example.root.metr.root.ConnectErrorDialog;
import com.example.root.metr.screens.garage.interfaces.GarageView;
import com.example.root.metr.screens.garage.presentation.GaragePresenter;

import java.util.List;

public class GarageFragment extends Fragment implements GarageView {

    private GaragePresenter mPresenter;

    GarageFragmentBinding binding;
    EvacuatorRecyclerAdapter evacuatorRecyclerAdapter;
    ConnectErrorDialog connectErrorDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding= DataBindingUtil.inflate(inflater,R.layout.garage_fragment,container,false);

        mPresenter=new GaragePresenter();
        mPresenter.attachView(this);

        AppCompatActivity appCompatActivity=(AppCompatActivity)getActivity();
        appCompatActivity.getSupportActionBar().setTitle("Эвакуаторы");

        evacuatorRecyclerAdapter=new EvacuatorRecyclerAdapter();
        binding.rvGarage.setAdapter(evacuatorRecyclerAdapter);
        connectErrorDialog=new ConnectErrorDialog(getActivity(),binding.cstrLogin,view -> {
            mPresenter.onLoadListWreckers();
            mPresenter.onLoadFullCar();
            connectErrorDialog.dismiss();
        });

        binding.fabAddEvacuator.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(),AddWreckerActivity.class));
        });

        evacuatorRecyclerAdapter.setWreckerClickListener(wrecker -> {
            Intent intent=new Intent(getActivity(),OpenWreckerActivity.class);
            intent.putExtra("wrecker",wrecker);
            startActivity(intent);
        });

        mPresenter.onLoadFullCar();
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onLoadListWreckers();
    }

    @Override
    public void setupRecycler(List<Wrecker> list) {
        evacuatorRecyclerAdapter.setList(list);
    }

    @Override
    public void showConnectError() {
        connectErrorDialog.show();
    }

    @Override
    public void hideFab() {
        binding.fabAddEvacuator.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showFab() {
        binding.fabAddEvacuator.setVisibility(View.VISIBLE);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
