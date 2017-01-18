package com.example.bharti.materialdesign;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.bharti.materialdesign.R.id.imageView;

public class SubActivity extends AppCompatActivity {
    ImageView iv;
    Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        iv = (ImageView) findViewById(R.id.imageView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.appBar);
        setSupportActionBar(toolbar);

        //noinspection ConstantConditions
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sub, menu);
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
        if (id == android.R.id.home) {

            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    public void download(View view) {
        String url = "http://www.w3schools.com/css/trolltunga.jpg";
        Thread myThread = new Thread(new DownloadImageThread(url));
        myThread.start();
    }

    public void takePicture(View view) {
        Intent myIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(myIntent, 123);

    }

    public void pickImages(View view) {
        Intent myIntent2 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(myIntent2, 321);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {
            Bitmap bm = (Bitmap) data.getExtras().get("data");
            iv.setImageBitmap(bm);
        } else if (requestCode == 321 && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            // Get the cursor
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            // Move to first row
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String imgDecodableString = cursor.getString(columnIndex);
            cursor.close();
            iv.setImageBitmap(BitmapFactory
                    .decodeFile(imgDecodableString));
        } else {
            Toast.makeText(this, "You haven't picked Image",
                    Toast.LENGTH_LONG).show();
        }
    }


    public class DownloadImageThread implements Runnable {

        private String url;

        public DownloadImageThread(String url) {
            this.url = url;
        }

        public void run() {
            HttpURLConnection conn;
            InputStream inputStream;
            URL downloadUrl = null;
            try {

                downloadUrl = new URL(url);
                conn = (HttpURLConnection) downloadUrl.openConnection();
                conn.connect();
                inputStream = conn.getInputStream();
                bmp = BitmapFactory.decodeStream(inputStream);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                SubActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        iv.setImageBitmap(bmp);
                    }
                });
            }
        }


    }
}

