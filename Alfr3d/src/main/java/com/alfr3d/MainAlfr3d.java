package com.alfr3d;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainAlfr3d extends Activity {
    public final static String EXTRA_MESSAGE = "com.test2.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alfr3d_main);

        SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String alfr3d_url= mySharedPreferences.getString("alfr3d_url_preference","alfr3d");

        // Create the text view
        TextView Alfr3dURLView = (TextView) findViewById(R.id.current_url);
        Alfr3dURLView.setText("Alfr3d URL: "+alfr3d_url);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main_activity__test2, menu);
        //return true;

        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_alfr3d_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /** Called when the user clicks the Send button */
    /*
    In order for the system to match this method to the method name given to android:onClick,
    the method must be public,return void value, and have View as the only parameter
     */
    public void sendMessage(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, CallResponse.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void sendBlink(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, CallResponse.class);
        String message = "Blink";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_search:
                //openSearch();
                return true;
            case R.id.action_settings:
                //openSettings();
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.action_reset:
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
