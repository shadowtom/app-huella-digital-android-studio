package com.example.pruebaapphuelladigital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Login extends AppCompatActivity {
    Button ButtonLogin;
    ImageButton ButtonFingerprint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButtonFingerprint=findViewById(R.id.img_btn_fingerprint);
        ButtonFingerprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toConfirmacionHuella = new Intent(getApplicationContext(), ConfirmacionHuella.class);
                startActivity(toConfirmacionHuella);
                finish();
            }
        });

    }
}
