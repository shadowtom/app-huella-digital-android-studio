package com.example.pruebaapphuelladigital;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerificationActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private String verificationId;
    EditText verificationCode;
    private ProgressBar loadingProgress;
    Button buttonVerify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        mAuth = FirebaseAuth.getInstance();
        loadingProgress = findViewById(R.id.loadingProgress);
        verificationCode = findViewById(R.id.verificationCode);
        verificationCode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        String phoneNumber = getIntent().getStringExtra("phoneNumber");
        sendVerificationCode(phoneNumber);
        findViewById(R.id.buttonVerify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = verificationCode.getText().toString().trim();
                String error = getResources().getString(R.string.codeVerification);
                if (code.isEmpty() || code.length() <6){
                    verificationCode.setError(error);
                    verificationCode.requestFocus();
                    return;
                }
                verifyCode(code);
            }
        });

    }
    private void verifyCode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        singInWithCredential(credential);
    }

    private void singInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //DONE: WalletActivity by D'V
                            //TODO: Intent to Wallet
                            Intent toWallet = new Intent(getApplicationContext(), Index.class);
                            String phoneNumber = getIntent().getStringExtra("phoneNumber");
                            //Kill all other Activities by D'V
                            toWallet.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(toWallet);
                        }else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void sendVerificationCode(String number) {
        loadingProgress.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null){
                verificationCode.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };
}
