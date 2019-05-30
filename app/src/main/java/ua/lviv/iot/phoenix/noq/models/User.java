package ua.lviv.iot.phoenix.noq.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;

@IgnoreExtraProperties
public class User implements Parcelable {
    private String name = "Петренко Петро Петрович";
    private String email;
    private String date = "01.01.1970";
    private String phone = "+380670123456";
    public static final Parcelable.Creator<User> CREATOR =
            new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
         return new User(source);
        }

        @Override
        public User[] newArray(int size) {
         return new User[size];
        }
     };

    public User() {
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User(String name, String email, String phone) {
        this(name, email);
        this.phone = phone;
    }

    public User(String name, String email, String date, String phone) {
        this(name, email, phone);
        this.date = date;
    }

    public User(HashMap<String, String> map) {
        this(map.get("name"), map.get("email"), map.get("date"), map.get("phone"));
    }

    public User(Parcel source) {
        this(source.readString(), source.readString(), source.readString(), source.readString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeString(email);
        out.writeString(date);
        out.writeString(phone);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
