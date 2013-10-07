package com.alfr3d;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainAlfr3d extends Activity {
    public final static String EXTRA_MESSAGE = "com.test2.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alfr3d_main);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_search:
                //openSearch();
                return true;
            case R.id.action_settings:
                //openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
