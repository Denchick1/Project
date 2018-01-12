package com.example.root.metr.root.stateMainActivity;

import android.support.v4.app.FragmentManager;

import com.example.root.metr.screens.profile.ui.ProfileFragment;

public class ProfileState extends BaseState {

    @Override
    public void execute(FragmentManager fragmentManager, int container) {
        setFragment(new ProfileFragment());
        fragmentManager.beginTransaction()
                .replace(container,getFragment()).commit();
    }
}
