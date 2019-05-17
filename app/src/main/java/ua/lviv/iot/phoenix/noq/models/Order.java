package ua.lviv.iot.phoenix.noq.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.PropertyName;
import com.google.firebase.database.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

public class Order implements Parcelable {

    private Date mDate;
    private String mTime;
    private Cafe mCafe;
    private double mSum;
    public static final Parcelable.Creator<Order> CREATOR = new Parcelable.Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel source) {
            try {
                return new Order(source);
            } catch (ParseException e) {
                e.printStackTrace();
                return new Order();
            }
        }

        @Override
        public Order[] newArray(int size) {
         return new Order[size];
        }
     };

    Order() {

    }

    public Order (Object o) {
        this((Map<String, ?>) o);
    }

    public Order (Map<String, ?> map) {
        mCafe = new Cafe(map.get("cafe"));
        mTime = (String) map.get("time");
        try {
            mSum = (Double) map.get("sum");
        } catch (Exception e){
            mSum = (Long) map.get("sum");
        }
    }

    public Order(String time, double sum, Date date, Cafe cafe){
        mTime = time;
        mSum = sum;
        mDate = date;
        mCafe = cafe;
    }

    Order(@NotNull Parcel source) throws ParseException {
        this(source.readString(), source.readInt(),
                DateFormat.getDateInstance().parse(source.readString()),
                source.readParcelable(Cafe.class.getClassLoader()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mTime);
        out.writeDouble(mSum);
        out.writeString(mDate.toString());
        out.writeParcelable(mCafe,1);
    }

    @PropertyName("sum")
    public double getSum() {
        return mSum;
    }
    @PropertyName("date")
    public Date getDate() {
        return mDate;
    }
    @PropertyName("cafe")
    public Cafe getCafe() {
        return mCafe;
    }
    @PropertyName("time")
    public String getTime() {
        return mTime;
    }
}
