package com.ebe.qrScannerApp.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ebe.qrScannerApp.R;
import com.ebe.qrScannerApp.ui.utils.CheckTypes;
import com.ebe.qrScannerApp.ui.utils.Utils;
import com.ebe.qrScannerApp.ui.utils.scannerLayout.ScannerActivity;
import com.ebe.qrScannerApp.data.model.ScanModel;
import com.ebe.qrScannerApp.databinding.ActivityHomeBinding;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private ScanItemsViewModel scannerViewModel;
    private ScannerAdapter scannerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        onClick();
    }

    private void init() {
        binding.animNoData.setAnimation("lottie_no_data.json");
        scannerViewModel = new ViewModelProvider(this).get(ScanItemsViewModel.class);
        scanItemsObserve();
        fetchAllScans();
    }

    private void onClick() {
        binding.scanBtn.setOnClickListener(v -> {
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.setPrompt("Scan a QR Code");
            integrator.setOrientationLocked(false);
            integrator.setBeepEnabled(true);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
            integrator.setCaptureActivity(ScannerActivity.class);
            integrator.initiateScan();
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
                String contentType = CheckTypes.detectContentType(scannedContent);

                ScanModel item = new ScanModel();
                item.setContent(scannedContent);
                item.setType(contentType);
                item.setDateTime(Utils.getDatetimeNow());

                Utils.showDialog(this, contentType, scannedContent, R.drawable.ic_qr_scan, () -> {
                    saveScan(item);
                });

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void setupScannerRecycler(List<ScanModel> list) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        layoutManager.setStackFromEnd(true);
        scannerAdapter = new ScannerAdapter((item, position) -> {
            CheckTypes.checkContentResult(this, item.getContent());
        }, (item, position) -> {
            Utils.showDialog(this, "Delete Item.", "Are you sure you want to delete the item?", R.drawable.ic_error, () -> {
                deleteScan(item.getId());
            });
        });
        scannerAdapter.setData(list);
        binding.recycler.setLayoutManager(layoutManager);
        binding.recycler.setAdapter(scannerAdapter);
    }


    private void scanItemsObserve() {
        scannerViewModel.getScanItems().observe(this, scanItems -> {
            if (!scanItems.isEmpty()) {
                binding.animNoData.setVisibility(View.GONE);
                binding.imArrowNoData.setVisibility(View.GONE);
                scanItems.forEach(scanItem -> {
                    Log.d("dvdnvkdnvkmdv", "setupScannerRecycler: " + scanItem.getContent());
                });
                setupScannerRecycler(scanItems);
            } else {
                binding.animNoData.setVisibility(View.VISIBLE);
                binding.imArrowNoData.setVisibility(View.VISIBLE);
            }
        });
    }

    private void fetchAllScans() {
        scannerViewModel.fetchAllScans();
    }

    private void saveScan(ScanModel scanModel) {
        scannerViewModel.saveScan(scanModel);
    }

    private void deleteScan(int scanId) {
        scannerViewModel.deleteScan(scanId);
    }
}