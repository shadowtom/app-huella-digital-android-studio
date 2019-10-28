package com.example.pruebaapphuelladigital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.UUID;

import Model.Cliente;
import adapter.CountryData;

public class Register extends AppCompatActivity {
    DatabaseReference databasereference;
    FirebaseDatabase firebaseDatabase;
    Spinner countryCodes;

    Button btnlogin,btnregister,btngoogleregister;
    EditText EtextEmail,Etextpassword,Etextnombre,EtextPhone;
    String Nombre = Etextnombre.getText().toString(),pass = Etextpassword.getText().toString(),correo = EtextEmail.getText().toString(),PhoneNumber=EtextPhone.getText().toString();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializeFirebase();

        EtextEmail=findViewById(R.id.email);
        Etextpassword=findViewById(R.id.password);
        Etextnombre=findViewById(R.id.nombre);
        EtextPhone=findViewById(R.id.Phonenumber);
        btnlogin=findViewById(R.id.Login);
        btnregister=findViewById(R.id.Register);
        countryCodes.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));
        btngoogleregister=findViewById(R.id.GoogleRegister);
        //--------------------------------------------------
        //Boton de registro con google
        btngoogleregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                areaCode();
                register();
            }
        });
    }
    private void register() {
        Cliente user = new Cliente();
        user.setID(UUID.randomUUID().toString());
        user.setNombre(Nombre);
        user.setClave(pass);
        user.setCorreo(correo);
        user.setPhone(PhoneNumber);
        databasereference.child("Cliente").child(user.getID()).setValue(user);
    }
    private void areaCode() {
        String code = CountryData.countryAreaCodes[countryCodes.getSelectedItemPosition()];
        String phone = EtextPhone.getText().toString().trim();
        //Conditional if phone is empty
        String phoneError = getResources().getString(R.string.phoneError);
        String phonelength = getResources().getString(R.string.phonelenght);
        if (phone.isEmpty()){
            EtextPhone.setError(phoneError);
            EtextPhone.requestFocus();
            return;
        }if (phone.length() < 10){
            EtextPhone.setError(phonelength);
            EtextPhone.requestFocus();
            return;
        }
        //phoneNumber
        String numberPhone = "+" + code + phone;
        Toast.makeText(getApplicationContext(),"Agregado correctamente",Toast.LENGTH_LONG).show();
        //Intent to VerificationActivity
        Intent toVerify = new Intent(getApplicationContext(), VerificationActivity.class);
        toVerify.putExtra("phoneNumber", numberPhone);
        startActivity(toVerify);
    }

    private void initializeFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = firebaseDatabase.getInstance();
        databasereference = firebaseDatabase.getReference();

    }


}
