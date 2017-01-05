package com.org.iii.blackmenu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.org.iii.blackmenu.R.id.toolbar;

public class menupager extends Fragment {
    private TextView tvShopCartSubmit,tvShopCartTotalNum;
    private View mEmtryView;
    private RecyclerView rlvShopCart;
    private ShopCartAdapter mShopCartAdapter;
    private LinearLayout llPay;
    private RelativeLayout rlHaveProduct;
    private List<CartlistBean> mAllOrderList = new ArrayList<>();
    private ArrayList<CartlistBean> mGoPayList = new ArrayList<>();
    private TextView tvShopCartTotalPrice;
    private int mCount,mPosition;
    private float mTotalPrice1;
    private boolean mSelect;
    private Activity mActivity;
    private DBHandler handler;
    private SQLiteDatabase db;
    private FireBase1 fireBase1;
    private int rTotal;
    private String cname , cprice ,re ;
    private int cnumber;
    private int count;
    private MainActivity mainActivity;
    private String strtext;
    private HashMap nameMap =new HashMap();
    private HashMap numberMap = new HashMap();


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fireBase1 = new FireBase1();


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menupager, container, false);

        handler = new DBHandler(mActivity);
        db = handler.getWritableDatabase();

        strtext = getArguments().getString("edttext");
        Log.v("will", "get: "+strtext);



        tvShopCartTotalPrice = (TextView) view.findViewById(R.id.tv_shopcart_totalprice);
        tvShopCartTotalNum = (TextView) view.findViewById(R.id.tv_shopcart_totalnum);

        rlvShopCart = (RecyclerView) view.findViewById(R.id.rlv_shopcart);
        mEmtryView = (View) view.findViewById(R.id.emtryview);
        mEmtryView.setVisibility(View.GONE);
        llPay = (LinearLayout) view.findViewById(R.id.ll_pay);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);
        llPay.setLayoutParams(lp);



        tvShopCartSubmit = (TextView) view.findViewById(R.id.tv_shopcart_submit);
        tvShopCartSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = db.query("cart",null,null,null,null,null,null);
                while (cursor.moveToNext()) {
                    cprice = cursor.getString(cursor.getColumnIndex("price"));
                    String cpath = cursor.getString(cursor.getColumnIndex("path"));
                    cname = cursor.getString(cursor.getColumnIndex("name"));
                    cnumber = cursor.getInt(cursor.getColumnIndex("number"));

                    nameMap.put("name"+count , cname);
                    numberMap.put("number"+count , cnumber);
                    count++;

                    int ctotal = Integer.parseInt(cprice)*cnumber;
                    rTotal +=ctotal;
                }
                count = 0 ;

                Log.v("will", "Total : " + nameMap);
                fireBase1.WriteFoodBase(nameMap , numberMap,""+rTotal , strtext);

                db.execSQL("DROP TABLE IF EXISTS cart");
                db.execSQL("CREATE TABLE cart(id INTEGER PRIMARY KEY AUTOINCREMENT,name STRING,price INTEGER,path STRING,number INTEGER)");
            }
        });

        rlvShopCart.setLayoutManager(new LinearLayoutManager(mActivity));
        mShopCartAdapter = new ShopCartAdapter(mActivity, mAllOrderList);
        rlvShopCart.setAdapter(mShopCartAdapter);
//        mShopCartAdapter.notifyDataSetChanged();
        //删除商品接口
        mShopCartAdapter.setOnDeleteClickListener(new ShopCartAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(View view, int position,int cartid) {

                mShopCartAdapter.notifyDataSetChanged();
            }
        });
        //修改数量接口
        mShopCartAdapter.setOnEditClickListener(new ShopCartAdapter.OnEditClickListener() {
            @Override
            public void onEditClick(int position, int cartid, int count) {
                mCount = count;
                mPosition = position;
            }
        });
        //实时监控全选按钮
        mShopCartAdapter.setResfreshListener(new ShopCartAdapter.OnResfreshListener() {
            @Override
            public void onResfresh( boolean isSelect) {

                int mTotalPrice = 0;
                int mTotalNum = 0;
                mTotalPrice1 = 0;
                mGoPayList.clear();
                for(int i = 0;i < mAllOrderList.size(); i++)
                    if(mAllOrderList.get(i).getIsSelect()) {
                        mTotalPrice += Float.parseFloat(mAllOrderList.get(i).getPrice()) * mAllOrderList.get(i).getCount();
                        mTotalNum += 1;
                        mGoPayList.add(mAllOrderList.get(i));
                    }
                mTotalPrice1 = mTotalPrice;
                tvShopCartTotalPrice.setText("總價：$" + mTotalPrice);
                tvShopCartTotalNum.setText("共" + mTotalNum + "道餐點");
            }
        });

        initData();
        mShopCartAdapter.notifyDataSetChanged();
        return view;
    }

    private void initData(){
        Cursor cursor = db.query("cart",null,null,null,null,null,null);

        while (cursor.moveToNext()){
            String cprice = cursor.getString(cursor.getColumnIndex("price"));
            String cpath = cursor.getString(cursor.getColumnIndex("path"));
            String cname = cursor.getString(cursor.getColumnIndex("name"));
            int cnumber = cursor.getInt(cursor.getColumnIndex("number"));

            CartlistBean sb = new CartlistBean();
            sb.setPrice(cprice);
            sb.setDefaultPic(cpath);
            sb.setProductName(cname);
            sb.setCount(cnumber);
            mAllOrderList.add(sb);
        }
//        Log.v("will", "mu onCreate");
    }

    @Override
    public void onStart() {
        super.onStart();
//        Log.v("will", "mu onStart");
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        Log.v("mu", " mu onDestroyView");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        Log.v("mu", " mu onDestroy");

    }
}
