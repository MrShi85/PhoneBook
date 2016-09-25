package com.shilkin.sergey.phonebook.phonebook;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 23.09.2016.
 */
public class ContactListFragment extends Fragment {

    private RecyclerView mContactsRecyclerView;
    private ContactAdapter mContactAdapter;

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

    private void updateUI(){
        List<Contact> contacts = new ArrayList<>();
        String name = "Имя #";
        for(int i=0;i<100;i++){
            Contact contact = new Contact();
            contact.setmName(name + i);
            contact.setmPhone("8 800 555 55 00");
            contacts.add(contact);
        }
        mContactAdapter = new ContactAdapter(contacts);
        mContactsRecyclerView.setAdapter(mContactAdapter);
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
          /*Intent intent = ContactActivity.newIntent(getActivity(),
                  mContact.getId());*/
           Toast.makeText(view.getContext(),"Button pressed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onLongClick(View view) {
            Intent intent = new Intent(getActivity(),ContactActivity.class);
            startActivity(intent);
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
    }


}
