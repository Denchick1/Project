package com.example.root.metr.root.stateMainActivity;


import android.support.v4.app.FragmentManager;

public class MainActivityState {

    private BaseState baseState;

    private static MainActivityState ourInstance;

  public static MainActivityState getInstance() {
      if(ourInstance==null){
          ourInstance=new MainActivityState();
      }
        return ourInstance;
    }

    private MainActivityState() {
        baseState=new GarageState();
    }

    public MainActivityState setState(BaseState baseState){
       this.baseState=baseState;
       return this;
    }

    public MainActivityState execute(FragmentManager fragmentManager, int container){
        baseState.execute(fragmentManager,container);
        return this;
    }

    public void clear(){
        ourInstance=null;
    }
}
