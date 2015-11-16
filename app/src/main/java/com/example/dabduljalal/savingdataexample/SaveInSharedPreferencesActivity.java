package com.example.dabduljalal.savingdataexample;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.Set;

public class SaveInSharedPreferencesActivity extends AppCompatActivity {

    SharedPreferences mSharedPreferences;
    int count = 0;
    private static final String KEY_TEXT = "KEY_TEXT";
    private static final String myPreferences = "com.example.dabduljalal.savingdataexample.MY_PREFERENCE_FILE";

    @Override
    /**
     * This class saves data to shared preferences file
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSharedPreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);

        Button saveButton = (Button) findViewById(R.id.save_button_id);
        Button showButton = (Button) findViewById(R.id.show_button_id);
        final EditText insertText = (EditText)findViewById(R.id.insert_text_id);
        final TextView textView = (TextView)findViewById(R.id.show_text_id);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence message = "Saving data using shared preferences";
                Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();

                mSharedPreferences.edit().putString(
                        KEY_TEXT + Integer.toString(count), insertText.getText().toString())
                        .commit();
                count++;
                insertText.setText("");
            }
        });

        /**
         * Retrieve data from shared prefs
         * Onsert newline between them.
         * Show them on textView
         */
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence savedData = "";
                for(int i=0;i<count;i++)
                {
                    savedData = savedData.toString().concat(
                            mSharedPreferences.getString(KEY_TEXT+Integer.toString(i)
                            ,""));
                    savedData = savedData.toString().concat("\n");
                }
                textView.setText(savedData);

                CharSequence message = "Showing saved data "+savedData;
                Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
