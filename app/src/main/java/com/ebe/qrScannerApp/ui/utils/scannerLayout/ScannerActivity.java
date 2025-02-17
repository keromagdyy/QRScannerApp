package com.ebe.qrScannerApp.ui.utils.scannerLayout;

import android.os.Bundle;

import com.ebe.qrScannerApp.databinding.ActivityScannerBinding;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.CaptureManager;

public class ScannerActivity extends CaptureActivity {
    ActivityScannerBinding binding;
    private CaptureManager capture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScannerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        capture = new CaptureManager(this, binding.zxingBarcodeScanner);
        capture.initializeFromIntent(getIntent(), null);
        capture.decode();
    }


    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
    }
}