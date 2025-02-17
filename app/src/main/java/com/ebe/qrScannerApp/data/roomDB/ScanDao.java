package com.ebe.qrScannerApp.data.roomDB;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ebe.qrScannerApp.data.model.ScanModel;

import java.util.List;

@Dao
public interface ScanDao {

    @Query("SELECT * FROM scan")
    List<ScanModel> getAllScanItems();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveScan(ScanModel scan);

    @Query("DELETE FROM scan WHERE id = :id")
    void deleteScan(int id);
}
