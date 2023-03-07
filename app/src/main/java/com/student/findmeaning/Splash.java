package com.student.findmeaning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class Splash extends AppCompatActivity {
    LottieAnimationView lottieAnimationView;
    TextView appName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        code for animation
        lottieAnimationView = findViewById(R.id.animationView);
        lottieAnimationView.enableMergePathsForKitKatAndAbove(true);
        lottieAnimationView.animate().setDuration(2000).setStartDelay(2900);

//        code for text animation (typing effect)
        appName = findViewById(R.id.appName);
        appName.setText("");
//        appName.animate().setDuration(2000).setStartDelay(0).translationY(-500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                appName.setText("F");
            }
        },300);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                appName.append("i");
            }
        },400);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                appName.append("n");
            }
        },500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                appName.append("d");
            }
        },600);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                appName.append(" ");
            }
        },600);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                appName.append("M");
            }
        },700);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                appName.append("e");
            }
        },800);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                appName.append("a");
            }
        },900);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                appName.append("n");
            }
        },1000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                appName.append("i");
            }
        },1100);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                appName.append("n");
            }
        },1200);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                appName.append("g");
            }
        },1300);


//        code for splash screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                execute once timer is over
//                using INTENT to navigate from this to new activity
                Intent iMainActivity = new Intent(Splash.this, MainActivity.class);
                startActivity(iMainActivity);
                finish(); //to avoid calling back on this activity
            }
        },3000);
    }
}