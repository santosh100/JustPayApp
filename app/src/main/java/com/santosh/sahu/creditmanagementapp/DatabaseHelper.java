package com.santosh.sahu.creditmanagementapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "mydata1.db";
    public static final String TABLE1_NAME = "user_table";
    public static final String T1_COL_1 = "AC_NO";
    public static final String T1_COL_2 = "NAME";
    public static final String T1_COL_3 = "GMAIL";
    public static final String T1_COL_4 = "MOB_NO";
    public static final String T1_COL_5 = "AVAIL_BAL";

    public static final String TABLE2_NAME = "transaction_table";
    public static final String T2_COL_1 = "USER1_AC_NO";
    public static final String T2_COL_2 = "DEBIT_AMOUNT";
    public static final String T2_COL_3 = "CREDIT_AMOUNT";
    public static final String T2_COL_4 = "USER2_AC_NO";
    public static final String T2_COL_5 = "TRANSACTION_DATE";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
       // SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ TABLE1_NAME +" ("+T1_COL_1+" TEXT, "+T1_COL_2+" TEXT, "+T1_COL_3+" TEXT, "+T1_COL_4+" TEXT, "+T1_COL_5+" INTEGER)");

        db.execSQL("create table "+ TABLE2_NAME +" ("+T2_COL_1+" TEXT,"+T2_COL_2+" INTEGER,"+T2_COL_3+" INTEGER,"+T2_COL_4+" TEXT,"+T2_COL_5+" TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE1_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE2_NAME);
        onCreate(db);

    }

    public boolean insertData(String ac_no,String name, String gmail, String mob_no, int avail_bal){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T1_COL_1,ac_no);
        contentValues.put(T1_COL_2,name);
        contentValues.put(T1_COL_3,gmail);
        contentValues.put(T1_COL_4,mob_no);
        contentValues.put(T1_COL_5,avail_bal);
        long result = db.insert(TABLE1_NAME,null,contentValues);
        if(result == -1)
            return false;
        else
            return true;

    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE1_NAME,null);
        return res;
    }

    public Cursor getAllData(String myquery,String ac_no){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery(myquery, new String[] {ac_no});
        return res;
    }

    public boolean updateData(String ac_no,String name, String gmail, String mob_no, int avail_bal){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T1_COL_1,ac_no);
        contentValues.put(T1_COL_2,name);
        contentValues.put(T1_COL_3,gmail);
        contentValues.put(T1_COL_4,mob_no);
        contentValues.put(T1_COL_5,avail_bal);
        db.update(TABLE1_NAME,contentValues,"AC_NO = ?",new String[] { ac_no });
        return true;
    }


    public boolean insertIntoTransactionTable(String user1_ac_no,int debit_amount, int credit_amount, String user2_ac_no,String tras_date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T2_COL_1,user1_ac_no);
        contentValues.put(T2_COL_2,debit_amount);
        contentValues.put(T2_COL_3,credit_amount);
        contentValues.put(T2_COL_4,user2_ac_no);
        contentValues.put(T2_COL_5,tras_date);
        long result = db.insert(TABLE2_NAME,null,contentValues);
        if(result == -1)
            return false;
        else
            return true;

    }

    public Cursor getAllFromTransactionTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE2_NAME,null);
        return res;
    }

    public Cursor getParticularFromTransactionTable(String myquery, String[] values){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery(myquery, values);
        return res;
    }
}
