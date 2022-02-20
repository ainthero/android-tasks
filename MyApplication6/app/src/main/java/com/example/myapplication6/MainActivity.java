package com.example.myapplication6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Animation rotate_anim = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.rotate);
        TextView tv = findViewById(R.id.textView2);
        tv.startAnimation(rotate_anim);
    }
}