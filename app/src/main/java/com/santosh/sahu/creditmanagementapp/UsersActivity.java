package com.santosh.sahu.creditmanagementapp;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UsersActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerViewAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    private List<Users> lstUsers;
    private DatabaseHelper mydb;

    private String user1_ac_no;
    private String user2_ac_no;
    private int amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                user1_ac_no = bundle.getString("AC_NO_WHICH_I_NEED");
                amount = bundle.getInt("AMOUNT_I_NEED");
            }
        }

        mydb = new DatabaseHelper(this);
        lstUsers = new ArrayList<>();
        final Cursor res = mydb.getAllData("select * from user_table where ac_no != ?", user1_ac_no);
        if (res.getCount() == 0) {
            return;
        }

        while (res.moveToNext()) {
            lstUsers.add(new Users(res.getString(1), res.getString(0),R.drawable.ic_send));
        }

        mRecyclerView = findViewById(R.id.users_activity_recyclerview);
        mLayoutManager = new LinearLayoutManager(UsersActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new RecyclerViewAdapter(UsersActivity.this, lstUsers);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.mySetOnItemClickListener(new RecyclerViewAdapter.MyOnItemClickListener() {
            @Override
            public void myOnItemClick(int position) {
                // transfer money here
                user2_ac_no = lstUsers.get(position).getAc_number();

                Cursor res1 = mydb.getAllData("select * from user_table where ac_no = ?", user1_ac_no);
                Cursor res2 = mydb.getAllData("select * from user_table where ac_no = ?", user2_ac_no);
                if (res1.moveToNext() && res2.moveToNext()) {

                    int user1availAmount = res1.getInt(4);
                    int user1NewAmount = user1availAmount - amount;

                    int user2AvailAmount = res2.getInt(4);
                    int user2NewAmount = user2AvailAmount + amount;

                    boolean result1 = mydb.updateData(user1_ac_no, res1.getString(1), res1.getString(2), res1.getString(3), user1NewAmount);
                    boolean result2 = mydb.updateData(user2_ac_no, res2.getString(1), res2.getString(2), res2.getString(3), user2NewAmount);
                    if (result1 && result2) {

                        Date c = Calendar.getInstance().getTime();
                        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                        String tras_date = df.format(c);

                        //  String tras_date = "18/02/2019";

                        boolean status1 = mydb.insertIntoTransactionTable(user1_ac_no, amount, 0, user2_ac_no, tras_date);
                        boolean status2 = mydb.insertIntoTransactionTable(user2_ac_no, 0, amount, user1_ac_no, tras_date);
                        if (status1) {
                            if (status2){
                                Toast.makeText(getApplicationContext(), +amount + " transaction successful", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getApplicationContext(), " transaction failed (problem in second table)", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), " transaction failed (problem in first table)", Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }else {
                        Log.e("Problem","Problem while updating");
                    }
                }
            }
        });
    }
}
