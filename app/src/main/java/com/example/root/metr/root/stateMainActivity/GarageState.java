package com.example.root.metr.root.stateMainActivity;


import android.support.v4.app.FragmentManager;

import com.example.root.metr.screens.garage.ui.GarageFragment;

public class GarageState extends BaseState {

    @Override
    public void execute(FragmentManager fragmentManager,int container) {
        setFragment(new GarageFragment());
        fragmentManager.beginTransaction()
                .replace(container,getFragment()).commit();
    }
}
