package com.example.theflyingfish;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private FlyingFishView gameView;
    private Handler handler  = new Handler();
    private static final long Interval = 30;
    private long backPressTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new FlyingFishView(this);
        setContentView(gameView);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        gameView.invalidate();
                    }
                });
            }
        } , 0,Interval);
    }
    @Override
    public void onBackPressed() {
        if (backPressTime + 2000 > System.currentTimeMillis())
        {
            super.onBackPressed();
            System.exit(0);
        }
        else
        {
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
        }
        backPressTime = System.currentTimeMillis();
    }
}