package ua.lviv.iot.phoenix.noq.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class Order
        //implements Parcelable
        {

    private Date mDate;
    private String mTime;
    private Cafe mCafe;
    private double mSum;
    /*public static final Parcelable.Creator<Order> CREATOR = new Parcelable.Creator<Order>() {
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
     };*/

    /*Order() {

    }*/

    public Order(String time, double sum, Date date, Cafe cafe){
        mTime = time;
        mSum = sum;
        mDate = date;
        mCafe = cafe;
    }

    /*Order(@NotNull Parcel source) throws ParseException {
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
        out.writeInt(mSum);
        out.writeString(mDate.toString());
        out.writeParcelable(mCafe,1);
    }*/

    public double getSum() {
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
