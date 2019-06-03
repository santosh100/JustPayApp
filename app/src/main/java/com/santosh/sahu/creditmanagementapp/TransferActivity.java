package com.santosh.sahu.creditmanagementapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TransferActivity extends AppCompatActivity {

    DatabaseHelper mydb;
    Button view_trasaction;
    Button send_money;
    EditText amount_EditText;

    private String ac_no = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        if (savedInstanceState == null){
            Bundle bundle = getIntent().getExtras();
            if (bundle != null){
                ac_no = bundle.getString("AC_NO_WHICH_I_NEED");
            }
        }

        mydb = new DatabaseHelper(this);

        view_trasaction = findViewById(R.id.view_transaction_button);
        send_money = findViewById(R.id.send_money_button);
        amount_EditText = findViewById(R.id.amount_EditText);

        showTransaction();
        sendMoneyTo();
    }

    public void showTransaction(){
        view_trasaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getParticularFromTransactionTable("select * from transaction_table where USER1_AC_NO = ?", new String[] {ac_no});
                if(res.getCount() == 0){
                    showMessage("Transaction","You have zero transaction yet !");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){

                    if (res.getInt(1)==0){
                        buffer.append("RECEIVED FROM ac_no : "+res.getString(3)+"\n");
                        buffer.append("Rs. :"+res.getString(2)+"\n");
                    }else {
                        buffer.append("PAID TO ac_no : "+res.getString(3)+"\n");
                        buffer.append("Rs. :"+res.getString(1)+"\n");
                    }
                    buffer.append("Date : "+res.getString(4)+"\n\n");
                }

                // Show all data
                showMessage("Transaction",buffer.toString());
            }
        });
    }

    public void showMessage(String title, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.show();
    }

    public void sendMoneyTo(){
        send_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(amount_EditText.getText().length()==0){
                    Toast.makeText(getApplicationContext(),"Enter Amount",Toast.LENGTH_SHORT).show();
                }else {
                    int amount = Integer.parseInt("" + amount_EditText.getText());
                    if (amount<1){
                        Toast.makeText(getApplicationContext(),"Amount should be greater than 0",Toast.LENGTH_SHORT).show();
                    }else{

                        Cursor res1 =  mydb.getAllData("select * from user_table where ac_no = ?",ac_no);
                        if (res1.moveToNext()){

                            int availAmount = res1.getInt(4);

                            if (availAmount>amount){
                                Intent intent = new Intent(getApplicationContext(), UsersActivity.class);
                                intent.putExtra("AC_NO_WHICH_I_NEED",ac_no);
                                intent.putExtra("AMOUNT_I_NEED", amount);
                                startActivity(intent);
                            }else {
                                Toast.makeText(getApplicationContext(),"Sorry you don't have enough balance", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });
    }
}
