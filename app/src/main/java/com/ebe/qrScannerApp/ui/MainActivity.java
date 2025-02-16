package com.ebe.qrScannerApp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.ebe.qrScannerApp.databinding.ActivityMainBinding;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.scanBtn.setOnClickListener(v -> {
            IntentIntegrator intentIntegrator = new IntentIntegrator(this);
            intentIntegrator.setPrompt("Scan a barcode or QR Code");
            intentIntegrator.setOrientationLocked(true);
            intentIntegrator.initiateScan();
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(getBaseContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                String scannedContent = intentResult.getContents();
                String formatName = intentResult.getFormatName();
                String contentType = detectContentType(scannedContent);

                binding.textContent.setText(scannedContent);
                binding.textFormat.setText(formatName + " (" + contentType + ")");

                binding.textContent.setText(scannedContent);
                binding.textFormat.setText(formatName + " (" + contentType + ")");
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private String detectContentType(String content) {
        if (content == null || content.trim().isEmpty()) {
            return "Unknown";
        }

        content = content.trim(); // Remove leading/trailing spaces

        if (content.startsWith("tel:") || android.util.Patterns.PHONE.matcher(content).matches()) {
            return "Phone Number";
        } else if (content.startsWith("mailto:") || android.util.Patterns.EMAIL_ADDRESS.matcher(content).matches()) {
            return "Email";
        } else if (content.startsWith("sms:") || content.startsWith("smsto:")) {
            return "SMS";
        } else if (content.startsWith("geo:")) {
            return "Location";
        } else if (content.startsWith("WIFI:")) {
            return "Wi-Fi Configuration";
        } else if (content.startsWith("BEGIN:VCARD")) {
            return "Contact (vCard)";
        } else if (content.startsWith("BEGIN:VEVENT")) {
            return "Calendar Event";
        } else if (content.startsWith("bitcoin:")) {
            return "Bitcoin Address";
        } else if (content.startsWith("upi://") || content.startsWith("paytm://")) {
            return "Payment Link";
        } else if (content.startsWith("intent:")) {
            return "App Link";
        } else if (android.util.Patterns.WEB_URL.matcher(content).matches()) {
            return "URL";
        } else if (isValidIPAddress(content)) {
            return "IP Address";
        } else {
            return "Text";
        }
    }

    private boolean isValidIPAddress(String ip) {
        return ip.matches(
                "^((25[0-5]|2[0-4][0-9]|[0-1]?[0-9][0-9]?)\\.){3}" +
                        "(25[0-5]|2[0-4][0-9]|[0-1]?[0-9][0-9]?)$") || // IPv4
                ip.matches("([a-fA-F0-9:]+:+)+[a-fA-F0-9]+");  // IPv6
    }
}