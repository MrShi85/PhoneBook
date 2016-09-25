package com.shilkin.sergey.phonebook.phonebook;

import java.util.Date;
import java.util.UUID;

/**
 * Created by User on 24.09.2016.
 */

public class Contact {

    private UUID mId;
    private String mName;
    private String mPhone;

    public Contact(){
        this(UUID.randomUUID());
    }

    public Contact(UUID id){
        mId = id;
    }

    public UUID getId() {
        return mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }
}
