package com.example.financeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.content.Intent;
import android.graphics.Typeface;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Handler;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Add the full screen flags here.
        // This is used to hide the status bar and make the splash screen as a full screen activity.
        //It kind of makes the edges rounded
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);



        //Had to create this outside of the handler, wouldn't let me create it inside it
        Intent intent = new Intent(this, MainActivity.class);

        //To change switch to a different activity after a couple of seconds
        // As using handler the splash screen will disappear after what we give to the handler.
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(intent);
                                }

                                },
        2000); //We want it to run the intent after 2 seconds



    }
}