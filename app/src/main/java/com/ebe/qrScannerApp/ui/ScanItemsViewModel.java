package com.ebe.qrScannerApp.ui;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.ebe.qrScannerApp.data.model.ScanModel;
import com.ebe.qrScannerApp.domain.ScannerDbRepository;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ScanItemsViewModel extends AndroidViewModel {

    private final ScannerDbRepository repository;
    private final MutableLiveData<List<ScanModel>> scanItems = new MutableLiveData<>();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public ScanItemsViewModel(@NonNull Application application) {
        super(application);
        repository = new ScannerDbRepository(application);
    }

    public LiveData<List<ScanModel>> getScanItems() {
        return scanItems;
    }

    public void fetchAllScans() {
        executorService.execute(() -> {
            List<ScanModel> scans = repository.getAllScanItems();
            scanItems.postValue(scans);
        });
    }

    public void saveScan(ScanModel scanItem) {
        executorService.execute(() -> {
            repository.saveScanItem(scanItem);
            fetchAllScans(); // Refresh after saving
        });
    }

    public void deleteScan(int id) {
        executorService.execute(() -> {
            repository.deleteScanItemFromDb(id);
            fetchAllScans(); // Refresh after deletion
        });
    }
}
