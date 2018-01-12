package com.example.root.metr.screens.garage.interfaces;

import com.example.root.metr.models.DTO.Wrecker;
import com.example.root.metr.models.DTO.WreckerParams;
import com.example.root.metr.models.view_model.Full_car;

public interface AddEvacuatorView {
    void showErrorConnectBottomSheet();
    void initTypeWreckerAdapter(String[] types);
    void initMarkWreckerAdapter(String[] types);
    void initModelWreckerAdapter(String[] types);
    void initCarryingWreckerAdapter(String[] types);
    void showProgressBar();
    void hideProgressBar();
    void showErrorInputNumber();
    void showConnectError();
    void fillField(Full_car full_car, Wrecker wrecker);

    void showErrorFillField();
    void onSuccesActionWithWrecker(Wrecker wrecker);

    void inflatingView(WreckerParams wreckerParams);
}
