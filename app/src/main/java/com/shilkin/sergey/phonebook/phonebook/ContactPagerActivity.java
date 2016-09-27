package com.shilkin.sergey.phonebook.phonebook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

/**
 * Created by User on 27.09.2016.
 */

public class ContactPagerActivity extends AppCompatActivity {

    public static final String EXTRA_CONTACT_ID=
            "com.shilkin.sergey.phonebook.phonebook.contact_id";

    private ViewPager mViewPager;
    private List<Contact> mContacts;

    public static Intent newIntent (Context packageContext, UUID contact_id){
        Intent intent = new Intent(packageContext, ContactPagerActivity.class);
        intent.putExtra(EXTRA_CONTACT_ID,contact_id);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_pager);

        UUID contactId = (UUID) getIntent().
                getSerializableExtra(EXTRA_CONTACT_ID);

        mViewPager = (ViewPager) findViewById
                (R.id.activity_contact_pager_view_pager);
        mContacts = ContactsList.get(this).getContacs();
        FragmentManager fragmentManager=getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Contact contact = mContacts.get(position);
                return ContactFragment.newInstance(contact.getId());
            }

            @Override
            public int getCount() {
                return mContacts.size();
            }
        });

        for (int i=0; i<mContacts.size(); i++){
            if(mContacts.get(i).getId().equals(contactId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
