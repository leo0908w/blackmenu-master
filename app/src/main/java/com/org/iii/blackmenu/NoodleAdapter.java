package com.org.iii.blackmenu;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;


/**
 * Created by user on 2016/12/27.
 */

public class NoodleAdapter extends RecyclerView.Adapter<NoodleAdapter.ViewHolder> implements View.OnClickListener {
    private Fragment fragment;
    public ImageView imageView;

    private List<String> food;
    private List<String> path;
    private List<String> price;

    public NoodleAdapter(Fragment fragment , List<String> food , List<String> path , List<String> price) {

        this.fragment = fragment;
        this.path = path;
        this.food = food;
        this.price = price;
    }


    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String app);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter, parent, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            Glide.with(fragment)
                   .load(path.get(position))
                   .error(R.drawable.rice1)
                   .placeholder(R.drawable.noodle1)
                   .centerCrop()
                   .into(imageView);
        holder.nameTextView.setText(food.get(position));
        holder.priceTextView.setText("$"+price.get(position));

        holder.itemView.setTag(""+position);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (String) v.getTag());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return food.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
//        public CardView cardView;
        public TextView priceTextView;
        public TextView nameTextView;
//        private Context context;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            priceTextView = (TextView) itemView.findViewById(R.id.priceTextView);
//            this.context = context;
//            itemView.setOnClickListener(this);
        }
    }

}