package com.example.day14.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.day14.R;
import com.example.day14.bean.JavaBean;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RcyAdapter extends RecyclerView.Adapter {
    private ArrayList<JavaBean.DataBean.ListBean> list;
    private Context context;
    public int index = -1;

    public int getSxf() {
        return sxf;
    }

    public void setSxf(int sxf) {
        this.sxf = sxf;
    }

    private int sxf ;


    public RcyAdapter(ArrayList<JavaBean.DataBean.ListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rcy, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        JavaBean.DataBean.ListBean bean = list.get(position);//取出点击的数据
        ViewHolder holder1= (ViewHolder) holder;
        Glide.with(context).load(bean.getPic()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder1.iv_rcy);
        holder1.tv_zhongguo.setText(bean.getName());
        holder1.tv_kucun.setText("库存"+bean.getStockCount()+"");
        holder1.tv_xiaoshou.setText("销售"+bean.getSellCount()+"");
        holder1.tv_money.setText(bean.getPrice()+sxf+"元");

        holder1.rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    index=position;//记录选中的条目的索引
//                    notifyDataSetChanged();
                    bean.setIsChecked(true);//把当前数据设置为选中状态
                    //循环数据集合，把其他条数据设置为不选中状态
                    for (int i = 0; i < list.size(); i++) {
                        JavaBean.DataBean.ListBean listBean = list.get(i);
                        if(i != position){
                            listBean.setIsChecked(false);
                        }
                    }
                    notifyDataSetChanged();
                }

            }
        });
        holder1.rb.setChecked(bean.getIsChecked());

//        if (index==position){
//            holder1.rb.setChecked(true);
//        }else {
//            holder1.rb.setChecked(false);
//        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static
    class ViewHolder extends RecyclerView.ViewHolder{
        public View rootView;
        public RadioButton rb;
        public ImageView iv_rcy;
        public TextView tv_zhongguo;
        public TextView tv_kucun;
        public TextView tv_xiaoshou;
        public TextView tv_money;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.rb = (RadioButton) rootView.findViewById(R.id.rb);
            this.iv_rcy = (ImageView) rootView.findViewById(R.id.iv_rcy);
            this.tv_zhongguo = (TextView) rootView.findViewById(R.id.tv_zhongguo);
            this.tv_kucun = (TextView) rootView.findViewById(R.id.tv_kucun);
            this.tv_xiaoshou = (TextView) rootView.findViewById(R.id.tv_xiaoshou);
            this.tv_money = (TextView) rootView.findViewById(R.id.tv_money);
        }

    }
}
