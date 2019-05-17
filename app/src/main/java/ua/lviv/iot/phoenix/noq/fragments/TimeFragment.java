package ua.lviv.iot.phoenix.noq.fragments;


import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.stream.Collectors;

import ua.lviv.iot.phoenix.noq.R;
import ua.lviv.iot.phoenix.noq.activities.MainActivity;
import ua.lviv.iot.phoenix.noq.models.Cafe;
import ua.lviv.iot.phoenix.noq.models.Meal;

public class TimeFragment extends Fragment {

    private TimePicker floatTime;
    private TextView orderTime;
    private TextView minOrderTime;
    private View view;
    private int preparationTime = 15;

    private final int closingHour = 25;
    private final int openingHour = -1;
    private static final int minutesInHour = 60;
    private Bundle b;
    private Cafe cafe;
    private ArrayList<Meal> meals;

    boolean wasShownToastForPast = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_time, container, false);

        b = getArguments();
        floatTime = view.findViewById(R.id.clock);
        orderTime = view.findViewById(R.id.selected_time);
        minOrderTime = view.findViewById(R.id.min_time_of_cook);

        floatTime.setIs24HourView(true);

        final int currentHour = floatTime.getHour();
        final int currentMinute = floatTime.getMinute();

        if ((currentMinute + calculateTimeToPrepare()) > minutesInHour) {
            floatTime.setHour(currentHour + 1);
            floatTime.setMinute(currentMinute + calculateTimeToPrepare() - minutesInHour);
        } else if ((currentMinute + calculateTimeToPrepare()) == minutesInHour) {
            floatTime.setHour(currentHour + 1);
            floatTime.setMinute(0);
        } else {
            floatTime.setMinute(currentMinute + calculateTimeToPrepare());
        }


        if (isCafeOpen(currentHour, currentMinute)) {
            updateDisplayAtFirst(currentHour, currentMinute);
        }

        cafe = getArguments().getParcelable("time_cafe");
        meals = cafe.getCafeMeals();

        meals = (ArrayList<Meal>) meals.stream()
                .filter(meal -> meal.getSelectedQuantity() > 0).collect(Collectors.toList());
        cafe.setCafeMeals(meals);
        b.putParcelable("order_cafe", cafe);


        minOrderTime.setText(calculateTimeToPrepare() + " хв");

        floatTime.setOnTimeChangedListener((TimePicker view, int hourOfDay, int minute) -> {
            if (isCafeOpen(hourOfDay, minute) & isAllowableTime(hourOfDay, minute)) {
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

    private Integer calculateTimeToPrepare() {

        cafe = getArguments().getParcelable("time_cafe");
        meals = cafe.getCafeMeals();
        meals = (ArrayList<Meal>) meals.stream()
                .filter(meal -> meal.getSelectedQuantity() > 0).collect(Collectors.toList());
        int minTimeToPrepare = 0;

        for (Meal meal : meals) {
            int temp = meal.getTime() * meal.getSelectedQuantity();
            if (minTimeToPrepare < temp) {
                minTimeToPrepare = temp;
            }
        }

        return minTimeToPrepare;
    }

    private void updateDisplayAtFirst(int hour, int minute) {
        int orderHour = hour;
        int orderMinute = minute;

        if ((orderMinute + calculateTimeToPrepare()) > minutesInHour) {

            orderTime.setText(String.format("%02d:%02d", orderHour + 1, orderMinute + calculateTimeToPrepare() - minutesInHour));

        } else if ((orderMinute + calculateTimeToPrepare()) == minutesInHour) {

            orderTime.setText(String.format("%02d:%02d", orderHour + 1, 0));

        } else {

            orderTime.setText(String.format("%02d:%02d", orderHour, orderMinute + calculateTimeToPrepare()));

        }
    }

    private void updateDisplay(int hour, int minute) {
        int orderHour = hour;
        int orderMinute = minute;
        orderTime.setText(String.format("%02d:%02d", orderHour, orderMinute));
    }


    private boolean isAllowableTime(int orderHour, int orderMinute) {
        int currentHour = floatTime.getCurrentHour();
        int currentMinute = floatTime.getCurrentMinute();

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
        int currentHour = floatTime.getCurrentHour();
        int currentMinute = floatTime.getCurrentMinute();

        if (
                (isNearNewHour() && (orderHour == currentHour || ((orderHour == currentHour + 1) &&
                        (orderMinute < ((currentMinute + preparationTime) % minutesInHour))))) ||
                        (orderHour == currentHour && (orderMinute < currentMinute + preparationTime))
        )
            return false;
        return true;
    }
}
