package ua.lviv.iot.phoenix.noq.fragments;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ua.lviv.iot.phoenix.noq.R;
import ua.lviv.iot.phoenix.noq.activities.ListOfMeals;
import ua.lviv.iot.phoenix.noq.adapters.MealAdapter;
import ua.lviv.iot.phoenix.noq.listeners.MealRecyclerTouchListener;
import ua.lviv.iot.phoenix.noq.models.Cafe;
import ua.lviv.iot.phoenix.noq.models.Meal;


public class ListOfMealsFragment extends Fragment {

    private List<Meal> mealList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MealAdapter mealAdapter;
    private View view;

    Button plus, minus;
    Dialog quantityDialog;
    FragmentActivity currentActivity;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_of_menu, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mealAdapter = new MealAdapter(mealList);
        recyclerView.setAdapter(mealAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        currentActivity = getActivity();

        recyclerView.setLayoutManager(new LinearLayoutManager(currentActivity));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // row click listener
        recyclerView.addOnItemTouchListener(new MealRecyclerTouchListener(currentActivity.getApplicationContext(), recyclerView, new MealRecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Meal meal = mealList.get(position);
                //Toast.makeText(getApplicationContext(), meal.getMealName() + " is selected!", Toast.LENGTH_SHORT).show();
                quantityDialogCaller(meal);
            }
            @Override
            public void onLongClick(View v, int position) {
            }
        }));
        //mealList = ((Cafe) getIntent().getExtras().getParcelable("cafe")).getCafeMeals();
        prepareMealData();
        mealAdapter.notifyDataSetChanged();

        return view;
    }

    public void quantityDialogCaller(Meal meal) {
        quantityDialog = new Dialog(currentActivity);
        quantityDialog.setContentView(R.layout.quantity_item);

        plus = quantityDialog.findViewById(R.id.plus_button);
        minus = quantityDialog.findViewById(R.id.minus_button);
        TextView selectedQuantity = view.findViewById(R.id.selected_quantity);
        TextView dialogQuantity = quantityDialog.findViewById(R.id.dialog_quantity);

        dialogQuantity.setText(meal.selectedQuantityToString());

        plus.setEnabled(true);
        minus.setEnabled(true);

        plus.setOnClickListener((View v) -> {
            meal.setSelectedQuantity(meal.getSelectedQuantity() + 1);
            dialogQuantity.setText(meal.selectedQuantityToString());
            recyclerView.setAdapter(mealAdapter);
        });

        minus.setOnClickListener((View v) -> {
            if  (meal.getSelectedQuantity() <= 0) {
                return;
            }
            meal.setSelectedQuantity(meal.getSelectedQuantity() - 1);
            dialogQuantity.setText(meal.selectedQuantityToString());
            recyclerView.setAdapter(mealAdapter);
        });
        quantityDialog.show();
    }

    private void prepareMealData() {
        Meal meal_1 = new Meal("Meal_1", 100.0, "1 min", "Picture", 100.0, "fast making");
        mealList.add(meal_1);

        Meal meal_2 = new Meal("Meal_2", 200.0, "2 min", "Picture", 200.0, "fast making");
        mealList.add(meal_2);

        Meal meal_3 = new Meal("Meal_3", 300.0, "3 min", "Picture", 300.0, "fast making");
        mealList.add(meal_3);

        Meal meal_4 = new Meal("Meal_4", 400.0, "4 min", "Picture", 400.0, "fast making");
        mealList.add(meal_4);

        Meal meal_5 = new Meal("Meal_5", 500.0, "5 min", "Picture", 500.0, "fast making");
        mealList.add(meal_5);

        mealAdapter.notifyDataSetChanged();
    }

}
