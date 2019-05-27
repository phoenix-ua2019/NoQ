package ua.lviv.iot.phoenix.noq.fragments;


import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
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

    Button acceptOrder;
    Button rejectOrder;
    Button confirm;
    Button decline;

    Dialog confirmationDialog;

    MainActivity currentActivity;

    Order order;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_order, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_order_meals);

        time = getArguments().getString("time");
        cafe = getArguments().getParcelable("cafe");
        ArrayList<Meal> meals = cafe.getMeals();

        meals = (ArrayList<Meal>) meals
                .stream()
                .filter(meal -> meal.getSelectedQuantity() > 0)
                .collect(Collectors.toList());
        cafe.setMeals(meals);

        for (Meal meal : meals) {
            sumPrice += meal.getPrice() * meal.getSelectedQuantity();
        }

        MealAdapter mealAdapter = new MealAdapter(meals);
        mealAdapter.setFragment(this);
        recyclerView.setAdapter(mealAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        order = new Order(time, sumPrice, Date.from(Instant.now()), cafe);
        Bundle args = getArguments();
        args.putParcelable("order", order);
        setArguments(args);

        ((TextView) view.findViewById(R.id.name_of_order_cafe)).setText(cafe.getName());
        ((TextView) view.findViewById(R.id.location_of_order_cafe)).setText(cafe.getLocation());
        ((TextView) view.findViewById(R.id.selected_time_show)).setText(time);
        ((TextView) view.findViewById(R.id.selected_price)).setText(String.format("%s ₴", sumPrice));


        return view;
    }

    public void confirmationDialogCaller(boolean status) {
        confirmationDialog = new Dialog(currentActivity);
        confirmationDialog.setContentView(R.layout.confirmation_dialog);

        confirm = confirmationDialog.findViewById(R.id.confirm_button);
        decline = confirmationDialog.findViewById(R.id.decline_button);
        TextView confirmationMassage = confirmationDialog.findViewById(R.id.confirmation_message);

        confirm.setEnabled(true);
        decline.setEnabled(true);

        if (status) {
            confirmationMassage.setText("Ви впевнені, що хочете підтвердити замовлення?");
            confirm.setOnClickListener((View v) -> {
                setStatus(1);
                currentActivity.b4(view);
            });
        } else {
            confirmationMassage.setText("Ви впевнені, що хочете відхилити замовлення?");
            confirm.setOnClickListener((View v) -> {
                confirmationDialog.cancel();
            });
        }
        decline.setOnClickListener((View v) -> confirmationDialog.cancel());

        confirmationDialog.show();
    }

    private void setStatus(int status) {
        order.setStatus(status);
        Useful.orderRef.child(order.getUid()).child(""+order.getPos()).setValue(order);
        Useful.orderRef.child("Bikini Bottom").child(""+order.getPos()).setValue(order);
        confirmationDialog.cancel();
    }

}
