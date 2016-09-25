package com.shilkin.sergey.phonebook.phonebook;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

import static com.shilkin.sergey.phonebook.phonebook.database.ContactDbSchema.ContactTable.Cols.UUID;

/**
 * Created by User on 25.09.2016.
 */

public class ContactActivity extends SingleFragmentActivity {

    public static final String EXTRA_CONTACT_ID=
            "com.shilkin.sergey.phonebook.phonebook.contact_id";

    public static Intent newIntent (Context packageContext, UUID contact_id){
        Intent intent = new Intent(packageContext, ContactActivity.class);
        intent.putExtra(EXTRA_CONTACT_ID,contact_id);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return new ContactFragment();
    }
}
