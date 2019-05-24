package ua.lviv.iot.phoenix.noq.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.util.HashMap;

@IgnoreExtraProperties
public class Meal implements Parcelable {
    private int time;
    private String mealPicture;
    private String description;
    private String mealName;
    private double price;
    private double weight;
    private int selectedQuantity;
    public static final Parcelable.Creator<Meal> CREATOR =
            new Parcelable.Creator<Meal>() {
        @Override
        public Meal createFromParcel(Parcel source) {
         return new Meal(source);
        }

        @Override
        public Meal[] newArray(int size) {
         return new Meal[size];
        }
     };


    public Meal() {
    }

    public Meal (Object o) {
        this((HashMap<String, ?>) o);
    }

    public Meal (HashMap<String, ?> map) {
        mealName = (String) map.get("name");
        price = (Long) map.get("price");
        time = ((Long) map.get("time")).intValue();
        description = (String) map.get("description");
        mealPicture = (String) map.get("icon_of_food");
        try {
            Object temp = map.get("selectedQuantity");
            selectedQuantity = ((Long) temp).intValue();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        /*try {
            mealPicture = (String) map.get("mealPicture");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }*/
    }

    public Meal (Parcel source) {
        this(source.readString(), source.readDouble(), source.readInt(),
                source.readString(), source.readDouble(), source.readString(), source.readInt());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mealName);
        out.writeDouble(price);
        out.writeInt(time);
        out.writeString(mealPicture);
        out.writeDouble(weight);
        out.writeString(description);
        out.writeInt(selectedQuantity);
    }

    public Meal(String mealName, double price, int preparationTime, String mealPicture,
                double weight, String description) {
        this.mealName = mealName;
        this.price = price;
        this.time = preparationTime;
        this.mealPicture = mealPicture;
        this.weight = weight;
        this.description = description;
    }
    public Meal(String mealName, double price, int preparationTime, String mealPicture,
                double weight, String description, int selectedQuantity) {
        this(mealName, price, preparationTime, mealPicture, weight, description);
        this.selectedQuantity = selectedQuantity;
    }

    @PropertyName("name")
    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int preparationTime) {
        this.time = preparationTime;
    }

    @PropertyName("icon_of_food")
    public String getMealPicture() {
        return mealPicture;
    }

    public void setMealPicture(String mealPicture) {
        this.mealPicture = mealPicture;
    }

    public double getWeight() {
        return weight;
    }

    public String weightToString() {
        return weight + "г";
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSelectedQuantity() {
        return selectedQuantity;
    }

    public void setSelectedQuantity(int selectedQuantity) {
        this.selectedQuantity = selectedQuantity;
    }

    public boolean hasImage(){
        return mealPicture != null;
    }

    public String selectedQuantityToString() {
        return Integer.toString(selectedQuantity);
    }

    @Override
    public String toString() {
        return "Meal{" +
                "time=" + time +
                ", mealPicture='" + mealPicture + '\'' +
                ", description='" + description + '\'' +
                ", mealName='" + mealName + '\'' +
                ", price=" + price +
                ", weight=" + weight +
                ", selectedQuantity=" + selectedQuantity +
                '}';
    }
}