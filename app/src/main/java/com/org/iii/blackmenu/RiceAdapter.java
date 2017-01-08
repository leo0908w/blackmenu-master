package com.org.iii.blackmenu;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.util.List;


/**
 * Created by user on 2016/12/27.
 */

public class RiceAdapter extends RecyclerView.Adapter<RiceAdapter.ViewHolder> {
    private Fragment fragment;
    private RecyclerView recyclerView;
    private Context context;
//    private Animation mAnimation;
//    private PopupWindow mPopupWindow;
    public ImageView imageView;

    public final View.OnClickListener myOnClickListener = new MyOnClickListener();

    private List<String> food;
    private List<String> path;
    private List<String> price;

    public RiceAdapter(Context context , List<String> food , List<String> path , List<String> price) {
        this.context = context;
        //this.fragment = fragment;
        this.path = path;
        this.food = food;
        this.price = price;
//        Log.v("ppking", " comein");
    }



//    private OnRecyclerViewItemClickListener mOnItemClickListener = null;



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter, parent, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(myOnClickListener);

//        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            Glide.with(context)
                   .load(path.get(position))
                   .error(R.drawable.rice1)
                   .placeholder(R.drawable.loadingntv)
                   .centerCrop()
                   .into(imageView);
        holder.nameTextView.setText(food.get(position));
        holder.priceTextView.setText("$"+price.get(position));

//        holder.itemView.setTag(""+position);
    }

//    @Override
//    public void onClick(View v) {
//        if (mOnItemClickListener != null) {
//            //注意这里使用getTag方法获取数据
//            mOnItemClickListener.onItemClick(v, (String) v.getTag());
//        }
//    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return food.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView priceTextView;
        public TextView nameTextView;


        public ViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            priceTextView = (TextView) itemView.findViewById(R.id.priceTextView);
        }


    }
    public class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            recyclerView = new RecyclerView(context);
            int itemPosition = recyclerView.getChildAdapterPosition(view);

            String foodItem = food.get(itemPosition);
            String pathItem = path.get(itemPosition);
            String priceItem = price.get(itemPosition);
            int numberItem = 1;



            Toast.makeText(context, "已將"+foodItem+"加到點餐明細中", Toast.LENGTH_SHORT).show();
            EventBus.getDefault().post(new Order(foodItem, priceItem, pathItem, numberItem));

//            Log.v("ppking" , "Item : " + foodItem +" : " + pathItem +"  :  "+ priceItem);

        }
    }

    //    public interface OnRecyclerViewItemClickListener {
//        void onItemClick(View view, String app);
//    }

//    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
//        this.mOnItemClickListener = listener;
//    }

}