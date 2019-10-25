package Control;

import androidx.biometric.BiometricPrompt;

public class BiometricPromptHelper {

    public BiometricPrompt.PromptInfo helper(){
        final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Set the title to display.")
                .setSubtitle("Set the subtitle to display.")
                .setDescription("Set the description to display")
                .setNegativeButtonText("Negative Button")
                .build();
        return promptInfo;
    }
}
