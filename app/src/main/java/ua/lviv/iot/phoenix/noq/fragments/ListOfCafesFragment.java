package ua.lviv.iot.phoenix.noq.fragments;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ua.lviv.iot.phoenix.noq.R;
import ua.lviv.iot.phoenix.noq.adapters.CafeAdapter;
import ua.lviv.iot.phoenix.noq.adapters.MealAdapter;
import ua.lviv.iot.phoenix.noq.listeners.MealRecyclerTouchListener;
import ua.lviv.iot.phoenix.noq.models.Cafe;
import ua.lviv.iot.phoenix.noq.models.Meal;


public class ListOfCafesFragment extends Fragment {

    private List<Cafe> cafesList = new ArrayList<>();
    private RecyclerView cafesRecyclerView;
    private CafeAdapter cafesAdapter;
    private View view;

    FragmentActivity currentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_of_cafes, container, false);

        cafesRecyclerView = (RecyclerView) view.findViewById(R.id.cafe_recycler_view);
        cafesAdapter = new CafeAdapter(cafesList);
        cafesRecyclerView.setAdapter(cafesAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        cafesRecyclerView.setLayoutManager(mLayoutManager);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        currentActivity = getActivity();

        cafesRecyclerView.setLayoutManager(new LinearLayoutManager(currentActivity));
        cafesRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // row click listener
        cafesRecyclerView.addOnItemTouchListener(new MealRecyclerTouchListener(currentActivity.getApplicationContext(), cafesRecyclerView, new MealRecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Cafe cafe = cafesList.get(position);
                //Toast.makeText(getApplicationContext(), meal.getMealName() + " is selected!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onLongClick(View v, int position) {
            }
        }));
        //mealList = ((Cafe) getIntent().getExtras().getParcelable("cafe")).getCafeMeals();
        prepareCafeData();
        cafesAdapter.notifyDataSetChanged();

        return view;
    }

    private void prepareCafeData() {

        Cafe cafe_1 = new Cafe("Cafe_1","Location_1");
        cafesList.add(cafe_1);

        Cafe cafe_2 = new Cafe("Cafe_2","Location_2");
        cafesList.add(cafe_2);

        Cafe cafe_3 = new Cafe("Cafe_3","Location_3");
        cafesList.add(cafe_3);

        Cafe cafe_4 = new Cafe("Cafe_4","Location4");
        cafesList.add(cafe_4);

        cafesAdapter.notifyDataSetChanged();
    }

}
