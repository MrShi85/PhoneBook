package com.shilkin.sergey.phonebook.phonebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shilkin.sergey.phonebook.phonebook.database.ContactCursorWrapper;
import com.shilkin.sergey.phonebook.phonebook.database.ContactsBaseHelper;
import com.shilkin.sergey.phonebook.phonebook.database.ContactDbSchema.ContactTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by User on 24.09.2016.
 */

public class ContactsList {
    private static ContactsList sContactsList;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    private DatabaseReference mFDatabase;

    public static ContactsList get(Context context){
        if(sContactsList == null){
            sContactsList = new ContactsList(context);
        }
        return sContactsList;
    }

    public void addContact(Contact c){
        ContentValues values = getContentValues(c);

        mDatabase.insert(ContactTable.NAME,null, values);

        /*// Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");*/

        mFDatabase.child("users").child(c.getId().toString()).child("name").setValue(c.getmName());
        mFDatabase.child("users").child(c.getId().toString()).child("phone").setValue(c.getmPhone());
        //mFDatabase.child("users").child(c.getId().toString()).setValue(c);
    }

    private ContactsList(Context context){
        mFDatabase = FirebaseDatabase.getInstance().getReference();
        mContext = context.getApplicationContext();
        mDatabase = new ContactsBaseHelper(mContext).getWritableDatabase();
    }

    public List<Contact> getContacs()
    {
        List<Contact> Contacts = new ArrayList<>();

        ContactCursorWrapper cursor = queryContacts(null,null);

        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                Contacts.add(cursor.getContact());
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return Contacts;
    }

    public List<Contact> getContacs(String name)
    {
        List<Contact> Contacts = new ArrayList<>();

        ContactCursorWrapper cursor;

        if(name.trim().length() == 0 ){
            cursor = queryContacts(null,null);
        }
        else{
            /*cursor = queryContacts(
                    ContactTable.Cols.NAME + " LIKE '?'",
                    new String[] {name+"%"});*/
            cursor = queryContacts(
                    ContactTable.Cols.NAME + " LIKE '%"+name+"%'",
                    null);
        }


        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                Contacts.add(cursor.getContact());
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return Contacts;
    }

    public Contact getContact(UUID id){

        ContactCursorWrapper cursor = queryContacts(
                ContactTable.Cols.UUID + " = ?",
                new String[] {id.toString()});
        try{
            if(cursor.getCount()==0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getContact();
        }
        finally {
            cursor.close();
        }
    }


    public void updateContact(Contact Contact){
        String uuidString = Contact.getId().toString();
        ContentValues values = getContentValues(Contact);

        mDatabase.update(ContactTable.NAME, values,
                ContactTable.Cols.UUID + " = ?" ,
                new String[]{uuidString});

        mFDatabase.child("users").child(uuidString).child("name").setValue(Contact.getmName());
        mFDatabase.child("users").child(uuidString).child("phone").setValue(Contact.getmPhone());
    }

    private static ContentValues getContentValues(Contact Contact){
        ContentValues values = new ContentValues();
        values.put(ContactTable.Cols.UUID, Contact.getId().toString());
        values.put(ContactTable.Cols.NAME, Contact.getmName());
        values.put(ContactTable.Cols.PHONE, Contact.getmPhone());

        return values;
    }

    private ContactCursorWrapper queryContacts(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                ContactTable.NAME,
                null, // Columns-null выбирает все столбцы
                whereClause,
                whereArgs,
                null,
                null,
                ContactTable.Cols.NAME);
        return  new ContactCursorWrapper(cursor);
    }
    public int getPosition(UUID id){
        return -1;
    }
}
