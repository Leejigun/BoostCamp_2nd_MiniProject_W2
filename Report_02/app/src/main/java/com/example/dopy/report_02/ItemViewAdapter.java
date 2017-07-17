package com.example.dopy.report_02;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dopy.report_02.model.DataBaseManager;
import com.example.dopy.report_02.model.Item;

import java.util.ArrayList;

/**
 * Created by Dopy on 2017-07-14.
 */

public class ItemViewAdapter extends RecyclerView.Adapter<ItemViewAdapter.RestaurantViewHolder> {

    ArrayList<Item> arrayList;
    Context context;

    public ItemViewAdapter(Context context,ArrayList<Item> itemArrayList) {
        this.context = context;
        this.arrayList=itemArrayList;
    }

    @Override
    public RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RestaurantViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cardview,parent,false));
    }

    @Override
    public void onBindViewHolder(RestaurantViewHolder holder, int position) {
        holder.bindView(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    /*TODO: Clearly naming
    Class명이 첫 문자가 소문자인 부분을 확인 해주시기 바랍니다.
    또한, Base인 경우는 해당 VH의 사용용도가 불분명하니 명확한 네이밍을 사용해주시기 바랍니다.*/
    class RestaurantViewHolder extends RecyclerView.ViewHolder{
        ImageView imgView;
        TextView name;
        TextView contents;
        ImageView imgClick;

        public RestaurantViewHolder(View itemView) {
            super(itemView);
            imgView=(ImageView)itemView.findViewById(R.id.imgMain);
            name=(TextView)itemView.findViewById(R.id.txtName);
            contents=(TextView)itemView.findViewById(R.id.txtContents);
            imgClick=(ImageView)itemView.findViewById(R.id.imgClick);
        }

        public void bindView(final Item item){
            imgView.setImageResource(item.getImagePath());
            name.setText(item.getName());
            contents.setText(item.getContents());
            if(item.getFavorite()){
                imgClick.setImageResource(R.drawable.ic_clicked);
            }else{
                imgClick.setImageResource(R.drawable.ic_click);
            }

            imgClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("BaseViewHolder","Clocked imgListener");
                    item.clickFavoriteMark();
                    DataBaseManager.getInstance().updateData(item);
                    bindView(item);
                }
            });
        }


    }
}
