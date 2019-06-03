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

public class UsersFragment extends Fragment {

    private RecyclerView myrecyclerview;
    private RecyclerViewAdapter mAdapter;
    private List<Users> lstUsers;
    private String ac_no;

    private DatabaseHelper mydb;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_users,container,false);

        myrecyclerview = v.findViewById(R.id.users_recyclerview);
        mAdapter = new RecyclerViewAdapter(getContext(),lstUsers);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.setAdapter(mAdapter);

        mAdapter.mySetOnItemClickListener(new RecyclerViewAdapter.MyOnItemClickListener() {
            @Override
            public void myOnItemClick(int position) {
                ac_no = lstUsers.get(position).getAc_number();

                Intent intent = new Intent(getContext(), UserProfileActivity.class);
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

      //  insertAll();    // will work for first time

        lstUsers = new ArrayList<>();
        Cursor res = mydb.getAllData();
        if(res.getCount() == 0){
            return;
        }

        while (res.moveToNext()){
            lstUsers.add(new Users(res.getString(1),res.getString(0),R.drawable.ic_users));
        }
    }

    private void insertAll(){
        Cursor res = mydb.getAllData();
        if(res.getCount() == 0) {
            mydb.insertData("12345678911", "VAIBHAV TIWARI", "vt@gmail.com", "1234567899", 100000);
            mydb.insertData("22356456787", "SANTOSH SAHU", "ss@gmail.com", "2234567899", 200);
            mydb.insertData("32345678909", "RINKU SHARMA", "rrs@gmail.com", "3234567899", 300);
            mydb.insertData("42356789098", "PIYUSH SHARMA", "ps@gmail.com", "4234567899", 400);
            mydb.insertData("52345678909", "DIKSHIKA RANI", "ds@gmail.com", "5234567899", 500);
            mydb.insertData("62345678909", "MEGHA GUPTA", "ms@gmail.com", "6234567899", 600);
            mydb.insertData("72345656578", "NEHA SINGH", "ns@gmail.com", "7234567899", 700);
            mydb.insertData("82356765676", "ABHAY GUPTA", "as@gmail.com", "8234567899", 800);
            mydb.insertData("92378767876", "KHUSHI CHANDEL", "ks@gmail.com", "9234567899", 900);
            boolean status = mydb.insertData("99912345678", "RAJEEV TRIPATHI", "rrrs@gmail.com", "9334567899", 900);
            if (status == true) {
                Log.e("Successful", "Data inserted");
            } else {
                Log.e("failed", "problem in insertion");
            }
        }
    }
}
