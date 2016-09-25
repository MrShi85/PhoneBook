package com.shilkin.sergey.phonebook.phonebook;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.UUID;

/**
 * Created by User on 25.09.2016.
 */

public class ContactFragment extends Fragment {


    private Contact mContact;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID contactID = (UUID)getActivity().getIntent().
                getSerializableExtra(ContactActivity.EXTRA_CONTACT_ID);
        mContact = ContactsList.get(getActivity()).getContact(contactID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container,
                false);

        return view;
    }
}
