package ua.lviv.iot.phoenix.noq.models;

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.common.base.Splitter;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@IgnoreExtraProperties
public class Cafe implements Parcelable {
    private String name;
    private String location;
    private String email;
    private String icon;
    private String Cid;
    private ArrayList<Meal> meals;

    public static final Parcelable.Creator<Cafe> CREATOR =
            new Parcelable.Creator<Cafe>() {
                @Override
                public Cafe createFromParcel(Parcel source) {
                    return new Cafe(source);
                }

                @Override
                public Cafe[] newArray(int size) {
                    return new Cafe[size];
                }
            };
    public Cafe() {

    }

    public Cafe (Object o) {
        this((HashMap<String, ?>) o);
    }

    public Cafe (Parcel source) {
        this(source.readString(), source.readString(), source.readString(), source.readString(),
                source.readString(), new ArrayList<Meal>());
        source.readList(meals, Meal.class.getClassLoader());
    }

    public Cafe (String str) {
        this((new HashMap<>(Splitter.on("], ").withKeyValueSeparator("=[").split(str))));
    }

    public Cafe (HashMap<String, ?> map) {
        name = (String) map.get("name");
        location = (String) map.get("location");
        email = (String) map.get("email");
        icon = (String) map.get("icon");
        Cid = (String) map.get("cid");
        List<?> tempCafeMeals = (ArrayList<HashMap>) map.get("meals");
        meals = (ArrayList<Meal>) tempCafeMeals.stream().map(Meal::new).collect(Collectors.toList());
    }

    public Cafe (String name, String location, String email, String icon, ArrayList<Meal> meals) {
        this.name = name;
        this.location = location;
        this.email = email;
        this.icon = icon;
        this.meals = meals;
    }

    public Cafe (String name, String location, String email, String icon, String id, ArrayList<Meal> meals) {
        this(name, location, email, icon, meals);
        Cid = id;
    }

    @Override
    public String toString() {
        return "name=[" + name +
                "], location=[" + location +
                "], icon=[" + icon +
                "], meals=[" + meals + "] ";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeString(location);
        out.writeString(email);
        out.writeString(icon);
        out.writeString(Cid);
        out.writeList(meals);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setIcon(String id) {
        icon = id;
    }

    public String getIcon() {
        return icon;
    }

    public boolean hasImage(){
        return icon != null;
    }

    public ArrayList<Meal> getMeals() {
        return meals;
    }

    public void setMeals(ArrayList<Meal> meals) {
        this.meals = meals;
    }

    @Exclude
    public String getCid() {
        return Cid;
    }

}









