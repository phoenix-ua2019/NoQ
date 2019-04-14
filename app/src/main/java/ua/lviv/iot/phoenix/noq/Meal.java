package ua.lviv.iot.phoenix.noq;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.common.base.Splitter;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;

@IgnoreExtraProperties
public class Meal implements Parcelable {
    private String mMealName;
    private int mMealPrice;
    private boolean mIsChecked;
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
        mIsChecked = false;
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
        mMealName = mealName;
        mMealPrice = Integer.parseInt(mealPrice.toString());
        mQuantity = Integer.parseInt(mealQuantity.toString());
    }

    Meal (String mealName, long mealPrice) {
        this(mealName, mealPrice, 0L);
    }

    public String toString() {
        return "{name=" + mMealName +
                ", price=" + mMealPrice +
                ", quantity=" + mQuantity + "}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mMealName);
        out.writeInt(mMealPrice);
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
        mIsChecked = isChecked;
    }

    public String getMealName(){
        return mMealName;
    }

    public int getMealPrice(){
        return mMealPrice;
    }
}
