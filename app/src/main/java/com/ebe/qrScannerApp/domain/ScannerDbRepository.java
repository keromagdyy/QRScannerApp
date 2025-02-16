package com.ebe.qrScannerApp.domain;

import android.content.Context;

import com.ebe.qrScannerApp.data.model.ScanModel;
import com.ebe.qrScannerApp.data.roomDB.AppDatabase;
import java.util.List;

public class ScannerDbRepository {
    private final AppDatabase database;

    public ScannerDbRepository(Context context) {
        this.database = AppDatabase.getDatabase(context);
    }

    public void saveScanItem(ScanModel scanItem) {
        database.getScanDao().saveScan(scanItem);
    }

    public List<ScanModel> getAllScanItems() {
        return database.getScanDao().getAllScanItems();
    }

    public void deleteScanItemFromDb(int id) {
        database.getScanDao().deleteScan(id);
    }
}