package com.davidargote.appcanvasandphoto.control;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.davidargote.appcanvasandphoto.R;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnPhoto, btnVideo;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initViews();

    }

    private void initViews() {

        btnPhoto = findViewById(R.id.btnGoPhoto);
        btnPhoto.setOnClickListener(this);

        btnVideo = findViewById(R.id.btnGoVideo);
        btnVideo.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btnGoPhoto:
                intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.btnGoVideo:
                intent = new Intent(MenuActivity.this, VideoActivity.class);
                startActivity(intent);
                break;

        }

    }
}
