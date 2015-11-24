package com.yctech.kexin;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int THREE = 3;
    private static final int FOUR = 4;
    private static final int FIVE = 5;
    private static final int SIX = 6;
    private static final int SEVEN = 7;
    private Button mStart;
    private EditText mNumEt;
    private Button mSet;
    private TextView mTiShiTv;
    private int mCount = 5;
    private MediaPlayer mediaPlayer;
    private Random random;
    private List<Integer> list;
    private int modeFlag = THREE;
    private TextView nowModeTv;

    private void assignViews() {
        mStart = (Button) findViewById(R.id.start);
        mNumEt = (EditText) findViewById(R.id.numEt);
        mSet = (Button) findViewById(R.id.set);
        mTiShiTv = (TextView) findViewById(R.id.tishiTv);
        nowModeTv = (TextView) findViewById(R.id.now_mode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Hello KeXin", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });
        assignViews();
        mStart.setOnClickListener(this);
        mSet.setOnClickListener(this);
        random = new Random();
        nowModeTv.setText("CDE");
    }

    private int getRandomRawId(){
        Log.i("fffff",modeFlag+"");
        if(THREE==modeFlag){
            float temp =random.nextFloat();
            if(0<=temp&&temp<0.33){list.add(1);return R.raw.c;}
            if(0.33<=temp&&temp<0.66){list.add(2);return R.raw.d;}
            if(0.66<=temp&&temp<1){list.add(3);return R.raw.e;}
        }
        if(FOUR==modeFlag){
            float temp =random.nextFloat();
            if(0<=temp&&temp<0.25){list.add(1);return R.raw.c;}
            if(0.25<=temp&&temp<0.5){list.add(2);return R.raw.d;}
            if(0.5<=temp&&temp<0.75){list.add(3);return R.raw.e;}
            if(0.75<=temp&&temp<1){list.add(4);return R.raw.f;}
        }
        if(FIVE==modeFlag){
            float temp =random.nextFloat();
            if(0<=temp&&temp<0.2){list.add(1);return R.raw.c;}
            if(0.2<=temp&&temp<0.4){list.add(2);return R.raw.d;}
            if(0.4<=temp&&temp<0.6){list.add(3);return R.raw.e;}
            if(0.6<=temp&&temp<0.8){list.add(4);return R.raw.f;}
            if(0.8<=temp&&temp<1){list.add(5);return R.raw.g;}
        }
        if(SIX==modeFlag){
            float temp =random.nextFloat();
            if(0<=temp&&temp<0.15){list.add(1);return R.raw.c;}
            if(0.15<=temp&&temp<0.3){list.add(2);return R.raw.d;}
            if(0.3<=temp&&temp<0.45){list.add(3);return R.raw.e;}
            if(0.45<=temp&&temp<0.6){list.add(4);return R.raw.f;}
            if(0.6<=temp&&temp<0.75){list.add(5);return R.raw.g;}
            if(0.75<=temp&&temp<1){list.add(6);return R.raw.a;}
        }
        if(SEVEN==modeFlag){
            float temp =random.nextFloat();
            if(0<=temp&&temp<0.14){list.add(1);return R.raw.c;}
            if(0.14<=temp&&temp<0.28){list.add(2);return R.raw.d;}
            if(0.28<=temp&&temp<0.42){list.add(3);return R.raw.e;}
            if(0.42<=temp&&temp<0.56){list.add(4);return R.raw.f;}
            if(0.56<=temp&&temp<0.7){list.add(5);return R.raw.g;}
            if(0.7<=temp&&temp<0.84){list.add(6);return R.raw.a;}
            if(0.84<=temp&&temp<1){list.add(7);return R.raw.b;}
        }
        return R.raw.c;
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()){
            case R.id.start:
                v.setEnabled(false);
                mTiShiTv.setText("");
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        list = new ArrayList<>();
                        for(int i=0 ;i < mCount;i++){
                            mediaPlayer = MediaPlayer.create(MainActivity.this,getRandomRawId());
                            mediaPlayer.start();
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if(null!=mediaPlayer){
                                mediaPlayer.stop();
                                mediaPlayer.release();
                            }
                        }
                        v.post(new Runnable() {
                            @Override
                            public void run() {
                                mTiShiTv.setText(Arrays.toString(list.toArray()));
                            }
                        });
                        v.post(new Runnable() {
                            @Override
                            public void run() {
                                v.setEnabled(true);
                            }
                        });
                    }
                }.start();
                break;
            case R.id.set:
                mCount = Integer.parseInt(mNumEt.getText().toString());
                Toast.makeText(this,"set ok",Toast.LENGTH_SHORT).show();

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
            case R.id.cdef:
                Toast.makeText(this,"cdef",Toast.LENGTH_SHORT).show();
                modeFlag = FOUR;
                nowModeTv.setText("CDEF");
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
        }

        return super.onOptionsItemSelected(item);
    }
}
