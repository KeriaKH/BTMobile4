package com.example.bt4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class ClassRepo extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Class.db";
    public static final String TABLE_NAME = "classes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_KHOA = "khoa"; // Khoa represents the department

    // SQL để tạo bảng
    public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " TEXT PRIMARY KEY, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_KHOA + " TEXT)";

    public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    // Constructor
    public ClassRepo(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);
    }

    // Thêm mới một Class
    public void addNew(Class c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, c.id);
        values.put(COLUMN_NAME, c.name);
        values.put(COLUMN_KHOA, c.khoa);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    // Cập nhật một Class
    public boolean update(Class c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, c.name);
        values.put(COLUMN_KHOA, c.khoa);

        int rowAffected = db.update(TABLE_NAME, values, COLUMN_ID + "= ?", new String[]{c.id});
        db.close();
        return rowAffected > 0;
    }

    // Xóa một Class theo Name
    public boolean delete(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowAffected = db.delete(TABLE_NAME, COLUMN_NAME + "= ?", new String[]{name});
        db.close();
        return rowAffected > 0;
    }

    // Load tất cả các Class
    public ArrayList<Class> loadAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                COLUMN_ID,
                COLUMN_NAME,
                COLUMN_KHOA
        };

        Cursor cursor = db.query(TABLE_NAME, projection, null, null, null, null, null);
        ArrayList<Class> classes = new ArrayList<>();

        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String khoa = cursor.getString(2);
            classes.add(new Class( name, khoa));
        }

        cursor.close();
        db.close();
        return classes;
    }

    // Lấy một Class theo ID
    public Class getById(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                COLUMN_ID,
                COLUMN_NAME,
                COLUMN_KHOA
        };

        Cursor cursor = db.query(TABLE_NAME, projection, COLUMN_ID + "= ?", new String[]{id}, null, null, null);

        if (cursor.moveToFirst()) {
            String name = cursor.getString(1);
            String khoa = cursor.getString(2);
            cursor.close();
            db.close();
            return new Class( name, khoa);
        }

        cursor.close();
        db.close();
        return null;
    }
}
