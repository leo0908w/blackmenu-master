package com.org.iii.blackmenu;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
            if (count>3){
                timer.purge();
                timer.cancel();
                timer=null;
            }
            count++;

        }
    }

}
