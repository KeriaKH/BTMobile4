package com.example.bt4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class StudentRepo extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Student.db";
    public static final String TABLE_NAME = "students";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_CLASS = "class"; // New column

    // SQL to create the table
    public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " TEXT PRIMARY KEY, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_DATE + " TEXT, " +
            COLUMN_CLASS + " TEXT)"; // Add class column

    public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public StudentRepo(Context context) {
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

    // Add a new Student
    public void addNew(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, student.id);
        values.put(COLUMN_NAME, student.name);
        values.put(COLUMN_DATE, student.date);
        values.put(COLUMN_CLASS, student.className); // Insert class

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public boolean deleteByName(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_NAME, COLUMN_NAME + " = ?", new String[]{name});
        db.close();
        return rowsDeleted > 0;
    }

    // Update a Student
    public boolean update(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, student.name);
        values.put(COLUMN_DATE, student.date);
        values.put(COLUMN_CLASS, student.className); // Update class

        int rowAffected = db.update(TABLE_NAME, values, COLUMN_ID + "= ?", new String[]{student.id});
        db.close();
        return rowAffected > 0;
    }

    // Load all Students
    public ArrayList<Student> loadAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                COLUMN_ID,
                COLUMN_NAME,
                COLUMN_DATE,
                COLUMN_CLASS  // Add class column
        };

        Cursor cursor = db.query(TABLE_NAME, projection, null, null, null, null, null);
        ArrayList<Student> students = new ArrayList<>();

        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String date = cursor.getString(2);
            String className = cursor.getString(3); // Fetch class
            students.add(new Student(name, date, className));
        }

        cursor.close();
        db.close();
        return students;
    }
    // Load all Students by Class
    public ArrayList<Student> loadAllByClass(String className) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                COLUMN_ID,
                COLUMN_NAME,
                COLUMN_DATE,
                COLUMN_CLASS
        };
        String selection = COLUMN_CLASS + " = ?";
        String[] selectionArgs = { className };

        Cursor cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        ArrayList<Student> students = new ArrayList<>();

        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String date = cursor.getString(2);
            String classId = cursor.getString(3);
            students.add(new Student(name, date, classId));
        }

        cursor.close();
        db.close();
        return students;
    }

    // Get a Student by ID
    public Student getById(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                COLUMN_ID,
                COLUMN_NAME,
                COLUMN_DATE,
                COLUMN_CLASS  // Add class column
        };

        Cursor cursor = db.query(TABLE_NAME, projection, COLUMN_ID + "= ?", new String[]{id}, null, null, null);

        if (cursor.moveToFirst()) {
            String name = cursor.getString(1);
            String date = cursor.getString(2);
            String className = cursor.getString(3); // Get class
            cursor.close();
            db.close();
            return new Student(name, date, className);
        }

        cursor.close();
        db.close();
        return null;
    }
}
