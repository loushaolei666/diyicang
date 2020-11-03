package com.example.mydefenxiang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecyAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<GirlsBean.ResultsBean> list;

    public RecyAdapter(Context context, List<GirlsBean.ResultsBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.girl_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        Glide.with(context).load(list.get(position).getUrl()).into(viewHolder.iv_girl);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onViewItemClick!=null){
                    onViewItemClick.getClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static
    class ViewHolder extends RecyclerView.ViewHolder{
        public View rootView;
        public ImageView iv_girl;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.iv_girl = (ImageView) rootView.findViewById(R.id.iv_girl);
        }

    }
    private OnViewItemClick onViewItemClick;

    public void setOnViewItemClick(OnViewItemClick onViewItemClick) {
        this.onViewItemClick = onViewItemClick;
    }

    public interface OnViewItemClick{
        void getClick(int pos);
    }
}
