package com.example.pruebaapphuelladigital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import Control.BiometricPromptHelper;
import Model.Cliente;

public class Login extends AppCompatActivity {
    Button ButtonLogin,ButtonRegister;
    ImageButton ButtonFingerprint;

    DatabaseReference databasereference;
    FirebaseDatabase firebaseDatabase;
    EditText UserMail,UserPass;
    private FirebaseAuth mAuth;

    Executor executor = Executors.newSingleThreadExecutor();
    FragmentActivity activity = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButtonLogin=findViewById(R.id.btn_login);
        ButtonRegister=findViewById(R.id.Register);
        ButtonFingerprint=findViewById(R.id.img_btn_fingerprint);
        UserMail=findViewById(R.id.Mail);
        UserPass=findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
        //--------------------------------------------------------------

        initializeFirebase();

        //Email and password auth


        //Fingerprint Auth
        ButtonFingerprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfirmacionHuella();
            }
        });
        //Toregister
        ButtonRegister.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toRegister = new Intent(getApplicationContext(), Register.class);
                startActivity(toRegister);
                finish();
            }
        }));
        //Login
        ButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Signinwithemailandpass();
            }
        });


    }
    /*    public void VoiceItVoiceAuth(){
            VoiceIt2 myVoiceIt = new VoiceIt2("key_fb599719b198460c8ac9150145fdcc26","tok_14b861f38e364f25a7a9d05ba7a779b3");


        }
        public void Googleauth(){
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
        }*/

    private void initializeFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = firebaseDatabase.getInstance();
        databasereference = firebaseDatabase.getReference();

    }
    private void ConfirmacionHuella(){
        final BiometricPrompt biometricPrompt = new BiometricPrompt(activity, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    // user clicked negative button
                } else {
                    //TODO: Called when an unrecoverable error has been encountered and the operation is complete.
                }
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Intent ToIndex = new Intent(getApplicationContext(), Index.class);
                startActivity(ToIndex);
                finish();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                //TODO: Called when a biometric is valid but not recognized.
            }
        });

        final BiometricPromptHelper promptHelper=new BiometricPromptHelper();
        promptHelper.helper();
    }


    private void Signinwithemailandpass(){
        String email=UserMail.getText().toString(),password=UserPass.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent toindex= new Intent(getApplicationContext(),Index.class);
                            startActivity(toindex);
                        } else {

                            // If sign in fails, display a message to the user.
                            Log.w("signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

}
