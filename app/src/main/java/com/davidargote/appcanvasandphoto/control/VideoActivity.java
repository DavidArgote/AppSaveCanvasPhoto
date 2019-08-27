package com.davidargote.appcanvasandphoto.control;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.davidargote.appcanvasandphoto.R;

public class VideoActivity extends AppCompatActivity {

    private Button btnRecordVideo;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        initViews();

        videoView = new VideoView(this);

    }

    private void initViews() {

        btnRecordVideo = findViewById(R.id.btnRecordVideo);
        btnRecordVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordVideo();
            }
        });

    }

    private void recordVideo() {

        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(takeVideoIntent, 111);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 111 && requestCode == RESULT_OK){
            Uri videoUri = getIntent().getData();
            videoView.setVideoURI(videoUri);
        }

    }
}
