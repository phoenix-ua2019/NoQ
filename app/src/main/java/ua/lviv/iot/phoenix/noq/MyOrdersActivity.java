package ua.lviv.iot.phoenix.noq;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MyOrdersActivity extends AppCompatActivity {

    /*ArrayList<Meal> meals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        Bundle extras = getIntent().getExtras();
        final String userName = extras.getString("UserName");
        final String orderTime = extras.getString("order time");
        Cafe cafe = extras.getParcelable("cafe");
        meals = cafe.getCafeMeals();

        Date currentDate = Calendar.getInstance().getTime();

        int totalPrice = 0;

        for (Meal meal : meals) {
            totalPrice += meal.getMealPrice() * meal.getQuantity();
        }

        final Order order = new Order(orderTime, totalPrice, currentDate, cafe);
        displayOrder(order);

        Button orderButton = findViewById(R.id.button_order);
        orderButton.setOnClickListener( (View v) -> {
            AddToDatabase(order);
            startActivity(new Intent(MyOrdersActivity.this, MainActivity.class));
            overridePendingTransition(R.anim.from_top_to_bottom_exit, R.anim.from_top_to_bottom);
        });

        ImageView buttonToMain = findViewById(R.id.horse_icon_from_my_order);
        buttonToMain.setOnClickListener( (View v) -> {
            startActivity(new Intent(MyOrdersActivity.this, MainActivity.class));
            overridePendingTransition(R.anim.from_top_to_bottom_exit, R.anim.from_top_to_bottom);
        });

        ImageView backButton = findViewById(R.id.back_from_my_order) ;
        backButton.setOnClickListener( (View v) -> finish() );
    }

    private  void displayOrder(Order order)
    {
        Cafe cafe = order.getCafe();

        TextView nameTextView = findViewById(R.id.place);
        TextView locationTextView = findViewById(R.id.adress);
        nameTextView.setText(cafe.getCafeName());
        locationTextView.setText(cafe.getCafeLocation());

        ListView listView = findViewById(R.id.list_view_my_order);
        listView.setAdapter(new MyOrderAdapter(this, cafe.getCafeMeals()));

        TextView totalTextView = findViewById(R.id.total_field);
        totalTextView.setText(order.getSum() + " грн");

        TextView timeTextView = findViewById(R.id.time_field);
        timeTextView.setText(order.getTime());
    }

    public void AddToDatabase(Order order) {
        Database.getRef().child("order").setValue(order);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.from_top_to_bottom_exit, R.anim.from_top_to_bottom);
    }*/

}
