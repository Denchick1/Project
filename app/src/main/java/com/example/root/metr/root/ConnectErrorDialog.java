package com.example.root.metr.root;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.view.ViewGroup;

import com.example.root.metr.R;
import com.example.root.metr.databinding.ErrorConnectBinding;

public class ConnectErrorDialog {

    private ErrorConnectBinding errorConnectBinding;
    private BottomSheetDialog bottomSheetDialog;

    public ConnectErrorDialog(Context context, ViewGroup viewGroup, View.OnClickListener onClickListener) {
        bottomSheetDialog = new BottomSheetDialog(context);
        errorConnectBinding = DataBindingUtil.inflate(bottomSheetDialog.getLayoutInflater(), R.layout.error_connect, viewGroup, false);
        bottomSheetDialog.setContentView(errorConnectBinding.getRoot());
        errorConnectBinding.btRepeat.setOnClickListener(onClickListener);
    }

    public void show(){
        bottomSheetDialog.show();
    }

    public void dismiss(){
        if(bottomSheetDialog.isShowing()) {
            bottomSheetDialog.dismiss();
        }
    }

}
