package com.shilkin.sergey.phonebook.phonebook;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by User on 23.09.2016.
 */
public class ContactListFragment extends Fragment {
    private RecyclerView mContactsRecyclerView;
    private ContactAdapter mContactAdapter;
    private final static int REQUEST_CONTACT = 1;

    private static final String CONTACT_ID = ContactsContract.Contacts._ID;
    private static final String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
    private static final String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
    private static final String PHONE_NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
    private static final String PHONE_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contact_list, container,
            false);

        mContactsRecyclerView = (RecyclerView) view.findViewById(R.id.contact_recycler_view);

        mContactsRecyclerView.setLayoutManager(new LinearLayoutManager
                (getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_contact_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_item_new_contact:
                Contact contact = new Contact();
                ContactsList.get(getActivity()).addContact(contact);
                Intent intent = ContactPagerActivity
                        .newIntent(getActivity(), contact.getId());
                startActivity(intent);
                return true;
            case R.id.menu_item_load_contacts:
                loadContacs();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI(){

        ContactsList contactsList = ContactsList.get(getActivity());
        List<Contact> contacts = contactsList.getContacs();

        mContactAdapter = new ContactAdapter(contacts);
        mContactsRecyclerView.setAdapter(mContactAdapter);

        /*if(mContactAdapter == null) {
            mContactAdapter = new ContactAdapter(contacts);
            mContactsRecyclerView.setAdapter(mContactAdapter);
        }
        else{
            mContactAdapter.setCrimes(contacts);
            mContactAdapter.notifyDataSetChanged();
        }*/
    }

    private class ContactHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        private Contact mContact;
        private TextView mNameTextView;
        private TextView mPhoneTextView;
        private ImageButton mImageButton;


        public ContactHolder(View itemView){
            super(itemView);
            mNameTextView = (TextView) itemView.
                    findViewById(R.id.list_item_contact_name_text_view);
            mPhoneTextView = (TextView) itemView.
                    findViewById(R.id.list_item_contact_phone_text_view);
            mImageButton = (ImageButton) itemView.
                    findViewById(R.id.list_item_contact_button_call);
            itemView.setOnLongClickListener(this);
            mImageButton.setOnClickListener(this);
        }

        public void bindContact(Contact contact){
            mContact = contact;
            mNameTextView.setText(mContact.getmName());
            mPhoneTextView.setText(mContact.getmPhone());
        }

        @Override
        public void onClick(View view) {
            String number = mContact.getmPhone();
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" +number));
            startActivity(intent);
        }

        @Override
        public boolean onLongClick(View view) {
            Intent intent = ContactPagerActivity.newIntent(getActivity(),
                    mContact.getId());
            startActivityForResult(intent, REQUEST_CONTACT);
            return true;
        }
    }

    private class ContactAdapter extends RecyclerView.Adapter<ContactHolder>{

        private List<Contact> mContacts;

        public ContactAdapter(List<Contact> contacts){
            mContacts=contacts;
        }

        @Override
        public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_contact, parent,
                    false);
            return new ContactHolder(view);
        }

        @Override
        public void onBindViewHolder(ContactHolder holder, int position) {
            Contact contact = mContacts.get(position);
            holder.bindContact(contact);
        }

        @Override
        public int getItemCount() {
            return mContacts.size();
        }

        public void setCrimes(List<Contact> contacts){
            mContacts = contacts;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode != Activity.RESULT_OK){
            return;
        }
        if(requestCode == REQUEST_CONTACT){
            updateUI();
        }
    }

    private void loadContacs(){

        ContentResolver cr = getActivity().getContentResolver();

        Cursor pCur = cr.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{PHONE_NUMBER, PHONE_CONTACT_ID},
                ContactsContract.CommonDataKinds.Phone.TYPE + " = ?",
                new String[]{String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)},
                null
        );

        if(pCur != null){
            if(pCur.getCount() > 0) {
                HashMap<Integer, ArrayList<String>> phones = new HashMap<>();
                while (pCur.moveToNext()) {
                    Integer contactId = pCur.getInt(pCur.getColumnIndex(PHONE_CONTACT_ID));
                    ArrayList<String> curPhones = new ArrayList<>();
                    if (phones.containsKey(contactId)) {
                        curPhones = phones.get(contactId);
                    }
                    curPhones.add(pCur.getString(pCur.getColumnIndex(PHONE_NUMBER)));
                    phones.put(contactId, curPhones);
                }
                Cursor cur = cr.query(
                        ContactsContract.Contacts.CONTENT_URI,
                        new String[]{CONTACT_ID, DISPLAY_NAME, HAS_PHONE_NUMBER},
                        HAS_PHONE_NUMBER + " > 0",
                        null,
                        DISPLAY_NAME + " ASC");
                if (cur != null) {
                    if (cur.getCount() > 0) {
                        List<Contact>  contacts = ContactsList.get(getActivity()).getContacs();
                        while (cur.moveToNext()) {
                            int id = cur.getInt(cur.getColumnIndex(CONTACT_ID));
                            if(phones.containsKey(id)) {
                                Contact con = new Contact();
                                con.setmName(cur.getString(cur.getColumnIndex(DISPLAY_NAME)));
                                con.setmPhone(TextUtils.join(",", phones.get(id).toArray()));
                                if(!isContains(contacts, con)) {
                                    ContactsList.get(getActivity()).addContact(con);
                                }
                            }
                            updateUI();
                        }
                    }
                    cur.close();
                }
            }
            pCur.close();
        }

    }

    private boolean isContains(List<Contact> contactList, Contact contact){
        String phone = contact.getmPhone();
        for(Contact item :contactList){
            if(item.getmPhone()==phone){
                return true;
            }
        }
        return false;
    }
}
