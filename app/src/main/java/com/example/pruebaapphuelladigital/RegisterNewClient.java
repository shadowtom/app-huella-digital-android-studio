package com.example.pruebaapphuelladigital;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

import Model.NewClientModel;

public class RegisterNewClient extends AppCompatActivity {
    EditText nombre,Correo,Telefono,FechaDeNacimiento,DNI;
    CheckBox SenderEmail;
    DatabaseReference databasereference;
    TextView DatosClientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_client);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nombre=findViewById(R.id.NewClientName);
        Correo=findViewById(R.id.NewClientEmail);
        Telefono=findViewById(R.id.NewClientPhoneNumber);
        FechaDeNacimiento=findViewById(R.id.NewClientBirthDate);
        DNI=findViewById(R.id.NewClientDNI);
        SenderEmail=findViewById(R.id.NewClientEmailSender);
        DatosClientes=findViewById(R.id.TextViewDatosCliente);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Validations()){
                    AddNewClient();
                    String correo=Correo.getText().toString();
                    Snackbar.make(view, "Cliente Agregado Correctamente", Snackbar.LENGTH_LONG).show();
                    SenderEmail(correo);
                    Intent ToIndex= new Intent(getApplicationContext(),Index.class);
                    startActivity(ToIndex);
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(Getintent().getDataString()!=null){
            DatosClientes.setText(Getintent().getData().toString());
        }


    }

    private void AddNewClient() {
        String Nombre=nombre.getText().toString(),correo=Correo.getText().toString(),PhoneNumber=Telefono.getText().toString(),Dni=DNI.getText().toString(),FechaNacimiento=FechaDeNacimiento.getText().toString();
        NewClientModel user = new NewClientModel();
        user.setID(UUID.randomUUID().toString());
        user.setDNI(Dni);
        user.setNombre(Nombre);
        user.setFechaDeNacimiento(FechaNacimiento);
        user.setCorreo(correo);
        user.setPhone(PhoneNumber);
        databasereference.child("Cliente").child(user.getID()).setValue(user);

    }
    private  Boolean Validations(){
        String Nombre=nombre.getText().toString(),correo=Correo.getText().toString(),PhoneNumber=Telefono.getText().toString(),Dni=DNI.getText().toString(),FechaNacimiento=FechaDeNacimiento.getText().toString();
        if(TextUtils.isEmpty (Nombre)){
            nombre.setError("Obligatorio");
        }else if(TextUtils.isEmpty (correo)){
            Correo.setError("Obligatorio");
        }else if(TextUtils.isEmpty (PhoneNumber)){
            Telefono.setError("Obligatorio");
        }else if(TextUtils.isEmpty (Dni)){
            DNI.setError("Obligatorio");
        }else if(TextUtils.isEmpty (FechaNacimiento)){
            FechaDeNacimiento.setError("Obligatorio");
        }else if(!SenderEmail.isChecked()){
            SenderEmail.setError("Obligatorio");
        }
        return true;
    }
    private void SenderEmail(String correo){
        Log.i("Send email", "");
        Intent SendEmail= new Intent(Intent.ACTION_SEND);
        SendEmail.setData(Uri.parse("Mail To:"+correo));
        SendEmail.setType("text/plain");
        SendEmail.putExtra(Intent.EXTRA_SUBJECT,"Confirmacion de Inscripcion");
        try {
            startActivity(Intent.createChooser(SendEmail, "Send mail..."));
            finish();
            Log.i("Finished", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
    private Intent Getintent(){
        Intent Datoscliente=getIntent();
        return Datoscliente;
    }


}
