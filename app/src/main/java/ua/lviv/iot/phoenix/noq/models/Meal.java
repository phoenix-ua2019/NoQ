package ua.lviv.iot.phoenix.noq.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;

@IgnoreExtraProperties
public class Meal {
    private String preparationTime;
    private String mealPicture;
    private String description;
    private String mealName;
    private double price;
    private double weight;
    private int selectedQuantity;


    public Meal() {
    }

    public Meal (Object o) {
        this((HashMap<String, ?>) o);
    }

    public Meal (HashMap<String, ?> map) {
        mealName = (String) map.get("name");
        price = (Long) map.get("price");
    }
    public Meal(String mealName, double price, String preparationTime, String mealPicture, double weight, String description) {
        this.mealName = mealName;
        this.price = price;
        this.preparationTime = preparationTime;
        this.mealPicture = mealPicture;
        this.weight = weight;
        this.description = description;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(String preparationTime) {
        this.preparationTime = preparationTime;
    }

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
        return ((Double.toString(weight)) + "г");
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

    public String priceToString() {
        return Double.toString(price);
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

    public String selectedQuantityToString() {
        return Integer.toString(selectedQuantity);
    }
}

    /*
    private String mealName;
    private int mealPrice;
    private String mealPicture;
    private boolean IsChecked;
    private int mQuantity;
    public static int numberOfCheckedItems;
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

    Meal () {
        IsChecked = false;
    }

    Meal (Object o) {
        this((HashMap<String, ?>) o);
    }

    Meal (String str) {
        this((HashMap<String, String>) Splitter.on(",").withKeyValueSeparator("=").split(str));
    }

    Meal (HashMap<String, ?> map) {
        this((String) map.get("name"), map.get("price"),
                (map.get("quantity") != null) ? map.get("quantity") : 0);
    }

    Meal (Parcel source) {
        this(source.readString(), source.readInt(), source.readInt());
    }

    Meal (String mealName, Object mealPrice, Object mealQuantity) {
        this();
        mealName = mealName;
        mealPrice = Integer.parseInt(mealPrice.toString());
        mQuantity = Integer.parseInt(mealQuantity.toString());
    }

    Meal (String mealName, long mealPrice) {
        this(mealName, mealPrice, 0L);
    }

    public String toString() {
        return "{name=" + mealName +
                ", price=" + mealPrice +
                ", quantity=" + mQuantity + "}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mealName);
        out.writeInt(mealPrice);
        out.writeInt(mQuantity);
    }

    public int incrementQuantity() {
        mQuantity++;
        return getQuantity();
    }

    public int decrementQuantity() {
        mQuantity--;
        return getQuantity();
    }

    public int getQuantity() {
        return (mQuantity < 0 ? (mQuantity = 0) : mQuantity);
    }

    public void setChecked(boolean isChecked){
        numberOfCheckedItems += isChecked ? 1 : -1;
        IsChecked = isChecked;
    }

    public String getMealName(){
        return mealName;
    }

    public int getMealPrice(){
        return mealPrice;
    }

    public String getMealPicture() {
        return mealPicture;
    }

    public void setMealPicture(String mealPicture) {
        this.mealPicture = mealPicture;
    }
    */

