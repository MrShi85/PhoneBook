package com.shilkin.sergey.phonebook.phonebook.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.shilkin.sergey.phonebook.phonebook.database.ContactDbSchema.ContactTable;

/**
 * Created by User on 24.09.2016.
 */

public class ContactsBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "contactBase.db";

    public ContactsBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + ContactTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                ContactTable.Cols.UUID + ", " +
                ContactTable.Cols.NAME + ", " +
                ContactTable.Cols.PHONE + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
