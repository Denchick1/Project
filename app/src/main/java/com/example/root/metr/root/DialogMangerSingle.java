package com.example.root.metr.root;


import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.root.metr.R;
import com.example.root.metr.databinding.DialogNewOrderBinding;
import com.example.root.metr.screens.order.ui.OrderActivity;

public class DialogMangerSingle {
    private static final DialogMangerSingle ourInstance = new DialogMangerSingle();

    public static DialogMangerSingle getInstance() {
        return ourInstance;
    }

    private DialogMangerSingle() {
    }

    private DialogNewOrderBinding binding;

    private ViewGroup viewGroup;
    private Context context;

    public void show(){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        binding= DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.dialog_new_order,
                viewGroup,false);
        builder.setView(binding.getRoot());
        builder.setCancelable(false);
        builder.create();
        AlertDialog alertDialog= builder.show();
        binding.btCancelDialogOrder.setOnClickListener(view -> alertDialog.dismiss());
        binding.btConfirmDialogOrder.setOnClickListener(view -> context.startActivity(new Intent(context,OrderActivity.class)));
    }

    public DialogNewOrderBinding getBinding(){return binding;}

    public void setContext(Context context) {
        this.context = context;
    }

    public void setViewGroup(ViewGroup viewGroup) {
        this.viewGroup = viewGroup;
    }
}
