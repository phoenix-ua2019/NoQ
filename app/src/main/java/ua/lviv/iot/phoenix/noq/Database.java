package ua.lviv.iot.phoenix.noq;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Database {
    //private static final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("cafes");

    public static DatabaseReference getRef() {
        return ref;
    }

    /*public static FirebaseFirestore getDB() {
        return db;
    }*/
}
