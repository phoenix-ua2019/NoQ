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
import ua.lviv.iot.phoenix.noq.listeners.MealRecyclerTouchListener;
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
        recyclerView.addOnItemTouchListener(new MealRecyclerTouchListener(getApplicationContext(), recyclerView, new MealRecyclerTouchListener.ClickListener() {
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
        mealList = ((Cafe) getIntent().getExtras().getParcelable("cafe")).getCafeMeals();
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
    /*ArrayList<Meal> meals;

    boolean wasShownToast = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Bundle extras = getIntent().getExtras();
        final String userName = extras.getString("UserName");
        Cafe cafe = extras.getParcelable("cafe");


        meals = new ArrayList<>();
        meals.addAll(cafe.getCafeMeals());

        ListView listView = findViewById(R.id.menu_list);
        listView.setAdapter(new MealAdapter(this, meals));

        final Button chooseDishes = findViewById(R.id.choose_dishes_button);
        Meal.numberOfCheckedItems = 0;

        listView.setOnItemClickListener( (adapter, v, _position, l) -> {
            if (v != null) {
                CheckBox checkBox = v.findViewById(R.id.meal_checkbox);
                checkBox.setChecked(!checkBox.isChecked());
            }
        });

        ImageView buttonToMain = findViewById(R.id.horse_icon_from_menu);

        buttonToMain.setOnClickListener( (View v) -> {
            Intent toMainActivity = new Intent(ListOfMeals.this, MainActivity.class);
            startActivity(toMainActivity);
            overridePendingTransition(R.anim.from_top_to_bottom_exit, R.anim.from_top_to_bottom);
        });

        ImageView backButton = findViewById(R.id.back_from_menu) ;

        backButton.setOnClickListener((View v) -> finish());

        chooseDishes.setOnClickListener( (View view) -> {
            if (Meal.numberOfCheckedItems == 0) {
                if (!wasShownToast) {
                    Toast.makeText(getApplicationContext(), "Виберіть, будь ласка, страву",
                            Toast.LENGTH_SHORT).show();
                    wasShownToast = true;
                }
                return;
            }
            Intent OpenQuantityActivity = new Intent(ListOfMeals.this, QuantityActivity.class);
            OpenQuantityActivity.putExtra("UserName", userName);
            OpenQuantityActivity.putExtra("cafe", cafe);
            startActivity(OpenQuantityActivity);
            overridePendingTransition(R.anim.from_bottom_to_top, R.anim.from_bottom_to_top_exit);
        });

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.from_top_to_bottom_exit, R.anim.from_top_to_bottom);
    }*/

