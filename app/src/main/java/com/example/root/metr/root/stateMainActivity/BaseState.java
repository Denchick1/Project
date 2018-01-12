package com.example.root.metr.root.stateMainActivity;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public abstract class BaseState {

    private Fragment fragment;

    public abstract void execute(FragmentManager fragmentManager,int container);

    public Fragment getFragment(){
        return fragment;
    }

    protected void setFragment(Fragment fragment){
        this.fragment=fragment;
    }

}
