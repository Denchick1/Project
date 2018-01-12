package com.example.root.metr.screens.order.presentation;

import com.example.root.metr.root.App;
import com.example.root.metr.screens.order.business.OrderInteractor;
import com.example.root.metr.screens.order.interfaces.OrderView;
import com.example.root.metr.utils.ShedulerProvider;

import javax.inject.Inject;

public class OrderPresenter {

    @Inject
    ShedulerProvider shedulerProvider;
    OrderInteractor mOrderInteractor;
    private OrderView mOrderView;

    public OrderPresenter() {
        App.INSTANCE.getComponent().OrderPresenter(this);

        mOrderInteractor = new OrderInteractor();


    }

    public void attachView(OrderView view) {
        mOrderView = view;
    }

    public void clickBtOKAction1(){
        mOrderView.hidePriceCstrBlackAction1();
        mOrderView.showCstrBlackAction2();
        mOrderView.hideCstrokCancel();
        mOrderView.showCstrWillGo();
    }

    public void onWillGo(){

    }


    public void onDestroy() {
        mOrderView = null;
    }
}
