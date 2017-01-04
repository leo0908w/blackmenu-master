package com.org.iii.blackmenu;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Rice extends Fragment {
    private RecyclerView mRecyclerView;
    private Activity myContext;
    private FireBase1 fireBase1;
    private MyHandle myHandle;
    private int count;
    private Timer timer;
    private RiceAdapter riceAdapter;
    private MyReceive myReceive;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.myContext = (Activity) context;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fireBase1 = new FireBase1();
        fireBase1.ReadFoodBase("foodinfo");




        startRead();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rice_f1, null);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
//        mRecyclerView.addItemDecoration(new MarginDecoration(this));
//        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(myContext, 2));
        riceAdapter = new RiceAdapter(myContext, fireBase1.foodRice, fireBase1.pathRice ,fireBase1.priceRice);
        mRecyclerView.setAdapter(riceAdapter);

        myReceive = new MyReceive();
        IntentFilter intentFilter =new IntentFilter();
        intentFilter.addAction("test");
        myContext.registerReceiver(myReceive, intentFilter);


        return view;
    }

    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Override
    public void onStart() {
        super.onStart();
//        Log.v("will", "Rice onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
//        Log.v("will", "Rice onResume");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        Log.v("will", "Rice onActivityCreated");
    }

    public void startRead (){
        timer = new Timer();
        myHandle = new MyHandle();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                myHandle.sendEmptyMessage(0);
            }
        }, 0, 1000);

    }


    public class MyHandle extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            riceAdapter.notifyDataSetChanged();
            if (count>2){
                timer.purge();
                timer.cancel();
                timer=null;
            }
            count++;

        }
    }

    private class MyReceive extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String test = intent.getStringExtra("123");
            Log.v("ppking", test);
        }
    }
}
