package com.ebe.qrScannerApp.data.roomDB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ebe.qrScannerApp.data.model.ScanModel;

@Database(
        entities = {ScanModel.class},
        version = 5,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ScanDao getScanDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "scan_database"
                            )
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}
