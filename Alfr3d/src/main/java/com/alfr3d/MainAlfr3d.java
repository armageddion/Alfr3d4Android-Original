package com.alfr3d;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

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

    public void startVideo(View view) {
        Intent intent = new Intent(this, VideoActivity.class);
        startActivity(intent);
    }

    public void sendBlink(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, CallResponse.class);
        String message = "Blink";
        //intent.putExtra(EXTRA_MESSAGE, message);
        //startActivity(intent);

        SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String alfr3d_url= mySharedPreferences.getString("alfr3d_url_preference","url not set");
        String full_alfr3d_call = alfr3d_url+"/cgi-bin/alfr3d.cgi?command="+message;

        // curl: "http://alfr3d.no-ip.org/cgi-bin/test2.py?command=Blink"
        if (!full_alfr3d_call.substring(0,7).equals("http://"))
        {
            full_alfr3d_call = "http://"+full_alfr3d_call;
        }

        final String finalCall = full_alfr3d_call;

        new Thread() {
            public void run() {
                try{
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpResponse response;
                    String responseString = null;
                    try {
                        response = httpclient.execute(new HttpGet(finalCall));
                        StatusLine statusLine = response.getStatusLine();
                        if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                            ByteArrayOutputStream out = new ByteArrayOutputStream();
                            response.getEntity().writeTo(out);
                            out.close();
                            responseString = out.toString();
                        } else{
                            //Closes the connection.
                            response.getEntity().getContent().close();
                            throw new IOException(statusLine.getReasonPhrase());
                        }
                    } catch (ClientProtocolException e) {
                        //TODO Handle problems..
                    } catch (IOException e) {
                        //TODO Handle problems..
                    }
                }
                catch (Exception e) {
                    Log.e("tag", e.getMessage());
                }
            }
        }.start();
    }

    public void sendButtonBlink(View view) {
        sendButtonCommand("Blink");
    }

    public void sendButtonWelcome(View view) {
        sendButtonCommand("welcomehome");
    }

    public void sendButtonCommand(String Command) {
        // Do something in response to button
        Intent intent = new Intent(this, CallResponse.class);
        String message = Command;

        SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String alfr3d_url= mySharedPreferences.getString("alfr3d_url_preference","url not set");
        String full_alfr3d_call = alfr3d_url+"/cgi-bin/alfr3d.cgi?command="+message;

        boolean node_enabled = mySharedPreferences.getBoolean("node_enabled",false);
        if (node_enabled == true)
        {
            //TODO Finish this bit
            full_alfr3d_call = alfr3d_url+"/complete this bit when you have it working in node";
        }


        // curl: "http://alfr3d.no-ip.org/cgi-bin/test2.py?command=Blink"
        if (!full_alfr3d_call.substring(0,7).equals("http://"))
        {
            full_alfr3d_call = "http://"+full_alfr3d_call;
        }

        final String finalCall = full_alfr3d_call;

        new Thread() {
            public void run() {
                try{
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpResponse response;
                    String responseString = null;
                    try {
                        response = httpclient.execute(new HttpGet(finalCall));
                        StatusLine statusLine = response.getStatusLine();
                        if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                            ByteArrayOutputStream out = new ByteArrayOutputStream();
                            response.getEntity().writeTo(out);
                            out.close();
                            responseString = out.toString();
                        } else{
                            //Closes the connection.
                            response.getEntity().getContent().close();
                            throw new IOException(statusLine.getReasonPhrase());
                        }
                    } catch (ClientProtocolException e) {
                        //TODO Handle problems..
                    } catch (IOException e) {
                        //TODO Handle problems..
                    }
                }
                catch (Exception e) {
                    Log.e("tag", e.getMessage());
                }
            }
        }.start();
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
