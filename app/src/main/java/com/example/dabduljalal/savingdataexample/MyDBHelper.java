package com.example.dabduljalal.savingdataexample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by DAbduljalal on 10/11/2015.
 */
public class MyDBHelper extends SQLiteOpenHelper{


    public MyDBHelper(Context context)
    {
        super(context, SaveContract.DATABASE_NAME, null, SaveContract.DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(this.getClass().toString(),"Creating table");
        sqLiteDatabase.execSQL(SaveContract.CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
