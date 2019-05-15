package ua.lviv.iot.phoenix.noq.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ua.lviv.iot.phoenix.noq.R;
import ua.lviv.iot.phoenix.noq.activities.MainActivity;
import ua.lviv.iot.phoenix.noq.adapters.MealAdapter;
import ua.lviv.iot.phoenix.noq.listeners.MealRecyclerTouchListener;
import ua.lviv.iot.phoenix.noq.models.Cafe;
import ua.lviv.iot.phoenix.noq.models.Meal;


public class ListOfMealsFragment extends Fragment {

    private Cafe cafe;
    private ArrayList<Meal> mealList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MealAdapter mealAdapter;
    private View view;

    ImageView plus, minus;
    Button chooseTimeBtn;
    Dialog quantityDialog;
    MainActivity currentActivity;

    int commonSelectedAmount = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_of_menu, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        mealAdapter = new MealAdapter(mealList);
        recyclerView.setAdapter(mealAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        currentActivity = (MainActivity) getActivity();

        recyclerView.setLayoutManager(new LinearLayoutManager(currentActivity));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // row click listener
        recyclerView.addOnItemTouchListener(new MealRecyclerTouchListener(currentActivity.getApplicationContext(), recyclerView, new MealRecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Meal meal = mealList.get(position);
                //Toast.makeText(getApplicationContext(), meal.getMealName() + " is selected!", Toast.LENGTH_SHORT).show();
                quantityDialogCaller(meal);
                cafe.setCafeMeals(mealList);
                Bundle b = getArguments();
                b.putParcelable("cafe", cafe);
                setArguments(b);
            }
            @Override
            public void onLongClick(View v, int position) {
            }
        }));
        //mealList = ((Cafe) getIntent().getExtras().getParcelable("cafe")).getCafeMeals();
        System.out.println(getArguments());
        cafe = getArguments().getParcelable("cafe");
        mealList = cafe.getCafeMeals();
        mealAdapter.setList(mealList);
        mealAdapter.notifyDataSetChanged();
        chooseTimeBtn = view.findViewById(R.id.choose_time);
        chooseTimeBtn.setVisibility(View.INVISIBLE);

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
            chooseTimeBtn.setVisibility(View.VISIBLE);
            commonSelectedAmount++;
            meal.setSelectedQuantity(meal.getSelectedQuantity() + 1);
            dialogQuantity.setText(meal.selectedQuantityToString());
            recyclerView.setAdapter(mealAdapter);
        });

        minus.setOnClickListener((View v) -> {
            if  (meal.getSelectedQuantity() <= 0) { return; }
            commonSelectedAmount--;
            if (commonSelectedAmount == 0) { chooseTimeBtn.setVisibility(View.INVISIBLE); }
            meal.setSelectedQuantity(meal.getSelectedQuantity() - 1);
            //////cafe.setCafeMeals();
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

        Meal meal_6 = new Meal("Meal_1", 100.0, "1 min", "Picture", 100.0, "fast making");
        mealList.add(meal_6);

        Meal meal_7 = new Meal("Meal_1", 100.0, "1 min", "Picture", 100.0, "fast making");
        mealList.add(meal_7);

        Meal meal_8 = new Meal("Meal_1", 100.0, "1 min", "Picture", 100.0, "fast making");
        mealList.add(meal_8);

        Meal meal_9 = new Meal("Meal_1", 100.0, "1 min", "Picture", 100.0, "fast making");
        mealList.add(meal_9);

        Meal meal_10 = new Meal("Meal_1", 100.0, "1 min", "Picture", 100.0, "fast making");
        mealList.add(meal_10);

        Meal meal_11 = new Meal("Meal_1", 100.0, "1 min", "Picture", 100.0, "fast making");
        mealList.add(meal_11);

        mealAdapter.notifyDataSetChanged();
    }

}
