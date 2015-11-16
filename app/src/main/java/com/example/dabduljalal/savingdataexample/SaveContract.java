package com.example.dabduljalal.savingdataexample;

import android.provider.BaseColumns;

/**
 * Created by DAbduljalal on 10/11/2015.
 * Contract file containing constants for use in DB access
 */
public final class SaveContract {

    public SaveContract(){
    }

    public static final String DATABASE_NAME = "MYDB";
    public static final int DATABASE_VERSION=1;
    public static final String CREATE_QUERY = "CREATE TABLE "+MYTable.TABLE_NAME+" (" +
            MYTable._ID+" INTEGER PRIMARY KEY,"
            + MYTable.DATA_COLUMN+" TEXT"+
            ")";

    public static final String DELETE_QUERY = "DROP TABLE IF EXISTS "+MYTable.TABLE_NAME;

    /*
    IMplementing Basecolumns automatically adds a column named _id
     */
    public static abstract class MYTable implements BaseColumns
    {
        public static final String TABLE_NAME = "data_table";
        public static final String DATA_COLUMN = "DATA";

    }
}
