package ua.lviv.iot.phoenix.noq.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.stream.Collectors;

import ua.lviv.iot.phoenix.noq.R;
import ua.lviv.iot.phoenix.noq.adapters.MealAdapter;
import ua.lviv.iot.phoenix.noq.models.Cafe;
import ua.lviv.iot.phoenix.noq.models.Meal;

public class OrderFragment extends Fragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_order, container, false);

        String time = getArguments().getString("time");
        Cafe cafe = getArguments().getParcelable("cafe");
        ArrayList<Meal> meals = cafe.getCafeMeals();

        RecyclerView recyclerView = view.findViewById(R.id.recycler_order_meals);
        meals = (ArrayList<Meal>) meals.stream()
                .filter(meal -> meal.getSelectedQuantity()>0).collect(Collectors.toList());
        MealAdapter mealAdapter = new MealAdapter(meals);
        recyclerView.setAdapter(mealAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        Double sumPrice = 0.0;
        for (Meal meal:meals) {
            sumPrice += meal.getPrice()*meal.getSelectedQuantity();
        }

        ((TextView) view.findViewById(R.id.selected_time_show)).setText(time);
        ((TextView) view.findViewById(R.id.selected_price)).setText(sumPrice.toString());

        return view;
    }

}
