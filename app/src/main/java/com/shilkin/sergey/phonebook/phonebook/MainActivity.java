package com.shilkin.sergey.phonebook.phonebook;

import android.support.v4.app.Fragment;

public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new ContactListFragment();
    }
}
