package ua.lviv.iot.phoenix.noq;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    private String name;
    private String email;
    private String date;
    private String phone;

    public User() {

    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User(String name, String email, String date, String phone) {
        this.name = name;
        this.email = email;
        this.date = date;
        this.phone = phone;
    }


}
