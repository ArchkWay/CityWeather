package com.example.archek.weathercities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "cwDb";
    public static final String TABLE_CW = "cw";

    public static final String ID = "_id";
    public static final String CITY_NAME = "name";
    public static final String CITY_SIZE = "size";
    public static final String TEMPRETURE_AVERAGE_WINTER = "WINTER";
    public static final String TEMPRETURE_AVERAGE_SPRING = "SPRING";
    public static final String TEMPRETURE_AVERAGE_SUMMER = "SUMMER";
    public static final String TEMPRETURE_AVERAGE_AUTUMN = "AUTUMN";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_CW + "(" + ID
                + " integer primary key," + CITY_NAME + " text," + CITY_SIZE + " text," + TEMPRETURE_AVERAGE_WINTER
                + " integer," + TEMPRETURE_AVERAGE_SPRING + " integer," + TEMPRETURE_AVERAGE_SUMMER + " integer," + TEMPRETURE_AVERAGE_AUTUMN
                + " integer)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_CW);

        onCreate(db);

    }
}
