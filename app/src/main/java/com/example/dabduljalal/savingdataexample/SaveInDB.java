package com.example.dabduljalal.savingdataexample;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * On click of save button, saves data to DB
 * On click of show button, retrieves data from DB.
 * Displays the data read in :
 *  1. Scrollable text view (Commented code. Reads cursor using moveNext() methods. Writes to text view.)
 *  2. ListView: Populates using a simplecursoradapter.
 *              To use this, SaveContract.MyTable should extend BaseColumn, so that the table includes a _id column.
 *              No need to explicitly mention the column if you extend this class.
 *              Note the listview_layout xml to e used for each row in the cursor.
 *              Cursor rows are mentioned as fromColums and textViews in layout xml is mentioned in toViews.
 *              We can also use implicit android layout simple_list_view_1 if there is only 1 column to display.
 * Due to small screen size, either textView or LIstView is seen at a time. To use textView, place it before listView in layout xml.
 */
public class SaveInDB extends AppCompatActivity {

    MyDBHelper openHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button saveButton = (Button) findViewById(R.id.save_button_id);
        Button showButton = (Button) findViewById(R.id.show_button_id);
        final EditText insertText = (EditText) findViewById(R.id.insert_text_id);
        final TextView textView = (TextView) findViewById(R.id.show_text_id);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String data = insertText.getText().toString();
                saveInDB(data);
                insertText.setText("");
            }
        });

        /**
         * Retrieve data from file
         * Show them on textView
         */
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String data = readFromDB();
                //textView.setText(data);
                CharSequence message = "Reading DB data " + data;
                Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void saveInDB(String data) {
        if(openHelper==null)
            openHelper = new MyDBHelper(this);
        SQLiteDatabase db = openHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SaveContract.MYTable.DATA_COLUMN, data);
        db.insert(SaveContract.MYTable.TABLE_NAME, null, values);
    }

    public String readFromDB()
    {
        String data = "";
        if(openHelper==null)
            openHelper = new MyDBHelper(this);
        SQLiteDatabase db = openHelper.getReadableDatabase();
        String[] projection = {SaveContract.MYTable._ID,SaveContract.MYTable.DATA_COLUMN};
        Cursor c = db.query(SaveContract.MYTable.TABLE_NAME,projection,null,null,null,null,null);

        String[] fromColumns = {SaveContract.MYTable._ID,SaveContract.MYTable.DATA_COLUMN};
        int[] toViews = {R.id.text_id,R.id.data_id};

        //Using a simpleCursorAdaptor
        try {
            Log.d(this.getLocalClassName(),"ID column index: "+c.getColumnIndex(SaveContract.MYTable._ID));
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                    this, R.layout.listview_layout, c, fromColumns, toViews, 0);
            ListView listView = (ListView) findViewById(R.id.list_view_id);
            adapter.notifyDataSetChanged();
            listView.setAdapter(adapter);
        }catch(IllegalStateException e)
        {
            Log.d(this.getLocalClassName(),"IllegalStateException "+e.getMessage());
        }catch (IllegalArgumentException e)
        {
            Log.d(this.getLocalClassName(),"IllegalArgumentException "+e.getMessage());
        }
        /*
        Using cursor move methods
        c.moveToFirst();
        do {
            data = data+c.getString(c.getColumnIndex(SaveContract.MYTable.DATA_COLUMN)) + "\n";
        }while(c.moveToNext());

        c.close();*/
        return data;
    }

    public void onDestroy()
    {
       /* if(openHelper==null)
        {
            openHelper = new MyDBHelper(this);
        }
        SQLiteDatabase db = openHelper.getWritableDatabase();
        db.execSQL(SaveContract.DELETE_QUERY);
        Log.d(this.getLocalClassName(),"Deleted table successfully");*/
        if(openHelper!=null)
            openHelper.close();
        super.onDestroy();
    }
}
