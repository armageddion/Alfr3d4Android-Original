package com.alfr3d;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.VideoView;

public class VideoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        VideoView vidView = (VideoView)findViewById(R.id.myVideo);

        String vidAddress = "http://alfr3d.no-ip.org:8181/video";
        Uri vidUri = Uri.parse(vidAddress);

        vidView.setVideoURI(vidUri);
        vidView.start();
    }
}
