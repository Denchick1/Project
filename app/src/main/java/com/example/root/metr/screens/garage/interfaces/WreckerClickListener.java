package com.example.root.metr.screens.garage.interfaces;

import com.example.root.metr.models.DTO.Wrecker;

public interface WreckerClickListener {
    void click(Wrecker wrecker);

    static WreckerClickListener empty(){
        return wrecker -> {};
    }
}
