package com.example.root.metr.screens.garage.interfaces;

import com.example.root.metr.models.DTO.Wrecker;

import java.util.List;

public interface GarageView {

    void setupRecycler(List<Wrecker> list);
    void showConnectError();
    void hideFab();
    void showFab();
}
