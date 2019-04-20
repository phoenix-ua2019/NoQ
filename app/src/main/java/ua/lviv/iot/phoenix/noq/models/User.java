package ua.lviv.iot.phoenix.noq.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;

@IgnoreExtraProperties
public class User implements Parcelable {
    private String uName = "Петренко Петро Петрович";
    private String name;
    private String email;
    private String date = "01.01.1970";
    private String phone = "+380671234567";
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

    public static String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    public User() {
    }

    public User(String email) {
        this.name = User.usernameFromEmail(email);
        this.email = email;
    }

    public User(String name, String email, String date, String phone) {
        this.uName = name;
        this.email = email;
        this.date = date;
        this.phone = phone;
        this.name = User.usernameFromEmail(email);
    }

    public User(String name, String email, String date, String phone, String username) {
        this(name, email, date, phone);
        this.name = username;
    }

    public User(HashMap<String, String> map) {
        this(map.get("uName"), map.get("email"), map.get("date"), map.get("phone"), map.get("name"));
    }

    public User(Parcel source) {
        this(source.readString(), source.readString(), source.readString(),
                source.readString(), source.readString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(uName);
        out.writeString(email);
        out.writeString(date);
        out.writeString(phone);
        out.writeString(name);
    }

    public String getUName() {
        return uName;
    }

    public void setUName(String uName) {
        this.uName = uName;
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
