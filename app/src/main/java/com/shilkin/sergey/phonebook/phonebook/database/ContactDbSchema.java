package com.shilkin.sergey.phonebook.phonebook.database;

/**
 * Created by User on 24.09.2016.
 */

public class ContactDbSchema {
    public static final class ContactTable{
        public static final String NAME = "contacts";

        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String PHONE = "phone";
        }
    }
}
