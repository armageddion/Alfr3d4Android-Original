package com.alfr3d;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
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

public class CallResponse extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);

        // Get the message from the intent
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainAlfr3d.EXTRA_MESSAGE);

        // Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // get settings
        SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String alfr3d_url= mySharedPreferences.getString("alfr3d_url_preference","alfr3d");
        String full_alfr3d_call = alfr3d_url+"/cgi-bin/test2.py?command="+message;

        // curl: "http://alfr3d.no-ip.org/cgi-bin/test2.py?command=Blink"
        if (full_alfr3d_call.substring(0,7).equals("http://"))
        {
            new RequestTask().execute(full_alfr3d_call);
        }
        else
        {
            new RequestTask().execute("http://"+full_alfr3d_call);
        }

        // Create the text view
        TextView Alfr3dURLView = (TextView) findViewById(R.id.alfr3d_url);
        Alfr3dURLView.setTextSize(20);
        Alfr3dURLView.setText("Alfr3d URL: "+alfr3d_url);

        // Create the text view
        TextView MessageView = (TextView) findViewById(R.id.message);
        MessageView.setTextSize(20);
        MessageView.setText("Command: "+message);


        // Create the text view
        TextView full_callView = (TextView) findViewById(R.id.full_call);
        full_callView.setTextSize(20);
        full_callView.setText("Full URL Call: \n"+full_alfr3d_call);

        // Set the text view as the activity layout
        //setContentView(textView);
    }

    /**
     * Set up the {@link android.app.ActionBar}.
     */
    private void setupActionBar() {
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.call_response, menu);
        return true;
    }
    

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}

//"http://alfr3d.no-ip.org/cgi-bin/test2.py?command=Blink"
class RequestTask extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... uri) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        try {
            response = httpclient.execute(new HttpGet(uri[0]));
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
        return responseString;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        //Do anything with response..
    }
}