package com.example.root.metr.screens.order.ui;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.root.metr.R;
import com.example.root.metr.databinding.ActivityOrderBinding;
import com.example.root.metr.databinding.BottomSheetDialogPriceOrderBinding;
import com.example.root.metr.databinding.DialogDismissOrderBinding;
import com.example.root.metr.root.BaseActivityWithToolbar;
import com.example.root.metr.root.ConnectErrorDialog;
import com.example.root.metr.screens.order.interfaces.OrderView;
import com.example.root.metr.screens.order.presentation.OrderPresenter;

public class OrderActivity extends BaseActivityWithToolbar implements OrderView {

    private OrderPresenter mPresenter;

    private ConnectErrorDialog connectErrorDialog;

    private ActivityOrderBinding binding;

    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order);
        alertDialog = createDialog("Вы уверены что хотите отменить заказ?");
        initToolbar(binding.toolbarOrder, "№345678");
        setColorHomeButton(binding.toolbarOrder, R.color.white);
        connectErrorDialog = new ConnectErrorDialog(this, binding.cstrActivityOrder, view -> {
            connectErrorDialog.dismiss();
        });
        mPresenter = new OrderPresenter();
        mPresenter.attachView(this);

        binding.btCancelOrder.setOnClickListener(view -> showDialog());
        binding.btCancelOrder2.setOnClickListener(view -> showDialog());
        binding.btOkOrderAction1.setOnClickListener(view -> mPresenter.clickBtOKAction1());
        firstOpenOrder();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setViewGroup(binding.cstrActionTop1Action.getId());
    }

    AlertDialog createDialog(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        DialogDismissOrderBinding dialogDismissOrderBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_dismiss_order,
                binding.cstrActivityOrder, false);
        dialogDismissOrderBinding.textView2.setText(text);
        builder.setView(dialogDismissOrderBinding.getRoot());
        builder.setCancelable(false);
        dialogDismissOrderBinding.btCancelDialogOrder2.setOnClickListener(view -> alertDialog.dismiss());
        dialogDismissOrderBinding.btConfirmDialogOrder2.setOnClickListener(view -> finish());
        return builder.create();
    }


    void showDialog() {
        alertDialog.show();
    }

    void firstOpenOrder() {
        binding.cstrBlackAuto2.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_call_order, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                alertDialog = createDialog("Вы уверены, что хотите закрыть заказ? Он будет доступен в списке заявок");
                alertDialog.show();
                return true;
            case R.id.menu_call_order:

                return true;
            default:
                return false;
        }
    }

    @Override
    public void onBackPressed() {
        alertDialog = createDialog("Вы уверены, что хотите закрыть заказ? Он будет доступен в списке заявок");
        alertDialog.show();
    }

    @Override
    public void errorConnect() {
        connectErrorDialog.show();
    }

    @Override
    public void hidePriceCstrBlackAction1() {
        binding.textView10.setVisibility(View.INVISIBLE);
        binding.tvPrice1Action.setVisibility(View.INVISIBLE);
        binding.imageView2.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showCstrBlackAction2() {
        binding.cstrBlackAuto2.setVisibility(View.VISIBLE);
        BottomSheetDialogPriceOrderBinding bottomSheetDialogPriceOrderBinding;
        bottomSheetDialogPriceOrderBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.bottom_sheet_dialog_price_order,
                binding.cstrActivityOrder, false);
        Dialog d = new BottomSheetDialog(this);
        d.setContentView(bottomSheetDialogPriceOrderBinding.getRoot());
        d.setCancelable(true);
        binding.btEditPriceToAction.setOnClickListener(view -> {
            d.show();
            String price = binding.textView18.getText().toString();
            bottomSheetDialogPriceOrderBinding.editText.setText(price.substring(0, price.length() - 1));
        });

        bottomSheetDialogPriceOrderBinding.textView11.setOnClickListener(view -> {
            binding.textView18.setText(bottomSheetDialogPriceOrderBinding.editText.getText().toString() + " \u20BD");
            d.dismiss();
        });
    }

    @Override
    public void hideCstrokCancel() {
        binding.cstrOkCancel.setVisibility(View.GONE);
    }

    @Override
    public void showCstrWillGo() {
        binding.cstrWillgoCancel.setVisibility(View.VISIBLE);
        binding.btWillgoOrderAction2.setOnClickListener(view -> {
            mPresenter.onWillGo();
            binding.btWillgoOrderAction2.setVisibility(View.GONE);
            binding.btCancelOrder2.setVisibility(View.GONE);
            binding.tvClientWait.setVisibility(View.VISIBLE);
        });
    }

}
