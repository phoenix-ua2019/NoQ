package ua.lviv.iot.phoenix.noq;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Order implements Parcelable {

    private Date mDate;
    private String mTime;
    private Cafe mCafe;
    private int mSum;
    public static final Parcelable.Creator<Order> CREATOR =
            new Parcelable.Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel source) {
         return new Order(source);
        }

        @Override
        public Order[] newArray(int size) {
         return new Order[size];
        }
     };

    Order(String time, int sum, Date date, Cafe cafe){
        mTime = time;
        mSum = sum;
        mDate = date;
        mCafe = cafe;
    }

    Order(Parcel source) {
        this(source.readString(), source.readInt(), new Date(source.readString()), source.readParcelable(Cafe.class.getClassLoader()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mTime);
        out.writeInt(mSum);
        out.writeString(mDate.toString());
        out.writeParcelable(mCafe,1);
    }

    public int getSum() {
        return mSum;
    }
    public Date getDate() {
        return mDate;
    }
    public Cafe getCafe() {
        return mCafe;
    }
    public String getTime() {
        return mTime;
    }
}
