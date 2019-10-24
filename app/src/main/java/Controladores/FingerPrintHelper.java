package Controladores;

import android.content.Context;
import android.content.Intent;

import androidx.core.hardware.fingerprint.FingerprintManagerCompat;
import androidx.core.os.CancellationSignal;
import android.widget.Toast;

public class FingerPrintHelper extends FingerprintManagerCompat.AuthenticationCallback {

    private Context mContext;

    public FingerPrintHelper(Context mContext) {
        this.mContext = mContext;
    }


    public void startAuth(FingerprintManagerCompat manager, androidx.core.hardware.fingerprint.FingerprintManagerCompat.CryptoObject cryptoObject) {
        CancellationSignal cancellationSignal = new CancellationSignal();
        manager.authenticate(cryptoObject, 0, cancellationSignal,this , null);

    }



    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        this.showInformationMessage("Auth error" + errString);
    }



    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        this.showInformationMessage("Auth help\n"+ helpString);
    }

    @Override
    public void onAuthenticationFailed() {
        this.showInformationMessage("Auth failed.");
    }


    public void onAuthenticationSucceeded(androidx.core.hardware.fingerprint.FingerprintManagerCompat.AuthenticationResult result) {
        this.showInformationMessage("Auth succeeded.");
        mContext.startActivity(new Intent(mContext, Index.class));
    }


    private void showInformationMessage(String msg) {

        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }
}
