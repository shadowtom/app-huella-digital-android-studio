package com.example.pruebaapphuelladigital;

import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.kyanogen.signatureview.SignatureView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class FirmaContrato extends AppCompatActivity {
    Bitmap bitmap;
    Button clear,save;
    SignatureView signatureView;
    String path;
    private static final String IMAGE_DIRECTORY = "/signdemo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firma_contrato);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        signatureView = (SignatureView) findViewById(R.id.signature_view);
        clear = (Button) findViewById(R.id.clear);
        save = (Button) findViewById(R.id.save);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signatureView.clearCanvas();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap = signatureView.getSignatureBitmap();
                path = saveImage(bitmap);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }
        public String saveImage(Bitmap myBitmap) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
            File wallpaperDirectory = new File(
                    getApplicationContext().getFilesDir() + IMAGE_DIRECTORY /*iDyme folder*/);
            // have the object build the directory structure, if needed.
            if (!wallpaperDirectory.exists()) {
                wallpaperDirectory.mkdirs();
                Log.d("hhhhh",wallpaperDirectory.toString());
            }

            try {
                File f = new File(wallpaperDirectory, Calendar.getInstance()
                        .getTimeInMillis() + ".jpg");
                f.createNewFile();
                FileOutputStream fo = new FileOutputStream(f);
                fo.write(bytes.toByteArray());
                MediaScannerConnection.scanFile(getApplicationContext(),
                        new String[]{f.getPath()},
                        new String[]{"image/jpeg"}, null);
                fo.close();
                Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

                return f.getAbsolutePath();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return "";



        }


}
