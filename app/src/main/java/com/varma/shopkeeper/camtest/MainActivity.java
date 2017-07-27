package com.varma.shopkeeper.camtest;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;


public class MainActivity extends AppCompatActivity {

    GetCam getCam1,getCam2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        runApp();

    }

    private void runApp(){
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},100);

        }else{
            getCam1 = new GetCam(this,(SurfaceView) findViewById(R.id.surfaceViewCamera1));
            getCam2 = new GetCam(this,(SurfaceView) findViewById(R.id.surfaceViewCamera2));
            try {
                getCam1.setCam(0);
                getCam2.setCam(1);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }


    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if((grantResults.length>0)&&(grantResults[0]==PackageManager.PERMISSION_GRANTED)){
            Intent intent = getIntent();
            finish();
            startActivity(intent);

        }


    }
}