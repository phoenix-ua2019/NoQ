package ua.lviv.iot.phoenix.noq.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class QuantityActivity extends AppCompatActivity {

    /*ArrayList<Meal> meals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_quantity);

        Bundle extras = getIntent().getExtras();
        final String userName = extras.getString("UserName");
        Cafe cafe = extras.getParcelable("cafe");
        meals = cafe.getCafeMeals();

        ((ListView) findViewById(R.id.quantity_list)).setAdapter(new QuantityAdapter( this, meals));

        ImageView buttonToMain = findViewById(R.id.horse_icon_from_quantity);
        buttonToMain.setOnClickListener( (View v) -> {
            startActivity(new Intent(QuantityActivity.this, MainActivity.class));
            overridePendingTransition(R.anim.from_top_to_bottom_exit, R.anim.from_top_to_bottom);
        });

        ImageView backButton = findViewById(R.id.back_from_quantity) ;
        backButton.setOnClickListener( (View v) -> finish() );

        Button chooseQuantity = findViewById(R.id.choose_quantity_button);
        chooseQuantity.setOnClickListener( (View view) -> {
            cafe.setCafeMeals(meals);
            Intent OpenTimeActivity = new Intent(QuantityActivity.this, TimeActivity.class);
            OpenTimeActivity.putExtra("UserName", userName);
            OpenTimeActivity.putExtra("cafe", cafe);
            startActivity(OpenTimeActivity);
            overridePendingTransition(R.anim.from_bottom_to_top, R.anim.from_bottom_to_top_exit);
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.from_top_to_bottom_exit, R.anim.from_top_to_bottom);
    }*/
}
