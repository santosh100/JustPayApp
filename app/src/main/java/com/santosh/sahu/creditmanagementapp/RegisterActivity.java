package com.santosh.sahu.creditmanagementapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {


    private DatabaseHelper mydb;

    private EditText name, ac_no, email_id, mob_no, balance;

    private Button submit_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mydb = new DatabaseHelper(this);

        name = findViewById(R.id.name_editText);
        ac_no = findViewById(R.id.ac_number_editText);
        email_id = findViewById(R.id.emailid_editText);
        mob_no = findViewById(R.id.mob_no_editText);
        balance = findViewById(R.id.balance_editText);
        submit_btn = findViewById(R.id.submit_button);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {


                    String u_name = name.getText().toString().trim();
                    String u_ac = ac_no.getText().toString().trim();
                    String u_mail = email_id.getText().toString().trim();
                    String u_mob = mob_no.getText().toString();
                    String bal = balance.getText().toString().trim();

                    if (bal.length() <= 0 || u_mob.length() != 10 || u_name.length() <= 0 || u_ac.length() != 11 || u_mail.length() <= 0) {
                        Toast.makeText(getApplicationContext(), "Enter Valid data", Toast.LENGTH_SHORT).show();
                    } else {
                        int u_bal = Integer.parseInt(bal);

                        if(u_bal <=0){
                            Toast.makeText(getApplicationContext(), "Enter Valid balance", Toast.LENGTH_SHORT).show();
                        }else{
                            boolean status = mydb.insertData(u_ac, u_name, u_mail, u_mob, u_bal);

                            if (status) {

                                Toast.makeText(getApplicationContext(), "Successfully Added", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "Problem in Registration", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }


                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Problem in Registration", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}
