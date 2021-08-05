package com.idax.ventax.RoomDB.AppDataBase;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.idax.ventax.Entity.CompanySQLite;
import com.idax.ventax.Entity.OrderSQLite;
import com.idax.ventax.RoomDB.DAO.CompanyDao;
import com.idax.ventax.RoomDB.DAO.OrderDao;

@Database(entities = {OrderSQLite.class, CompanySQLite.class}, version = 2, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    public static AppDataBase instance = null;

    public static AppDataBase getAppDataBase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDataBase.class, "database-VAX.db").allowMainThreadQueries().addCallback(new RoomDatabase.Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                }
            }).fallbackToDestructiveMigration().build();
        }
        return instance;
    }
    public abstract OrderDao orderDao();
    public abstract CompanyDao companyDao();
}

