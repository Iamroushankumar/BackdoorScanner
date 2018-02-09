package com.example.lue.backdoorscanner;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;

import com.github.lzyzsd.circleprogress.ArcProgress;

public class MainActivity extends AppCompatActivity {
ArcProgress arcprogress;
ObjectAnimator animation;
ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar=(ProgressBar)findViewById(R.id.progress) ;
        animation = ObjectAnimator.ofInt (progressBar, "progress", 0, 500); // see this max value coming back here, we animale towards that value
        animation.setDuration (50000); //in milliseconds
        animation.setInterpolator (new DecelerateInterpolator());
        animation.start();
new Handler().postDelayed(new Runnable() {
    @Override
    public void run() {
        Intent i=new Intent(MainActivity.this,AllApp.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

},5000);
    }
}
