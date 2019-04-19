package ua.lviv.iot.phoenix.noq;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListOfMeals extends AppCompatActivity {

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
}
