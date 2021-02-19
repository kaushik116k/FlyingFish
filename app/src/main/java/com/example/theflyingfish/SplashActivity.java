package com.example.theflyingfish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity
{
    private long backPressTime;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread thread = new Thread()
        {
           public void run()
           {
               try
               {
                    sleep(4000);
               }
               catch(Exception e)
               {
                   e.getStackTrace();
               }
               finally
               {
                   Intent i = new Intent(SplashActivity.this,MainActivity.class);
                   startActivity(i);
               }
           }
        };
        thread.start();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        finish();
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