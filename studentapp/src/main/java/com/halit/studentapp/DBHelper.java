package com.halit.studentapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by developer on 10/30/20.
 */

public class DBHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION=1;

    public DBHelper(Context context){
        super(context, "student db", null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String studentSql="create table tb_student ("+
                "_id integer primary key autoincrement,"+
                "name not null," +
                "email," +
                "phone," +
                "photo)";

        String scoreSql="create table tb_grade ("+
                "_id integer primary key autoincrement,"+
                "student_id not null," +
                "subject," +
                "date," +
                "grade)";

        db.execSQL(studentSql);
        db.execSQL(scoreSql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         if (newVersion == DATABASE_VERSION){
             db.execSQL("drop table tb_student");
             db.execSQL("drop table tb_grade");
             onCreate(db);
         }

    }
}
