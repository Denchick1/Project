package com.example.root.metr.root;


import com.example.root.metr.data.IUserRepository;

import javax.inject.Inject;

public class MainActivityPresenter {

    private final int PHISIC_USER=0;
    private final int JURIDIC_USER=1;

    MainView mainView;

    @Inject
    IUserRepository iUserRepository;

    public MainActivityPresenter() {
        App.INSTANCE.getComponent().MainActivityPresenter(this);
    }

    public void attachView(MainView mainView){
        this.mainView=mainView;
    }

    public void create(){
        /*int typeUser=iUserRepository.getUser().getType();
        if(typeUser==PHISIC_USER) mainView.hideItemMenuWebPage();
        if(typeUser==JURIDIC_USER) mainView.showItemMenuWebPage();*/
    }

    public  void exite(){
        iUserRepository.deleteUser();
    }

}
