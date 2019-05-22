package ua.lviv.iot.phoenix.noq.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ua.lviv.iot.phoenix.noq.R;
import ua.lviv.iot.phoenix.noq.adapters.MealAdapter;
import ua.lviv.iot.phoenix.noq.listeners.RecyclerTouchListener;
import ua.lviv.iot.phoenix.noq.models.Cafe;
import ua.lviv.iot.phoenix.noq.models.Meal;

public class ListOfMeals extends AppCompatActivity {

    private List<Meal> mealList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MealAdapter mealAdapter;

    Button plus, minus;
    Dialog quantityDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_list_of_menu);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mealAdapter = new MealAdapter(mealList);

        recyclerView = findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mealAdapter);

        // row click listener
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
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
        mealList = ((Cafe) getIntent().getExtras().getParcelable("cafe")).getMeals();
        mealAdapter.setR(getResources());
        mealAdapter.notifyDataSetChanged();
    }

    public void quantityDialogCaller(Meal meal) {
        quantityDialog = new Dialog(ListOfMeals.this);
        quantityDialog.setContentView(R.layout.quantity_item);

        plus = quantityDialog.findViewById(R.id.plus_button);
        minus = quantityDialog.findViewById(R.id.minus_button);
        TextView selectedQuantity = findViewById(R.id.selected_quantity);
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
            if (meal.getSelectedQuantity() <= 0) {
                return;
            }
            meal.setSelectedQuantity(meal.getSelectedQuantity() - 1);
            dialogQuantity.setText(meal.selectedQuantityToString());
            recyclerView.setAdapter(mealAdapter);
        });
        quantityDialog.show();
    }
}

