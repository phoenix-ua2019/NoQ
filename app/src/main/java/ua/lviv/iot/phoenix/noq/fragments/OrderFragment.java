package ua.lviv.iot.phoenix.noq.fragments;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import ua.lviv.iot.phoenix.noq.R;
import ua.lviv.iot.phoenix.noq.activities.MainActivity;
import ua.lviv.iot.phoenix.noq.activities.Useful;
import ua.lviv.iot.phoenix.noq.adapters.MealAdapter;
import ua.lviv.iot.phoenix.noq.models.Cafe;
import ua.lviv.iot.phoenix.noq.models.Meal;
import ua.lviv.iot.phoenix.noq.models.Order;

public class OrderFragment extends Fragment {

    private View view;
    private Cafe cafe;
    private String time;
    private Double sumPrice = 0.0;
    final long count[] = {0};
    private boolean userUpdated = false;
    private boolean cafeUpdated = false;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_order, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_order_meals);

        try {
            time = getArguments().getString("time");
            cafe = getArguments().getParcelable("order_cafe");
            ArrayList<Meal> meals = cafe.getMeals();

            meals = (ArrayList<Meal>) meals.stream()
                    .filter(meal -> meal.getSelectedQuantity() > 0).collect(Collectors.toList());
            cafe.setMeals(meals);

            for (Meal meal : meals) {
                sumPrice += meal.getPrice() * meal.getSelectedQuantity();
            }

            MealAdapter mealAdapter = new MealAdapter(meals);
            recyclerView.setAdapter(mealAdapter);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLayoutManager);

            Order finalOrder = new Order(time, sumPrice, Date.from(Instant.now()), cafe);
        } catch (NullPointerException e) {
            Order order = getArguments().getParcelable("order");
            cafe = order.getCafe();
            time = order.getTime();
            sumPrice = order.getSum();
        }

        ((TextView) view.findViewById(R.id.name_of_order_cafe)).setText(cafe.getName());
        ((TextView) view.findViewById(R.id.location_of_order_cafe)).setText(cafe.getLocation());
        ((TextView) view.findViewById(R.id.selected_time_show)).setText(time);
        ((TextView) view.findViewById(R.id.selected_price)).setText(String.format("%s ₴", sumPrice));

        return view;
    }

}
