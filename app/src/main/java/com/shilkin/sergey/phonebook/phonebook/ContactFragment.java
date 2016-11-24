package com.shilkin.sergey.phonebook.phonebook;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.UUID;

/**
 * Created by User on 25.09.2016.
 */

public class ContactFragment extends Fragment {


    private static final String ARG_CONTACT_ID = "crime_id";

    private Contact mContact;

    private EditText mNameField;
    private EditText mPhoneField;


    public static ContactFragment newInstance(UUID crimeId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_CONTACT_ID, crimeId);
        ContactFragment fragment = new ContactFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID contactID = (UUID)getArguments().getSerializable(ARG_CONTACT_ID);
        /*UUID contactID = (UUID)getActivity().getIntent().
                getSerializableExtra(ContactActivity.EXTRA_CONTACT_ID);*/
        mContact = ContactsList.get(getActivity()).getContact(contactID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container,
                false);

        mNameField =(EditText)view.findViewById(R.id.contact_name);
        mNameField.setText(mContact.getmName());
        mNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mContact.setmName(charSequence.toString());
                updateContact();
                returnResult();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mPhoneField = (EditText)view.findViewById(R.id.contact_phone);
        mPhoneField.setText(mContact.getmPhone());
        mPhoneField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {mContact.setmPhone(charSequence.toString());
                updateContact();
                returnResult();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        updateContact();

        return view;
    }

    private void updateContact(){
        ContactsList.get(getActivity()).updateContact(mContact);
    }

    public void returnResult(){
        getActivity().setResult(Activity.RESULT_OK, null);
    }

}
