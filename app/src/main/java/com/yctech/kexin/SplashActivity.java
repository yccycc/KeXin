package com.yctech.kexin;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    PackageManager packageManager;
    PackageInfo packageInfo;
    int versionCode;
    String versionName;
    TextView showTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        packageManager = getPackageManager();
        showTv = (TextView) findViewById(R.id.splash_tv);
        try {
            packageInfo = packageManager.getPackageInfo("com.yctech.kexin",0);
            versionCode = packageInfo.versionCode;
            versionName = packageInfo.versionName;
            showTv.setText("version:"+versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SplashActivity.this.startActivity(new Intent(SplashActivity.this,MainActivity.class));
                SplashActivity.this.finish();
            }
        },1500);
    }
}
