package com.org.iii.blackmenu;

import android.app.Activity;
import android.content.Context;
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menupager, container, false);
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

        rlvShopCart.setLayoutManager(new LinearLayoutManager(mActivity));
        mShopCartAdapter = new ShopCartAdapter(mActivity, mAllOrderList);
        rlvShopCart.setAdapter(mShopCartAdapter);
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
                        mTotalPrice += Integer.parseInt(mAllOrderList.get(i).getPrice()) * mAllOrderList.get(i).getCount();
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



        for(int i = 0;i < 4;i ++){
            CartlistBean sb = new CartlistBean();
            sb.setPrice("5000");
            sb.setDefaultPic("http://b.blog.xuite.net/b/2/d/e/12584724/blog_32120/txt/375984958/0.jpg");
            sb.setProductName("高級100A熟成牛排");
            sb.setCount(1);
            mAllOrderList.add(sb);
        }

        Log.v("will", "mu onCreate");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v("will", "mu onStart");
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
