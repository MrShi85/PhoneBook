package com.shilkin.sergey.phonebook.phonebook.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.shilkin.sergey.phonebook.phonebook.Contact;
import com.shilkin.sergey.phonebook.phonebook.database.ContactDbSchema.ContactTable;

import java.util.UUID;

/**
 * Created by User on 24.09.2016.
 */

public class ContactCursorWrapper extends CursorWrapper {

    public ContactCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Contact getContact(){
        String uuidString = getString(getColumnIndex(ContactTable.Cols.UUID));
        String name = getString(getColumnIndex(ContactTable.Cols.NAME));
        String phone = getString(getColumnIndex(ContactTable.Cols.PHONE));

        Contact contact = new Contact(UUID.fromString(uuidString));
        contact.setmName(name);
        contact.setmPhone(phone);

        return contact;
    }

}
