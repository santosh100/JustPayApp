package com.santosh.sahu.creditmanagementapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    Context mContext;
    List<Users> mData;
    MyViewHolder myViewHolder;

    private MyOnItemClickListener myOnItemClickListener;

    public interface MyOnItemClickListener {
        void myOnItemClick(int position);
    }

    public void mySetOnItemClickListener(MyOnItemClickListener myOnItemClickListener){
        this.myOnItemClickListener = myOnItemClickListener;
    }

    public RecyclerViewAdapter(Context mContext, List<Users> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {

        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item,viewGroup,false);
        myViewHolder = new MyViewHolder(v,myOnItemClickListener);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.tv_name.setText(mData.get(i).getName());
        myViewHolder.tv_ac_number.setText(mData.get(i).getAc_number());
        myViewHolder.imageView.setImageResource(mData.get(i).getPhoto());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private CardView cv_users;
        private TextView tv_name;
        private TextView tv_ac_number;
        private ImageView imageView;

        public MyViewHolder(@NonNull View itemView, final MyOnItemClickListener myOnItemClickListener) {
            super(itemView);

            cv_users = itemView.findViewById(R.id.users_cardview);
            tv_name =  itemView.findViewById(R.id.name_textView);
            tv_ac_number =  itemView.findViewById(R.id.ac_number);
            imageView = itemView.findViewById(R.id.more_imageview);

            cv_users.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (myOnItemClickListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            // CODE TO CALL SELECTED Person
                            myOnItemClickListener.myOnItemClick(position);

                        }
                    }
                }
            });
        }
    }
}
