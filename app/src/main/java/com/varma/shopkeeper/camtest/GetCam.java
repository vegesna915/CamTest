package com.varma.shopkeeper.camtest;


import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

class GetCam implements SurfaceHolder.Callback {
    private SurfaceView surfaceView;
    private CameraManager cameraManager;
    private SurfaceHolder surfaceHolder;
    private CameraDevice.StateCallback camSateCallBack;
    private CameraDevice camDevice;


    GetCam(MainActivity activity,SurfaceView surfaceView) {

        this.surfaceView = surfaceView;
        this.surfaceHolder = surfaceView.getHolder();
        this.cameraManager = (CameraManager) activity.getSystemService(Context.CAMERA_SERVICE);
    }


    void setCam(int camNum) throws CameraAccessException {
        String[] camList = cameraManager.getCameraIdList();
        if (camList.length < camNum) {
            return;
        }

        String camId = camList[camNum];

        setSurfaceHolder();

        try{
            cameraManager.openCamera(camId, camSateCallBack, new Handler());
        }catch (SecurityException e){
            e.printStackTrace();
        }

    }


    private void setSurfaceHolder() {

        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);

        camSateCallBack = new CameraDevice.StateCallback() {
            @Override
            public void onOpened(@NonNull CameraDevice camera) {

                camDevice = camera;
            }

            @Override
            public void onDisconnected(@NonNull CameraDevice camera) {

            }

            @Override
            public void onError(@NonNull CameraDevice camera, int error) {

            }
        };

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        try {
            configCameras();
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    private void configCameras() throws CameraAccessException{

        List<Surface> surfaceList =new ArrayList<>();

        surfaceList.add(surfaceHolder.getSurface());

        camDevice.createCaptureSession(surfaceList, new CameraCaptureSession.StateCallback() {
            @Override
            public void onConfigured(@NonNull CameraCaptureSession session){
                try {

                    CaptureRequest.Builder builder = camDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                    builder.addTarget(surfaceHolder.getSurface());
                    session.setRepeatingRequest(builder.build(),null,null);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onConfigureFailed(@NonNull CameraCaptureSession session) {

            }
        },null);

    }

}
