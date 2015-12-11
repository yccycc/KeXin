package com.yctech.kexin;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextToSpeech.OnInitListener {
    private static final int THREE = 3;
    private static final int FOUR = 4;
    private static final int FIVE = 5;
    private static final int SIX = 6;
    private static final int SEVEN = 7;
    private static final int EIGHT = 8;
    private static final int NINE = 9;
    private static final int TEN = 10;
    private static final int ELEVEN = 11;
    private static final int TWELVE = 12;
    private Button mStart;
    private TextView mTiShiTv;

    public int getmCount() {
        return mCount;
    }

    public void setmCount(int mCount) {
        this.mCount = mCount;
    }

    private int mCount = 3;
    private MediaPlayer mediaPlayer;
    private Random random;
    private List<Integer> list;
    private int modeFlag = THREE;
    private TextView nowModeTv;
    private Button stopBtn;
    private Button loop;
    private TextToSpeech tts;
    private Boolean readFlag = false;
    PowerManager powerManager;
    PowerManager.WakeLock wakeLock;
    PlayThread playThread;
    LoopThread loopThread;

    private class LoopThread extends Thread{
        @Override
        public void run() {
            super.run();
            while(!Thread.interrupted()){
                try {
                    Thread.sleep(1000*(readFlag? mCount+mCount/2 :mCount+2));
                } catch (InterruptedException e) {
                    break;
                }
                MainActivity.this.getWindow().getDecorView().post(new Runnable() {
                    @Override
                    public void run() {
                        mStart.performClick();
                    }
                });
            }
        }
    }
    private class PlayThread extends Thread{
        @Override
        @SuppressWarnings( "deprecation" )
        public void run() {
            super.run();
            list = new ArrayList<>();
            MainActivity.this.getWindow().getDecorView().post(new Runnable() {
                @Override
                public void run() {
                    mTiShiTv.setText("next");
                }
            });
            for(int i=0 ;i < mCount;i++){
                mediaPlayer = MediaPlayer.create(MainActivity.this,getRandomRawId());
                mediaPlayer.start();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
                if(null!=mediaPlayer){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
            }
            MainActivity.this.getWindow().getDecorView().post(new Runnable() {
                @Override
                public void run() {
                    mTiShiTv.setText(Arrays.toString(list.toArray()));
                    if (readFlag) {
                        tts.setSpeechRate(10);
                        tts.speak(Arrays.toString(list.toArray()), TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
            });
            MainActivity.this.getWindow().getDecorView().post(new Runnable() {
                @Override
                public void run() {
                    mStart.setEnabled(true);
                }
            });
        }
    }

    private void assignViews() {
        mStart = (Button) findViewById(R.id.start);
        mTiShiTv = (TextView) findViewById(R.id.tishiTv);
        nowModeTv = (TextView) findViewById(R.id.now_mode);
        stopBtn = (Button) findViewById(R.id.stop);
        loop = (Button) findViewById(R.id.loop);
    }

    @Override
    @SuppressWarnings( "deprecation" )
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "nice day", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });
        assignViews();
        mStart.setOnClickListener(this);
        stopBtn.setOnClickListener(this);
        loop.setOnClickListener(this);
        random = new Random();
        tts = new TextToSpeech(this,this);
        tts.setLanguage(Locale.CHINESE);
        nowModeTv.setText("CDE");
        powerManager = (PowerManager) getSystemService(Activity.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK,"mylock");
        wakeLock.acquire();
    }

    private int getRandomRawId(){
        float temp =random.nextFloat();
        if(THREE==modeFlag){
            if(0<=temp&&temp<0.33){list.add(1);return R.raw.c;}
            if(0.33<=temp&&temp<0.66){list.add(2);return R.raw.d;}
            if(0.66<=temp&&temp<1){list.add(3);return R.raw.e;}
        }
        if(FOUR==modeFlag){
            if(0<=temp&&temp<0.25){list.add(1);return R.raw.c;}
            if(0.25<=temp&&temp<0.5){list.add(2);return R.raw.d;}
            if(0.5<=temp&&temp<0.75){list.add(3);return R.raw.e;}
            if(0.75<=temp&&temp<1){list.add(6);return R.raw.a;}
        }
        if(FIVE==modeFlag){
            if(0<=temp&&temp<0.2){list.add(1);return R.raw.c;}
            if(0.2<=temp&&temp<0.4){list.add(2);return R.raw.d;}
            if(0.4<=temp&&temp<0.6){list.add(3);return R.raw.e;}
            if(0.6<=temp&&temp<0.8){list.add(4);return R.raw.f;}
            if(0.8<=temp&&temp<1){list.add(5);return R.raw.g;}
        }
        if(SIX==modeFlag){
            if(0<=temp&&temp<0.15){list.add(1);return R.raw.c;}
            if(0.15<=temp&&temp<0.3){list.add(2);return R.raw.d;}
            if(0.3<=temp&&temp<0.45){list.add(3);return R.raw.e;}
            if(0.45<=temp&&temp<0.6){list.add(4);return R.raw.f;}
            if(0.6<=temp&&temp<0.75){list.add(5);return R.raw.g;}
            if(0.75<=temp&&temp<1){list.add(6);return R.raw.a;}
        }
        if(SEVEN==modeFlag){
            if(0<=temp&&temp<0.14){list.add(1);return R.raw.c;}
            if(0.14<=temp&&temp<0.28){list.add(2);return R.raw.d;}
            if(0.28<=temp&&temp<0.42){list.add(3);return R.raw.e;}
            if(0.42<=temp&&temp<0.56){list.add(4);return R.raw.f;}
            if(0.56<=temp&&temp<0.7){list.add(5);return R.raw.g;}
            if(0.7<=temp&&temp<0.84){list.add(6);return R.raw.a;}
            if(0.84<=temp&&temp<1){list.add(7);return R.raw.b;}
        }

        if(EIGHT==modeFlag){
            if(0<=temp&&temp<0.5){list.add(2);return R.raw.d;}
            if(0.5<=temp&&temp<1){list.add(3);return R.raw.e;}
        }
        if(NINE==modeFlag){
            if(0<=temp&&temp<0.5){list.add(3);return R.raw.e;}
            if(0.5<=temp&&temp<1){list.add(4);return R.raw.f;}
        }
        if(TEN==modeFlag){
            if(0<=temp&&temp<0.33){list.add(2);return R.raw.d;}
            if(0.33<=temp&&temp<0.66){list.add(3);return R.raw.e;}
            if(0.66<=temp&&temp<1){list.add(4);return R.raw.f;}
        }
        if(ELEVEN==modeFlag){
            if(0<=temp&&temp<0.5){list.add(1);return R.raw.c;}
            if(0.5<=temp&&temp<1){list.add(2);return R.raw.d;}
        }
        if(TWELVE==modeFlag){
            if(0<=temp&&temp<0.33){list.add(5);return R.raw.g;}
            if(0.33<=temp&&temp<0.66){list.add(6);return R.raw.a;}
            if(0.66<=temp&&temp<1){list.add(7);return R.raw.b;}
        }
        return R.raw.c;
    }

    @Override
    @SuppressWarnings( "deprecation" )
    public void onClick(final View v) {
        switch (v.getId()){
            case R.id.start:
                tts.stop();
                v.setEnabled(false);
                playThread = new PlayThread();
                playThread.start();
                break;
            case R.id.stop:
                mStart.setEnabled(true);
                if(null!=playThread){
                    playThread.interrupt();
                }
                if(null!=loopThread){
                    loopThread.interrupt();
                }
                loop.setEnabled(true);
                if(null!=mediaPlayer){
                    Toast.makeText(this,"wait a minute",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.loop:
                loop.setEnabled(false);
                mStart.performClick();
                loopThread = new LoopThread();
                loopThread.start();
                break;

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.cde:
                Toast.makeText(this,"cde",Toast.LENGTH_SHORT).show();
                modeFlag = THREE;
                nowModeTv.setText("CDE");
                break;
            case R.id.cdea:
                Toast.makeText(this,"cdea",Toast.LENGTH_SHORT).show();
                modeFlag = FOUR;
                nowModeTv.setText("CDEA");
                break;
            case R.id.cdefg:
                Toast.makeText(this,"cdefg",Toast.LENGTH_SHORT).show();
                modeFlag = FIVE;
                nowModeTv.setText("CDEFG");
                break;
            case R.id.cdefga:
                Toast.makeText(this,"cdefga",Toast.LENGTH_SHORT).show();
                modeFlag = SIX;
                nowModeTv.setText("CDEFGA");
                break;
            case R.id.cdefgab:
                Toast.makeText(this,"cdefgab",Toast.LENGTH_SHORT).show();
                modeFlag = SEVEN;
                nowModeTv.setText("CDEFGAB");
                break;
            case R.id.de:
                Toast.makeText(this,"de",Toast.LENGTH_SHORT).show();
                modeFlag = EIGHT;
                nowModeTv.setText("DE");
                break;
            case R.id.ef:
                Toast.makeText(this,"ef",Toast.LENGTH_SHORT).show();
                modeFlag = NINE;
                nowModeTv.setText("EF");
                break;
            case R.id.def:
                Toast.makeText(this,"def",Toast.LENGTH_SHORT).show();
                modeFlag = TEN;
                nowModeTv.setText("DEF");
                break;
            case R.id.cd:
                Toast.makeText(this,"cd",Toast.LENGTH_SHORT).show();
                modeFlag = ELEVEN;
                nowModeTv.setText("CD");
                break;
            case R.id.read_on:
                Toast.makeText(this,"read is on",Toast.LENGTH_SHORT).show();
                readFlag = Boolean.TRUE;
                break;
            case R.id.read_off:
                Toast.makeText(this,"read is off",Toast.LENGTH_SHORT).show();
                readFlag = Boolean.FALSE;
                break;
            case R.id.gab:
                Toast.makeText(this,"gab",Toast.LENGTH_SHORT).show();
                modeFlag = TWELVE;
                nowModeTv.setText("GAB");
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onInit(int status) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(wakeLock.isHeld()){
            wakeLock.release();
        }
    }
}
