package com.org.iii.blackmenu;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("ppking" , "food" + intent.getStringExtra("food"));
        List<Order> list = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        HashMap<String, Integer> hashMap = new HashMap<>();
        int number = 0;

        for(int i = 0 ; i<list.size() ; i++) {
            if (list.get(i).equals(intent.getStringExtra("food"))){
                list.add(new Order(
                        intent.getStringExtra("food"),
                        intent.getStringExtra("path"),
                        intent.getStringExtra("price"),
                        intent.getIntExtra("number", 0)
                ));
                number ++;
            }else {
                number=1;
            }
        }






//        intent = new Intent("test");
//        intent.putExtra("123", "123456");
//        sendBroadcast(intent);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }
}
