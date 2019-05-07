package ua.lviv.iot.phoenix.noq.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import ua.lviv.iot.phoenix.noq.R;
import ua.lviv.iot.phoenix.noq.activities.MainActivity;
import ua.lviv.iot.phoenix.noq.activities.TimeActivity;

public class TimeFragment extends Fragment {

    private TimePicker floatTime;
    private TextView orderTime;
    private Button submitTime;
    private View view;

    private final int closingHour = 22;
    private final int openingHour = 7;
    private final int preparationTime = 15;
    private final int minutesInHour = 60;

    boolean wasShownToastForPast = false;
    boolean wasShownTooEarlyToast = false;
    boolean wasShownTooLateToast = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_time, container, false);

        floatTime = (TimePicker) view.findViewById(R.id.clock);
        orderTime = (TextView) view.findViewById(R.id.selected_time);

        floatTime.setIs24HourView(true);

        final Integer currentHour = floatTime.getHour();
        final Integer currentMinute = floatTime.getMinute();
        if (isCafeOpen(currentHour, currentMinute)){
            orderTime.setText(updateDisplay());
        }
        else {

            //Intent toMainActivity = new Intent(TimeActivity.this, MainActivity.class);
            //startActivity(toMainActivity);
        }

        floatTime.setOnTimeChangedListener((TimePicker view, int hourOfDay, int minute) -> {
                if (isCafeOpen(hourOfDay, minute) &
                        (isAllowableTime(hourOfDay, currentHour, minute, currentMinute))) {
                    updateDisplay(hourOfDay, minute);
                }
        });
        view.findViewById(R.id.submit_time).setOnClickListener((View v) -> {
            Bundle b = new Bundle();
            b.putString("time", orderTime.getText().toString());
            setArguments(b);
            ((MainActivity) getActivity()).b3(view);
        });




        return view;

    }

    private void updateDisplay(int hour, int minute) {
        Integer orderHour = hour;
        Integer orderMinute = minute;
        String mOrderTime = convertTime(orderHour, orderMinute);
        orderTime.setText(mOrderTime);
    }


    private String updateDisplay() {
        Integer currentHour = floatTime.getHour();
        Integer currentMinute = floatTime.getMinute();
        String mOrderTime = convertTime(currentHour, currentMinute);
        return mOrderTime;
    }


    private String fixZero(Integer num) {
        String stringNum;
        if (num < 10) {
            stringNum = "0";
            stringNum += num.toString();
        } else {
            stringNum = num.toString();
        }
        return stringNum;
    }



    private String convertTime(Integer hour, Integer minute) {
        String convertedTime = fixZero(hour);
        convertedTime += ":";
        convertedTime += fixZero(minute);
        return convertedTime;
    }



    private boolean isAllowableTime(int orderHour, Integer currentHour, int orderMinute, Integer currentMinute) {

        if (orderHour < currentHour) {
            if (!wasShownToastForPast) {
                //Toast.makeText(this, "Ей, не можна робити замовлення в минулому часі", Toast.LENGTH_SHORT).show();
                wasShownToastForPast = true;
            }
            floatTime.setHour(currentHour);
            floatTime.setMinute(currentMinute);
            return false;

        } else if (orderHour == currentHour) {
            if (orderMinute < currentMinute){
                if (!wasShownToastForPast) {
                    //Toast.makeText(this, "Ей, не можна робити замовлення в минулому часі", Toast.LENGTH_SHORT).show();
                    wasShownToastForPast = true;
                }
                floatTime.setHour(currentHour);
                floatTime.setMinute(currentMinute);
                return false;
            }
        }
        return true;
    }



    private int cutMinute(int minute){
        if (minute >= minutesInHour){
            minute -= minutesInHour;
        }
        return minute;
    }



    private boolean isNearNewHour(Integer currentMinute){
        if (minutesInHour - preparationTime <= currentMinute){
            return true;
        }
        return false;
    }



    private boolean isCafeOpen(int orderHour, int orderMinute) {
        if (orderHour > closingHour) {
            if (!wasShownTooLateToast) {
                //Toast.makeText(this, "Вибач, але кафе вже зачинено", Toast.LENGTH_SHORT).show();
                wasShownTooLateToast = true;
            }
            floatTime.setHour(closingHour);
            floatTime.setMinute(0);
            return false;
        }

        if (orderHour == closingHour && orderMinute > 0) {
            if (!wasShownTooLateToast) {
                //Toast.makeText(this, "Вибач, але кафе вже зачинено", Toast.LENGTH_SHORT).show();
                wasShownTooLateToast = true;
            }
            floatTime.setHour(closingHour);
            floatTime.setMinute(0);
            return false;
        }

        if (orderHour < openingHour) {
            if (!wasShownTooEarlyToast) {
                //Toast.makeText(this, "Вибач, але кафе ще зачинено", Toast.LENGTH_SHORT).show();
                wasShownTooEarlyToast = true;
            }
            floatTime.setHour(openingHour);
            floatTime.setMinute(0);
            return false;
        }
        return true;
    }


    private boolean checkPreparationTime(int orderHour, int orderMinute, int currentHour, int currentMinute) {
        if (isNearNewHour(currentMinute)) {
            if (orderHour == currentHour) {
                return false;
            }

            if(orderHour == currentHour + 1) {
                if (orderMinute < cutMinute(currentMinute + preparationTime)) {
                    return false;
                }
            }
        }

        else{
            if (orderHour == currentHour) {
                if (orderMinute < currentMinute + preparationTime) {
                    return false;
                }
            }
        }
        return  true;
    }




}
