package ua.lviv.iot.phoenix.noq;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    private String uName;
    private String name;
    private String email;
    private String date;
    private String phone;

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
