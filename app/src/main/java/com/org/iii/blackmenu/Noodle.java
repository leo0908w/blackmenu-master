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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Timer;
import java.util.TimerTask;

public class Noodle extends Fragment {
    private RecyclerView mRecyclerView;
    private Activity myContext;
    private FireBase1 fireBase1;
    private MyHandle myHandle;
    private int count;
    private Timer timer;
    private NoodleAdapter noodleAdapter;

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
        View view = inflater.inflate(R.layout.noodle_f2, null);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
//        mRecyclerView.addItemDecoration(new MarginDecoration(this));
//        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(myContext, 2));
        noodleAdapter = new NoodleAdapter(myContext, fireBase1.foodNoodle, fireBase1.pathNoodle ,fireBase1.priceNoodle);
        mRecyclerView.setAdapter(noodleAdapter);

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
            noodleAdapter.notifyDataSetChanged();
            if (count>2){
                timer.purge();
                timer.cancel();
                timer=null;
            }
            count++;

        }
    }

}
