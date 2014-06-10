package com.alfr3d;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread welcomeThread = new Thread(){

            @Override
            public void run(){
                try{
                    super.run();
                    sleep(3000);   // delay 10 sec
                } catch (Exception e){
                    //TODO
                } finally {
                    Intent i = new Intent(SplashActivity.this,MainAlfr3d.class);
                    startActivity(i);
                    finish();
                }
            }
        };
        welcomeThread.start();
    }
}
