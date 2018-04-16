package com.example.lue.backdoorscanner;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lue.backdoorscanner.ui.JunkCleanActivity;
import com.github.lzyzsd.circleprogress.ArcProgress;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;

public class MainActivity extends AppCompatActivity {
ArcProgress arcprogress;
ObjectAnimator animation;
ProgressBar progressBar;
TextView taptoscan;
Button boost;
    public static final String PARAM_TOTAL_SPACE = "total_space";
    public static final String PARAM_USED_SPACE = "used_space";
    public static final String PARAM_TOTAL_MEMORY = "total_memory";
    public static final String PARAM_USED_MEMORY = "used_memory";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar=(ProgressBar)findViewById(R.id.progress) ;
        taptoscan=(TextView)findViewById(R.id.tvscan);
        boost=(Button)findViewById(R.id.boost);

        FirebaseDatabase databse=FirebaseDatabase.getInstance();
        DatabaseReference myref= databse.getReference("message");
        myref.setValue("Hello");
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value= dataSnapshot.getValue(String.class);
                Log.d("HELLO_Value_Change",value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                                   Log.w("Failed to read value",databaseError.toException());

            }
        }) ;

        boost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent (MainActivity.this,JunkCleanActivity.class);
                startActivity(i);
//                clearCache();

            }
        });
        taptoscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startScanning();
            }
        });



    }


    public void startScanning(){
        taptoscan.setText("Scanning...");
        taptoscan.setEnabled(false);
        animation = ObjectAnimator.ofInt (progressBar, "progress", 0, 500); // see this max value coming back here, we animale towards that value
        animation.setDuration (50000); //in milliseconds
        animation.setInterpolator (new DecelerateInterpolator());
        animation.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(MainActivity.this,AllApp.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }

        },5000);

    }
    @Override
    protected void onResume() {
        super.onResume();
        taptoscan.setEnabled(true);
        taptoscan.setText("Tap to scan");
    }
    public void clearCache() {
        Log.d("HELLO", "Clearing Cache.");
        File[] dir = MainActivity.this.getCacheDir().listFiles();
        if(dir.length>0){
            for (File f : dir){
                f.delete();
                makealert("Junk file cleared");


                Toast.makeText(MainActivity.this, "Cache Cleared", Toast.LENGTH_SHORT).show();
            }
        }
        else{
          makealert("No junk file");
        }
    }
    private void makealert(String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(message);

        AlertDialog alert = builder.show();
        TextView messageText = alert.findViewById(android.R.id.message);
        messageText.setTextColor(Color.RED);
        messageText.setGravity(Gravity.CENTER);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



                Intent i=new Intent (MainActivity.this,MalwareScan.class);
            startActivity(i);

return true;


    }

}
