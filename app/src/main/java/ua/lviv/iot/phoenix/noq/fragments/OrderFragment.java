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
    private static int numOfOrders = 0;

    @RequiresApi(api = Build.VERSION_CODES.O)
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
        cafe.setCafeMeals(meals);
        MealAdapter mealAdapter = new MealAdapter(meals);
        recyclerView.setAdapter(mealAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        Double sumPrice = 0.0;
        int minTimeToPrepare = 0;
        for (Meal meal:meals) {
            sumPrice += meal.getPrice()*meal.getSelectedQuantity();
            int temp = meal.getTime()*meal.getSelectedQuantity();
            if (minTimeToPrepare < temp) {
                minTimeToPrepare = temp;
            }
        }

        ((TextView) view.findViewById(R.id.name_of_order_cafe)).setText(cafe.getCafeName());
        ((TextView) view.findViewById(R.id.location_of_order_cafe)).setText(cafe.getCafeLocation());
        ((TextView) view.findViewById(R.id.selected_time_show)).setText(time);
        ((TextView) view.findViewById(R.id.selected_price)).setText(String.format("%s ₴", sumPrice));

        Order finalOrder = new Order(time, sumPrice, Date.from(Instant.now()), cafe);

        DatabaseReference cafeReference = Useful.orderRef.child(cafe.getCafeLocation());
        cafeReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cafeReference.child(""+dataSnapshot.getChildrenCount()).setValue(finalOrder);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        DatabaseReference userReference = Useful.orderRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userReference.child(""+dataSnapshot.getChildrenCount()).setValue(finalOrder);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        numOfOrders += 1;

        return view;
    }

}
