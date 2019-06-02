package ua.lviv.iot.phoenix.noq.fragments;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ua.lviv.iot.phoenix.noq.R;
import ua.lviv.iot.phoenix.noq.adapters.MealAdapter;
import ua.lviv.iot.phoenix.noq.models.Cafe;
import ua.lviv.iot.phoenix.noq.models.Meal;
import ua.lviv.iot.phoenix.noq.models.Order;

public class SeeMyOrderFragment extends Fragment {

    private View view;
    private Cafe cafe;
    private String time;
    private Double sumPrice = 0.0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_see_my_order, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_see_my_order_meals);
        Order order = getArguments().getParcelable("see_order");
        cafe = order.getCafe();
        time = order.getTime();
        sumPrice = order.getSum();
        List<Meal> meals = cafe.getMeals();

        setArguments(new Bundle());
        MealAdapter mealAdapter = new MealAdapter(meals);
        mealAdapter.setFragment(this);
        recyclerView.setAdapter(mealAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        ((TextView) view.findViewById(R.id.name_of_see_my_order_cafe)).setText(cafe.getName());
        ((TextView) view.findViewById(R.id.location_of_see_my_order_cafe)).setText(cafe.getLocation());
        ((TextView) view.findViewById(R.id.selected_see_my_time_show)).setText(time);
        ((TextView) view.findViewById(R.id.selected_see_my_price)).setText(String.format("%s ₴", sumPrice));
        ((TextView) view.findViewById(R.id.order_number_in_SMO)).setText(String.format("№%06d",order.getPos()));

        return view;
    }

}
