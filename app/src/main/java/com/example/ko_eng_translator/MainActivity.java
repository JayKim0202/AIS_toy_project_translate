package com.example.ko_eng_translator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView logo = (ImageView) findViewById(R.id.logo);
        AppCompatButton startBtn = (AppCompatButton)findViewById(R.id.startBtn);

        startBtn.setVisibility(View.GONE);

        final Animation translateup = AnimationUtils.loadAnimation(MainActivity.this, R.anim.translate_up);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                logo.setAnimation(translateup);
                startBtn.setVisibility(View.VISIBLE);
            }
        }, 1000);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // InputTextLayout 화면 전환
                Intent InputTextIntent = new Intent(MainActivity.this, TranslateLayoutActivity.class);
                startActivity(InputTextIntent);
                finish();
            }
        });
    }
}