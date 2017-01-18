package com.example.bharti.materialdesign;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Button;

import java.io.IOException;

/**
 * Created by bharti on 12/9/2016.
 */

public class MusicService extends Service {
    MediaPlayer mediaPlayer;
    Boolean flag = false;
    int length;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public void onCreate() {
        super.onCreate();
        //mediaPlayer = MediaPlayer.create(this, R.raw.idil);

        if (mediaPlayer==null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.sia);
            mediaPlayer.start();
            //  startService();
           // Playbtn.setText("Pause");
          //  flag = true;
        }
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
      //  mediaPlayer.start();
        if(flag==true){

            mediaPlayer.pause();
            length=mediaPlayer.getCurrentPosition();
            try {
                mediaPlayer.prepare();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //    mediaPlayer.seekTo(0);
            flag=false;
            // Playbtn.setText("Play");
        }
        else if (flag == false) {
            mediaPlayer.seekTo(length);
            mediaPlayer.start();
            //  Playbtn.setText("Pause");
            flag = true;
        }
        return super.onStartCommand(intent, flags, startId);

    }
  /*  @Override
    public void onDestroy(){
        mediaPlayer.stop();
    }
*/
}
