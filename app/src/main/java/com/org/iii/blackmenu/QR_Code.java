package com.org.iii.blackmenu;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class QR_Code extends AppCompatActivity {
    private Button button;
    private Camera camera;
    private TextView textView;
    private String re;
    private SweetAlertDialog showDL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr__code);

        if (ContextCompat.checkSelfPermission(QR_Code.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(QR_Code.this,
                    new String[]{Manifest.permission.CAMERA
                    },
                    123);
        } else {
            init();
        }
    }

    public void init() {
        try {
            camera = Camera.open(2);


        } catch (Exception e) {
            Log.v("ppking", "failed to open Camera");
            e.printStackTrace();
        }
        IntentIntegrator integrator = new IntentIntegrator(QR_Code.this);
        integrator.initiateScan();
    }

    private void releaseCameraAndPreview() {
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        re = scanResult.getContents();

        if (re != null) {
            releaseCameraAndPreview();

            new SweetAlertDialog(QR_Code.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                    .setTitleText("確定要坐" + re + "號桌?")
                    .setCustomImage(R.drawable.seat)
                    .setCancelText("取消")
                    .setConfirmText("確定入座")
                    .showCancelButton(true)
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
//                            Log.v("will", "??");
                            finish();
                        }
                    }).setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
//                    Log.v("will", "OK");
                    Intent it = new Intent(QR_Code.this,MainActivity.class);
                    it.putExtra("ppking" , re);
                    startActivity(it);
                    finish();
                }
            }).show();

        } else {
            finish();
        }
        // else continue with any other code you need in the method
        super.onActivityResult(requestCode, resultCode, intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        init();
    }
}
