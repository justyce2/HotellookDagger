package com.hotellook.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.hotellook.db.data.DestinationPickerHistoryItem;
import com.hotellook.db.data.FavoriteHotelDataEntity;
import com.hotellook.db.data.HotelNameHistory;
import com.hotellook.db.data.PoiHistoryItem;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import timber.log.Timber;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "hotellook.db";
    private static final int DATABASE_VERSION = 2;
    private Dao<DestinationPickerHistoryItem, Integer> destinationPickerHistoryDao;
    private Dao<FavoriteHotelDataEntity, Long> favoriteHotelDataDao;
    private Dao<HotelNameHistory, Integer> hotelNameHistoryDao;
    private Dao<PoiHistoryItem, Integer> poiHistoryDao;
    private final List<Class<?>> tables;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.tables = Arrays.asList(new Class[]{DestinationPickerHistoryItem.class, HotelNameHistory.class, FavoriteHotelDataEntity.class, PoiHistoryItem.class});
    }

    public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
        try {
            for (Class dbClass : this.tables) {
                TableUtils.createTableIfNotExists(connectionSource, dbClass);
            }
        } catch (SQLException e) {
            Timber.m754e(e, "Unable to create databases", new Object[0]);
        }
    }

    public void onUpgrade(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {
        for (Class dbClass : this.tables) {
            try {
                TableUtils.createTableIfNotExists(connectionSource, dbClass);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        onCreate(sqliteDatabase, connectionSource);
    }

    public synchronized Dao<DestinationPickerHistoryItem, Integer> getDestinationPickerHistoryDao() {
        if (this.destinationPickerHistoryDao == null) {
            try {
                this.destinationPickerHistoryDao = getDao(DestinationPickerHistoryItem.class);
            } catch (SQLException e) {
                Timber.m754e(e, "Unable to create table %s", DestinationPickerHistoryItem.class.getSimpleName());
            }
        }
        return this.destinationPickerHistoryDao;
    }

    public synchronized Dao<HotelNameHistory, Integer> getHotelNameHistoryDao() {
        if (this.hotelNameHistoryDao == null) {
            try {
                this.hotelNameHistoryDao = getDao(HotelNameHistory.class);
            } catch (SQLException e) {
                Timber.m754e(e, "Unable to create table %s", HotelNameHistory.class.getSimpleName());
            }
        }
        return this.hotelNameHistoryDao;
    }

    public synchronized Dao<FavoriteHotelDataEntity, Long> getFavoriteHotelDataDao() {
        if (this.favoriteHotelDataDao == null) {
            try {
                this.favoriteHotelDataDao = getDao(FavoriteHotelDataEntity.class);
            } catch (SQLException e) {
                Timber.m754e(e, "Unable to create table %s", FavoriteHotelDataEntity.class.getSimpleName());
            }
        }
        return this.favoriteHotelDataDao;
    }

    public synchronized Dao<PoiHistoryItem, Integer> getPoiHistoryDao() {
        if (this.poiHistoryDao == null) {
            try {
                this.poiHistoryDao = getDao(PoiHistoryItem.class);
            } catch (SQLException e) {
                Timber.m754e(e, "Unable to create table %s", PoiHistoryItem.class.getSimpleName());
            }
        }
        return this.poiHistoryDao;
    }

    public void close() {
        super.close();
        this.destinationPickerHistoryDao = null;
        this.hotelNameHistoryDao = null;
        this.favoriteHotelDataDao = null;
        this.poiHistoryDao = null;
    }
}
