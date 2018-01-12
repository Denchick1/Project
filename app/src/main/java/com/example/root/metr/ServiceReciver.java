package com.example.root.metr;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ServiceReciver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        new Thread(() -> context.startService(new Intent(context,CoordinateService.class))).start();

    }
}
