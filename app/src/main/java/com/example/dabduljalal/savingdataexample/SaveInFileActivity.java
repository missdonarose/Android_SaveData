package com.example.dabduljalal.savingdataexample;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Save in file:
 * a. Internal
 * b. External: Didnot work as it gave disk not mounted error.
 */
public class SaveInFileActivity extends AppCompatActivity {

    private static final String FILE_NAME = "com.example.dabduljalal.savingdataexample.MyFile";
    File myFile;
    @Override
    /**
     * This class saves data to shared preferences file
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button saveButton = (Button) findViewById(R.id.save_button_id);
        Button showButton = (Button) findViewById(R.id.show_button_id);
        final EditText insertText = (EditText)findViewById(R.id.insert_text_id);
        final TextView textView = (TextView)findViewById(R.id.show_text_id);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String data = insertText.getText().toString();
                saveInInternalFile(data);
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

                textView.setText(readFromInternalFile());

                CharSequence message = "Reading file data ";
                Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Saves in internal file
     * @param data
     */
    public void saveInInternalFile(String data)
    {
        try {
            if(null==myFile) {
                myFile = new File(getFilesDir(), FILE_NAME);
            }
            FileWriter writer = new FileWriter(myFile, true);
            writer.write(data);
            writer.write("\n");
            writer.close();
            CharSequence message = "Saved data in a file";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }catch(FileNotFoundException fe) {
            Log.e(this.getClass().toString(),fe.getMessage());
        }catch(IOException fe) {
            Log.e(this.getClass().toString(),fe.getMessage());
        }
    }

    public void saveInExternalFile(String data)
    {
            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                try {
                    if (null == myFile) {
                    myFile = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), FILE_NAME);
                }
                FileWriter writer = new FileWriter(myFile, true);
                writer.write(data);
                writer.write("\n");
                writer.close();
                    CharSequence message = "Saved data in a file";
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }catch(FileNotFoundException fe) {
            Log.e(this.getClass().toString(),fe.getMessage());
        }catch(IOException fe) {
            Log.e(this.getClass().toString(),fe.getMessage());
        }
            }
            else
                Toast.makeText(this,"External media not mounted "+Environment.getExternalStorageState(),Toast.LENGTH_SHORT).show();
    }

    public String readFromInternalFile()
    {
        StringBuilder finalData = new StringBuilder();
        try{
           /* FileReader reader = new FileReader(new File(getFilesDir(),FILE_NAME));
            reader.read(data);
            reader.close();*/
            Scanner scanner = new Scanner(new FileInputStream(new File(getFilesDir(),FILE_NAME)));

            while(scanner.hasNextLine())
            {
                finalData.append(scanner.nextLine()+"\n");
            }
            scanner.close();
        }catch(FileNotFoundException fe)
        {
            Log.e(this.getClass().toString(),fe.getMessage());
        }
        catch(IOException fe)
        {
            Log.e(this.getClass().toString(),fe.getMessage());
        }

        return finalData.toString();
    }

    public String readFromExternalFile()
    {
        char[] data = new char[100];
        String finalData = "";
        try{
            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                FileReader reader = new FileReader(new File(
                        getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), FILE_NAME
                ));
                reader.read(data);
                reader.close();
            }else
                Toast.makeText(this,"Media not mounted. "+Environment.getExternalStorageState(),Toast.LENGTH_SHORT).show();
        }catch(FileNotFoundException fe)
        {
            Log.e(this.getClass().toString(),fe.getMessage());
        }
        catch(IOException fe)
        {
            Log.e(this.getClass().toString(),fe.getMessage());
        }

        finalData = String.valueOf(data);
        return finalData;
    }

    protected void onDestroy()
    {
        //Set myfile to null so that only data for the current session is saved.
        //If you wish to persist data permanantly, omit this step.
        if(myFile != null)
            myFile.delete();
        super.onDestroy();
    }
}

