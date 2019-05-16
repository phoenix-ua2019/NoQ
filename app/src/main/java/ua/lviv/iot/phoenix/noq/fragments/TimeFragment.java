package ua.lviv.iot.phoenix.noq.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import ua.lviv.iot.phoenix.noq.R;
import ua.lviv.iot.phoenix.noq.activities.MainActivity;

public class TimeFragment extends Fragment {

    private TimePicker floatTime;
    private TextView orderTime;
    private View view;

    private final int closingHour = 22;
    private final int openingHour = 7;
    private final int preparationTime = 15;
    private static final int minutesInHour = 60;

    boolean wasShownToastForPast = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_time, container, false);

        Bundle b = getArguments();
        floatTime = view.findViewById(R.id.clock);
        orderTime = view.findViewById(R.id.selected_time);

        floatTime.setIs24HourView(true);

        final int currentHour = floatTime.getHour();
        final int currentMinute = floatTime.getMinute();
        floatTime.setHour(currentHour+1);

        if (isCafeOpen(currentHour, currentMinute)){
            updateDisplay(currentHour, currentMinute);
        }

        floatTime.setOnTimeChangedListener((TimePicker view, int hourOfDay, int minute) -> {
            if (isCafeOpen(hourOfDay, minute) & isAllowableTime(hourOfDay, currentHour, minute, currentMinute)) {
                updateDisplay(hourOfDay, minute);
            }
        });
        view.findViewById(R.id.submit_time).setOnClickListener((View v) -> {
            //if (checkPreparationTime(floatTime.getHour(), floatTime.getMinute())) {
            b.putString("time", orderTime.getText().toString());
            setArguments(b);
            ((MainActivity) getActivity()).b3(view);
            /*} else {
                Toast.makeText(getContext(), "Май совість, дай хоча б 15 хвилин на приготування", Toast.LENGTH_SHORT).show();
                floatTime.setHour(floatTime.getCurrentHour()
                        +((floatTime.getCurrentMinute() + preparationTime<minutesInHour) ? 1 : 0));
                floatTime.setMinute((floatTime.getCurrentMinute()+preparationTime) % minutesInHour);
            }*/
        });

        return view;
    }



    private void updateDisplay(int hour, int minute) {
        int orderHour = hour;
        int orderMinute = minute;
        orderTime.setText(String.format("%02d:%02d", orderHour, orderMinute));
    }

    private boolean isAllowableTime(int orderHour, Integer currentHour, int orderMinute, Integer currentMinute) {

        if (orderHour < currentHour ||
                (orderHour == currentHour && orderMinute < currentMinute)
        ) {
            if (!wasShownToastForPast) {
                Toast.makeText(getActivity(), "Ей, не можна робити замовлення в минулому часі", Toast.LENGTH_SHORT).show();
                wasShownToastForPast = true;
            }
            floatTime.setHour(currentHour);
            floatTime.setMinute(currentMinute);
            return false;
        }
        return true;
    }

    private boolean isNearNewHour() {
        return minutesInHour - preparationTime <= floatTime.getCurrentMinute();
    }

    private boolean isCafeOpen(int orderHour, int orderMinute) {
        if (orderHour > closingHour || (orderHour == closingHour && orderMinute > 0)) {
            Toast.makeText(getActivity(), "Вибач, але кафе вже зачинено", Toast.LENGTH_SHORT).show();
            floatTime.setHour(closingHour % 24);
            floatTime.setMinute(0);
            return false;
        }
        if (orderHour < openingHour) {
            Toast.makeText(getActivity(), "Вибач, але кафе ще зачинено", Toast.LENGTH_SHORT).show();
            floatTime.setHour(openingHour);
            floatTime.setMinute(0);
            return false;
        }
        return true;
    }

    private boolean checkPreparationTime(int orderHour, int orderMinute) {
        int currentHour = floatTime.getHour();
        int currentMinute = floatTime.getMinute();

        if (
                (isNearNewHour() && (orderHour == currentHour || ((orderHour == currentHour + 1) &&
                        (orderMinute < ((currentMinute + preparationTime) % minutesInHour))) )) ||
                        (orderHour == currentHour && (orderMinute < currentMinute + preparationTime))
        )
            return false;
        return true;
    }
}
