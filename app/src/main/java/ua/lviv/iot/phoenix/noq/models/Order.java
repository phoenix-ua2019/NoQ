package ua.lviv.iot.phoenix.noq.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.PropertyName;
import com.google.firebase.database.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import ua.lviv.iot.phoenix.noq.activities.Useful;

public class Order implements Parcelable {

    private Date mDate = new Date();
    private String mTime;
    private Cafe mCafe;
    private double mSum;
    private int status;
    private long pos;
    private long userPos;
    private String Uid;
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

    public Order() {
        Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public Order (Object o) {
        this((Map<String, ?>) o);
    }

    public Order (Map<String, ?> map) {
        this();
        mCafe = new Cafe(map.get("cafe"));
        mTime = (String) map.get("time");
        pos = (Long) map.get("pos");
        try {
            mSum = (Double) map.get("sum");
        } catch (Exception e){
            mSum = (Long) map.get("sum");
        }
        status = map.containsKey("status") ? ((Long) map.get("status")).intValue() : 0;
    }

    public Order(String time, double sum, Date date, Cafe cafe){
        this();
        mTime = time;
        mSum = sum;
        mDate = date;
        mCafe = cafe;
    }

    Order(String time, double sum, String id, Date date, Cafe cafe) {
        this(time, sum, date, cafe);
        Uid = id;
    }

    Order(@NotNull Parcel source) throws ParseException {
        this(source.readString(), source.readInt(), source.readString(),
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
        out.writeString(Uid);
        out.writeString(mDate.toString());
        out.writeParcelable(mCafe,1);
    }

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
    public int getStatus() {
        return status;
    }
    @Exclude
    public boolean isDone() {
        return status == 1;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public void setPos(long pos) {
        this.pos = pos;
    }
    public long getPos() {
        return pos;
    }
    public void setUserPos(long up) {
        this.userPos = up;
    }
    public long getUserPos() {
        return userPos;
    }
    public String getUid() {
        return Uid;
    }

    @Override
    public String toString() {
        return "time:"+mTime+", sum:"+mSum+", cafe:"+mCafe+", pos's:"+pos+" .";
    }
}