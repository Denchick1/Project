package com.example.root.metr.screens.auth.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.root.metr.R;
import com.example.root.metr.databinding.AgreementFragmentBinding;

public class AgreementFragment extends Fragment {

    AgreementFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.agreement_fragment, container, false);
        binding.btAgreeAgreement.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(),Registration_activity.class));
            getActivity().finish();
        });
        binding.tvCancel.setOnClickListener(view -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .remove(this)
                    .commit();
        });
        return binding.getRoot();
    }



}
