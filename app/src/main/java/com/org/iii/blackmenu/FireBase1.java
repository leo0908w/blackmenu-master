package com.org.iii.blackmenu;


import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import junit.framework.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FireBase1 {
//    FirebaseDatabase databaseFood = FirebaseDatabase.getInstance();
//    FirebaseDatabase databasePrice = FirebaseDatabase.getInstance();
//    FirebaseDatabase databasePath = FirebaseDatabase.getInstance();
    private FirebaseDatabase databaseTest = FirebaseDatabase.getInstance();
    private FirebaseDatabase databaseWrite = FirebaseDatabase.getInstance();
    private FirebaseDatabase databaseDelete = FirebaseDatabase.getInstance();

//    DatabaseReference myRefFood;
//    DatabaseReference myRefPrice;
//    DatabaseReference myRefPath;
    private DatabaseReference myRefTest;
    private DatabaseReference myRefWrite;
    private DatabaseReference myRefDelete;

    List<String> foodNoodle =new ArrayList<>();
    List<String> foodRice =new ArrayList<>();
    List<String> foodSoup =new ArrayList<>();



    List<String> priceNoodle = new ArrayList<>();
    List<String> priceRice = new ArrayList<>();
    List<String> priceSoup = new ArrayList<>();

    List<String> pathNoodle = new ArrayList<>();
    List<String> pathRice = new ArrayList<>();
    List<String> pathSoup = new ArrayList<>();

    static List<String> key ;
    static List<String> keyNoodle ;
    static List<String> keyRice ;
    static List<String> keySoup ;

    Map<String,Object> child01;
    Map<String,Object> child02;

    Map<String,Object> menuKey;

    private HashMap DBName;
    private HashMap DBNumber;
    private String DBTotal;
    private String seat;

    private long count;
    private boolean isupdate = false;

    private int intkey;


    public void ReadFoodBase(final String name){
        myRefTest =databaseTest.getReference(name);
        myRefTest.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                key = new ArrayList<>();
                keyNoodle = new ArrayList<>();
                keyRice = new ArrayList<>();
                keySoup = new ArrayList<>();
                if(dataSnapshot.getChildrenCount()!=key.size()){
                    foodNoodle.clear();
                    foodRice.clear();
                    foodSoup.clear();

                    pathNoodle.clear();
                    pathRice.clear();
                    pathSoup.clear();

                    priceNoodle.clear();
                    priceRice.clear();
                    priceSoup.clear();
                }
                for (DataSnapshot type : dataSnapshot.getChildren()) {
                    key.add(type.getKey());

                }

                for(DataSnapshot childNoodle : dataSnapshot.child(key.get(0)).getChildren()){
                    keyNoodle.add(childNoodle.getKey());
//                    Log.v("ppking" , "keyNoodle : " +keyNoodle);
                }
                for(DataSnapshot childRice : dataSnapshot.child(key.get(1)).getChildren()){
                    keyRice.add(childRice.getKey());
//                    Log.v("ppking" , "childRice : " +keyRice);
                }
                for(DataSnapshot childSoup : dataSnapshot.child(key.get(2)).getChildren()){
                    keySoup.add(childSoup.getKey());
//                    Log.v("ppking" , "keyNoodle : " +keySoup);
                }

                //麵類 (key.get(0))
                for (int j=0 ; j < keyNoodle.size() ; j++) {
                    foodNoodle.add(dataSnapshot.child(key.get(0)).child(keyNoodle.get(j)).child("name").getValue().toString());
                    pathNoodle.add(dataSnapshot.child(key.get(0)).child(keyNoodle.get(j)).child("path").getValue().toString());
                    priceNoodle.add(dataSnapshot.child(key.get(0)).child(keyNoodle.get(j)).child("price").getValue().toString());
//                    Log.v("ppking", "noodlename : " + foodNoodle);
//                    Log.v("ppking", "noodlepath : " + pathNoodle);
//                    Log.v("ppking", "noodleprice : " + priceNoodle);
                }

                //飯類
                for (int j=0 ; j < keyRice.size() ; j++) {
                    foodRice.add(dataSnapshot.child(key.get(1)).child(keyRice.get(j)).child("name").getValue().toString());
                    pathRice.add(dataSnapshot.child(key.get(1)).child(keyRice.get(j)).child("path").getValue().toString());
                    priceRice.add(dataSnapshot.child(key.get(1)).child(keyRice.get(j)).child("price").getValue().toString());
//                    Log.v("ppking", "ricename : " + foodRice);
//                    Log.v("ppking", "ricepath : " + pathRice);
//                    Log.v("ppking", "ricerice : " + priceRice);
                }

                //濃湯類
                for (int j=0 ; j < keySoup.size() ; j++) {
                    foodSoup.add(dataSnapshot.child(key.get(2)).child(keySoup.get(j)).child("name").getValue().toString());
                    pathSoup.add(dataSnapshot.child(key.get(2)).child(keySoup.get(j)).child("path").getValue().toString());
                    priceSoup.add(dataSnapshot.child(key.get(2)).child(keySoup.get(j)).child("price").getValue().toString());
//                    Log.v("ppking", "soupname : " + foodSoup);
//                    Log.v("ppking", "souppath : " + pathSoup);
//                    Log.v("ppking", "soupprice : " + priceSoup);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void WriteFoodBase(HashMap name , HashMap number , String total , final String seat) {
        this.DBName = name;
        this.DBNumber = number;
        this.DBTotal = total;
        //座號 number
        this.seat = seat;



        myRefWrite = databaseWrite.getReference("numberseat");
        child01 = new HashMap<>();
        child02 = new HashMap<>();
        isupdate = false;




        myRefWrite.addValueEventListener(new ValueEventListener() {
            @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(!isupdate) {
//                        if (number.equals("01")) {
//                            intkey = Integer.parseInt(keyNoodle.get(keyNoodle.size() - 1));
//                        }else if (number.equals("02")) {
//                            intkey = Integer.parseInt(keyRice.get(keyRice.size() - 1));
//                        }else if(number.equals("03")) {
//                            intkey = Integer.parseInt(keySoup.get(keySoup.size() - 1));
//                        }

                        //count = dataSnapshot.getChildrenCount();
                        child02.put("name", DBName);
                        child02.put("number", DBNumber);
                        child02.put("price", DBTotal);
                        child01.put(seat , child02);
//                        Log.v("ppking" ,"key.size"+key.get(key.size()-1 ));
                        //myRefWrite.updateChildren(child01);
                        isupdate = true;
                        myRefWrite.updateChildren(child01);

                    }
                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void DeleteData(int itemPosition ,String type){
        myRefDelete = databaseDelete.getReference("foodinfo/"+type);
        if(type == "noodle") {
            Log.v("ppking", "Delete" + myRefDelete.child(keyNoodle.get(itemPosition)));
            myRefDelete.child(keyNoodle.get(itemPosition)).removeValue();
        }else if(type == "rice"){
            Log.v("ppking", "Delete" + myRefDelete.child(keyRice.get(itemPosition)));
            myRefDelete.child(keyRice.get(itemPosition)).removeValue();
        }else if(type == "soup"){
            Log.v("ppking", "Delete" + myRefDelete.child(keySoup.get(itemPosition)));
            myRefDelete.child(keySoup.get(itemPosition)).removeValue();
        }
        //myRefDelete.child(key.get(itemPosition)).removeValue();
    }
}
