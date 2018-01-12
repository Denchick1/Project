package com.example.root.metr.screens.profile.ui;

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
import com.example.root.metr.databinding.ActivityProfileBinding;
import com.example.root.metr.models.DTO.User;
import com.example.root.metr.root.ConnectErrorDialog;
import com.example.root.metr.screens.profile.interfaces.ProfileView;
import com.example.root.metr.screens.profile.presentation.ProfilePresenter;

import ru.tinkoff.decoro.MaskImpl;
import ru.tinkoff.decoro.slots.PredefinedSlots;
import ru.tinkoff.decoro.watchers.FormatWatcher;
import ru.tinkoff.decoro.watchers.MaskFormatWatcher;

public class ProfileFragment extends Fragment implements ProfileView {

    private ProfilePresenter mPresenter;

    private ConnectErrorDialog connectErrorDialog;

    ActivityProfileBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.activity_profile,container,false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Профиль");

        connectErrorDialog = new ConnectErrorDialog(getActivity(), binding.cstrActivityProfile, view -> {
            connectErrorDialog.dismiss();
        });
        mPresenter = new ProfilePresenter();
        mPresenter.attachView(this);
        mPresenter.create();
        return binding.getRoot();
    }

    @Override
    public void errorConnect() {
        connectErrorDialog.show();
    }

    @Override
    public void initUser(User user) {
        binding.tvNameProfile.setText(user.getF()+" "+user.getI()+" "+user.getO());
        MaskImpl mask = MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER);
        FormatWatcher watcher = new MaskFormatWatcher(mask);
        watcher.installOn(binding.tietPhoneProfile);
        binding.tietPhoneProfile.setText(user.getPhone());
    }

}
