package com.santosh.sahu.creditmanagementapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class UserProfileActivity extends AppCompatActivity {
    DatabaseHelper mydb;
    private TextView user_profile_textView;

    private String ac_no = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        if (savedInstanceState == null){
            Bundle bundle = getIntent().getExtras();
            if (bundle != null){
                ac_no = bundle.getString("AC_NO_WHICH_I_NEED");
            }
        }

        mydb = new DatabaseHelper(this);

        user_profile_textView = findViewById(R.id.user_profile_textView);


        setUserDetail();
    //    showTransaction();
    //    sendMoneyTo();
    }

    private void setUserDetail(){
        Cursor res = mydb.getAllData("select * from user_table where AC_NO = ?",ac_no);
        if (res.moveToNext()){
            StringBuffer buffer = new StringBuffer();
            buffer.append("AC_NO   : "+res.getString(0)+"\n");
            buffer.append("Name    : "+res.getString(1)+"\n");
            buffer.append("Gmail   : "+res.getString(2)+"\n");
            buffer.append("Phone   : "+res.getString(3)+"\n");
            buffer.append("Balance : "+res.getString(4)+"\n");

            user_profile_textView.setText(buffer.toString());
        }
    }
}
