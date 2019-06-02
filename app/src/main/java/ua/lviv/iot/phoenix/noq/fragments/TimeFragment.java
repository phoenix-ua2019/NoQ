package ua.lviv.iot.phoenix.noq.fragments;


import android.os.Bundle;
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

    private final int closingHour = 25;
    private final int openingHour = -1;
    private static final int minutesInHour = 60;
    private Bundle b;
    private Cafe cafe;
    private ArrayList<Meal> meals;

    boolean wasShownToastForPast = false;
    boolean wasShownToastForClosedCafe = false;


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
            System.out.println("Перишй раз updateDisplayAtFirst");
            updateDisplayAtFirst(currentHour, currentMinute);
        }

        cafe = getArguments().getParcelable("cafe");
        meals = cafe.getMeals();

        minOrderTime.setText(calculateTimeToPrepare() + " хв");

        floatTime.setOnTimeChangedListener((TimePicker view, int orderHour, int orderMinute) -> {
            if (isCafeOpen(orderHour, orderMinute) && isAllowableTime(orderHour, currentHour, orderMinute, currentMinute)) {

                int temp;
                if ((currentMinute + calculateTimeToPrepare()) > minutesInHour) {
                    temp = 1;
                } else if ((currentMinute + calculateTimeToPrepare()) == minutesInHour) {
                    temp = 2;
                } else {
                    temp = 3;
                }
                switch (temp) {
                    case 1: {
                        if (orderHour < currentHour + 1) {
                            updateDisplay(currentHour + 1, orderMinute);

                        } else if (orderHour == currentHour + 1 && orderMinute < currentMinute + calculateTimeToPrepare() - minutesInHour) {
                            updateDisplay(orderHour, currentMinute + calculateTimeToPrepare() - minutesInHour);
                        } else {
                            updateDisplay(orderHour, orderMinute);
                        }
                        break;
                    }
                    case 2: {
                        if (orderHour < currentHour + 1) {
                            updateDisplay(currentHour + 1, 0);

                        } else if (orderHour == currentHour + 1 && orderMinute != 0) {
                            updateDisplay(currentHour + 1, orderMinute);

                        } else {
                            updateDisplay(orderHour, orderMinute);
                        }
                        break;
                    }
                    case 3: {
                        if (orderHour < currentHour) {
                            updateDisplay(currentHour, orderMinute);
                        } else if (orderHour == currentHour && orderMinute < currentMinute + calculateTimeToPrepare()) {
                            updateDisplay(orderHour, currentMinute + calculateTimeToPrepare());
                        } else {
                            updateDisplay(orderHour, orderMinute);
                        }
                        break;
                    }
                }
            }
        });
        view.findViewById(R.id.submit_time).setOnClickListener((View v) -> {
            b.putString("time", orderTime.getText().toString());
            setArguments(b);
            ((MainActivity) getActivity()).b3(view);
        });

        return view;
    }

    private Integer calculateTimeToPrepare() {

        cafe = getArguments().getParcelable("time_cafe");
        meals = cafe.getMeals();
        meals = (ArrayList<Meal>) meals.stream()
                .filter(meal -> meal.getSelectedQuantity() > 0).collect(Collectors.toList());
        int minTimeToPrepare = 0;

        for (Meal meal : meals) {
            int temp = meal.getTime();
            if (minTimeToPrepare < temp) {
                minTimeToPrepare = temp;
            }
        }
        return minTimeToPrepare;
    }

    private void updateDisplayAtFirst(int orderHour, int orderMinute) {

        if ((orderMinute + calculateTimeToPrepare()) > minutesInHour) {

            orderTime.setText(String.format("%02d:%02d", orderHour + 1, orderMinute + calculateTimeToPrepare() - minutesInHour));

        } else if ((orderMinute + calculateTimeToPrepare()) == minutesInHour) {

            orderTime.setText(String.format("%02d:%02d", orderHour + 1, 0));

        } else {

            orderTime.setText(String.format("%02d:%02d", orderHour, orderMinute + calculateTimeToPrepare()));

        }
    }

    private void updateDisplay(int orderHour, int orderMinute) {
        System.out.println("updateDisplay   orderHour = " + orderHour + " orderMinute = " + orderMinute);
        orderTime.setText(String.format("%02d:%02d", orderHour, orderMinute));
    }


    private boolean isAllowableTime(int orderHour, Integer currentHour, int orderMinute, Integer currentMinute) {

        int temp;
        if ((currentMinute + calculateTimeToPrepare()) > minutesInHour) {
            temp = 1;
        } else if ((currentMinute + calculateTimeToPrepare()) == minutesInHour) {
            temp = 2;
        } else {
            temp = 3;
        }

        switch (temp) {
            case 1: {
                if (orderHour < currentHour + 1) {
                    if (!wasShownToastForPast) {
                        Toast.makeText(getActivity(), "Вибач, але ми не встигнемо зробити замовлення за такий час, дай нам хоча б " + calculateTimeToPrepare() + " хв", Toast.LENGTH_SHORT).show();
                        wasShownToastForPast = true;
                    }
                    floatTime.setHour(currentHour + 1);
                    floatTime.setMinute(orderMinute);

                } else if (orderHour == currentHour + 1 && orderMinute < currentMinute + calculateTimeToPrepare() - minutesInHour) {
                    if (!wasShownToastForPast) {
                        Toast.makeText(getActivity(), "Вибач, але ми не встигнемо зробити замовлення за такий час, дай нам хоча б " + calculateTimeToPrepare() + " хв", Toast.LENGTH_SHORT).show();
                        wasShownToastForPast = true;
                    }
                    floatTime.setHour(currentHour + 1);
                    floatTime.setMinute(currentMinute + calculateTimeToPrepare() - minutesInHour);
                }
                break;
            }
            case 2: {
                if (orderHour < currentHour + 1 && orderMinute == 0) {
                    if (!wasShownToastForPast) {
                        Toast.makeText(getActivity(), "Вибач, але ми не встигнемо зробити замовлення за такий час, дай нам хоча б " + calculateTimeToPrepare() + " хв", Toast.LENGTH_SHORT).show();
                        wasShownToastForPast = true;
                    }
                    floatTime.setHour(currentHour + 1);
                    floatTime.setMinute(orderMinute);

                } else if (orderHour < currentHour + 1 && orderMinute != 0) {
                    if (!wasShownToastForPast) {
                        Toast.makeText(getActivity(), "Вибач, але ми не встигнемо зробити замовлення за такий час, дай нам хоча б " + calculateTimeToPrepare() + " хв", Toast.LENGTH_SHORT).show();
                        wasShownToastForPast = true;
                    }
                    floatTime.setHour(currentHour + 1);
                    floatTime.setMinute(0);

                } else if (orderHour > currentHour + 1) {
                    floatTime.setHour(orderHour);
                    floatTime.setMinute(orderMinute);
                }
                break;
            }
            case 3: {
                if (orderHour < currentHour) {
                    if (!wasShownToastForPast) {
                        Toast.makeText(getActivity(), "Вибач, але ми не встигнемо зробити замовлення за такий час, дай нам хоча б " + calculateTimeToPrepare() + " хв", Toast.LENGTH_SHORT).show();
                        wasShownToastForPast = true;
                    }
                    floatTime.setHour(currentHour);
                    floatTime.setMinute(currentMinute + calculateTimeToPrepare());

                } else if (orderHour == currentHour && orderMinute < currentMinute + calculateTimeToPrepare()) {
                    if (!wasShownToastForPast) {
                        Toast.makeText(getActivity(), "Вибач, але ми не встигнемо зробити замовлення за такий час, дай нам хоча б " + calculateTimeToPrepare() + " хв", Toast.LENGTH_SHORT).show();
                        wasShownToastForPast = true;
                    }
                    floatTime.setHour(orderHour);
                    floatTime.setMinute(currentMinute + calculateTimeToPrepare());
                }
                break;
            }
            default: {
                return false;
            }
        }
        return true;
    }

    private boolean isCafeOpen(int orderHour, int orderMinute) {
        if (orderHour > closingHour || (orderHour == closingHour && orderMinute >= 0)) {
            if (wasShownToastForClosedCafe == false){
                Toast.makeText(getActivity(), "Вибач, але кафе вже зачинено", Toast.LENGTH_SHORT).show();
                wasShownToastForClosedCafe = true;
            }
            floatTime.setHour(closingHour - 1);
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
}
