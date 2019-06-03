package com.santosh.sahu.creditmanagementapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class TransferFragment extends Fragment {

    private RecyclerView myrecyclerview;
    private RecyclerViewAdapter mAdapter;
    private List<Users> lstUsers;
    private String ac_no;

    private DatabaseHelper mydb;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_transfer,null);

        myrecyclerview = v.findViewById(R.id.users_recyclerview);
        mAdapter = new RecyclerViewAdapter(getContext(),lstUsers);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.setAdapter(mAdapter);

        mAdapter.mySetOnItemClickListener(new RecyclerViewAdapter.MyOnItemClickListener() {
            @Override
            public void myOnItemClick(int position) {
                ac_no = lstUsers.get(position).getAc_number();

                Intent intent = new Intent(getContext(),TransferActivity.class);
                intent.putExtra("AC_NO_WHICH_I_NEED",ac_no);
                startActivity(intent);

            }
        });

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mydb = new DatabaseHelper(getContext());

        lstUsers = new ArrayList<>();
        Cursor res = mydb.getAllData();
        if(res.getCount() == 0){
            return;
        }

        while (res.moveToNext()){
            lstUsers.add(new Users(res.getString(1),res.getString(0),R.drawable.ic_send));
        }
    }
}
