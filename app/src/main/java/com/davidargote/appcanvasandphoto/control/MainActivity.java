package com.davidargote.appcanvasandphoto.control;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.davidargote.appcanvasandphoto.R;
import com.davidargote.appcanvasandphoto.model.CanvasDraw;
import com.frosquivel.magicalcamera.MagicalCamera;
import com.frosquivel.magicalcamera.MagicalPermissions;

import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnClear, btnPincel, btnTakePhoto, btnSavePhoto;
    private CanvasDraw canvasDraw;
    private MagicalCamera magicalCamera;

    private Bitmap photoBg;

    private static String[] MY_PERMISSONS = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
            , Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        initViews();

        MagicalPermissions permissions = new MagicalPermissions(this, MY_PERMISSONS);
        magicalCamera = new MagicalCamera(this, 80, permissions);

    }

    private void initViews() {

        btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        btnPincel = findViewById(R.id.btnPincel);
        btnPincel.setOnClickListener(this);

        btnTakePhoto = findViewById(R.id.btnTakePhoto);
        btnTakePhoto.setOnClickListener(this);

        btnSavePhoto = findViewById(R.id.btnSavePhoto);
        btnSavePhoto.setOnClickListener(this);

        canvasDraw = findViewById(R.id.canvasDraw);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnClear:
                canvasDraw.setClear(true);
                break;

            case R.id.btnPincel:
                canvasDraw.setClear(false);
                break;

            case R.id.btnTakePhoto:
                magicalCamera.takePhoto();
                break;

            case R.id.btnSavePhoto:
                savePhoto();
                break;

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        magicalCamera.resultPhoto(requestCode, resultCode, data, MagicalCamera.ORIENTATION_ROTATE_90);
        photoBg = magicalCamera.getPhoto();

        Drawable photoDrawable = new BitmapDrawable(getResources(), photoBg);
        canvasDraw.setBackground(photoDrawable);

    }

    private void savePhoto() {

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Save photo");
        alert.setMessage("¿Deseas guardar la photo en tu galeria?");
        alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                canvasDraw.setDrawingCacheEnabled(true);

                String imgSaved = MediaStore.Images.Media.insertImage(getContentResolver(),
                        canvasDraw.getDrawingCache(), UUID.randomUUID().toString()+".png", "Photo");

                if (imgSaved!=null)Toast.makeText(MainActivity.this, "Foto guardada", Toast.LENGTH_SHORT).show();
                else Toast.makeText(MainActivity.this, "La foto no se guardo", Toast.LENGTH_SHORT).show();

                canvasDraw.destroyDrawingCache();

            }
        });
        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        alert.create();
        alert.show();

    }
}
