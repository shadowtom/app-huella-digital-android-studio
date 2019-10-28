package com.example.pruebaapphuelladigital;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
private ImageView logo;
private TextView welcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logo = findViewById(R.id.knxLogo);
        welcome = findViewById(R.id.welcomeMessage);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash_transition);
        logo.startAnimation(animation);
        welcome.startAnimation(animation);
        final Intent toLogin = new Intent(getApplicationContext(), Login.class);
        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    startActivity(toLogin);
                    finish();
                }
            }
        };
        timer.start();
    }
}
