package com.example.bharti.materialdesign;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

import static android.view.View.VISIBLE;
import static com.example.bharti.materialdesign.R.menu.menu_main;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    //  boolean isButtonClicked = false;
   // Button btn;
    Button Playbtn;
    MediaPlayer mediaPlayer;
    Boolean flag = false;

  //  int length ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
       // getSupportActionBar().setDisplayShowHomeEnabled(true);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      //  btn = (Button) findViewById(R.id.stopButton);
        Playbtn = (Button) findViewById(R.id.musicButton);
        //   Boolean btnStatus = false;

        NavFragment navFragment = (NavFragment) getSupportFragmentManager().findFragmentById(R.id.nav);
        navFragment.setUp(R.id.nav, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.settings) {
            Toast.makeText(getBaseContext(), "You have pressed Settings tab!!! =)",
                    Toast.LENGTH_SHORT).show();

        }
        if (id == R.id.getHelp) {
            Toast.makeText(getBaseContext(), "You have pressed Help tab!!! =)",
                    Toast.LENGTH_SHORT).show();

        }
        if (id == R.id.navigate) {
            Toast.makeText(getBaseContext(), "You have pressed next tab!!! =)",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, SubActivity.class));

        }
        return super.onOptionsItemSelected(item);
    }

    public void animateMe(View view) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.tween_animation);
        view.startAnimation(animation);

    }

    public void playMusic(View view) {
        Intent intent = new Intent(this, MusicService.class);
        startService(intent);
        if(!flag) {
            Playbtn.setText("Pause");
            flag = true;
        }
        else{
            Playbtn.setText("Play");
            flag=false;
        }


    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Intent intent = new Intent(this,MusicService.class);
        stopService(intent);
    }


}