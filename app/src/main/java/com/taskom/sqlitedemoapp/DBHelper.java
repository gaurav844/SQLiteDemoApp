package com.taskom.sqlitedemoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {


    public static final String CUSTOMER_TABLE = "CUSTOMER_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_CNAME = "CNAME";
    public static final String COLUMN_CAGE = "CAGE";

    public DBHelper(@Nullable Context context) {
        super(context, "CUSTOMER_DB", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlStatement = "CREATE TABLE " + CUSTOMER_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_CNAME + " TEXT," + COLUMN_CAGE + " TEXT)";
        db.execSQL(sqlStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public boolean addCustomer(CustomerModel model){

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CNAME,model.getCustomer_name());
        values.put(COLUMN_CAGE,model.getCustomer_age());

        long success = database.insert(CUSTOMER_TABLE, null, values);

        database.close();
        if (success == -1){
            return false;
        }else{
            return true;
        }
    }


    public List<CustomerModel> getAllCustomers(){
        List<CustomerModel> returnList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        String sqlStatement = "SELECT * FROM " + CUSTOMER_TABLE;
        Cursor cursor = database.rawQuery(sqlStatement, null);

        boolean hasdata = cursor.moveToFirst();

        if (hasdata == true){

            do {
                int id = cursor.getInt(0);
                String cname = cursor.getString(1);
                int age = cursor.getInt(2);

                CustomerModel model = new CustomerModel(id,age,cname);
                returnList.add(model);

            }while(cursor.moveToNext());

        }else{
            //No Data
        }

        cursor.close();
        database.close();

        return returnList;

    }

    public boolean deleteCustomer(CustomerModel model){

        SQLiteDatabase database = this.getWritableDatabase();
        String[] argsArray = {String.valueOf(model.getCustomer_id())};

        int delete = database.delete(CUSTOMER_TABLE, "" + COLUMN_ID + " = ?", argsArray);

        database.close();

        if (delete == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean updateCustomer(CustomerModel model){

        SQLiteDatabase database = this.getWritableDatabase();
        String[] argsArray = {String.valueOf(model.getCustomer_id())};

        ContentValues values = new ContentValues();

        values.put(COLUMN_CNAME,model.getCustomer_name());
        values.put(COLUMN_CAGE,model.getCustomer_age());


        int update = database.update(CUSTOMER_TABLE,values, "" + COLUMN_ID + " = ?", argsArray);

        database.close();

        if (update == -1){
            return false;
        }else{
            return true;
        }
    }



}
