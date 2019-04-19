package ua.lviv.iot.phoenix.noq;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;

public class TimeActivity extends AppCompatActivity {

    /*ArrayList<Meal> meals;

    TimePicker floatTime;
    TextView orderTime;
    Button submitTime;

    final int closingHour = 24;
    final int openingHour = 7;
    final int preparationTime = 15;
    final int minutesInHour = 60;

    boolean wasShownToastForPast = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        floatTime = findViewById(R.id.clock);
        orderTime = findViewById(R.id.text_time);
        floatTime.setIs24HourView(true);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        final String userName = extras.getString("UserName");
        Cafe cafe = extras.getParcelable("cafe");
        meals = cafe.getCafeMeals();

        if (isCafeOpen(floatTime.getHour(), floatTime.getMinute())){
            orderTime.setText(updateDisplay());
        } else {
            Intent toMainActivity = new Intent(TimeActivity.this, MainActivity.class);
            startActivity(toMainActivity);
        }

        floatTime.setOnTimeChangedListener( (TimePicker view, int hourOfDay, int minute) -> {
            if (isCafeOpen(hourOfDay, minute) &&
                    isAllowableTime(hourOfDay, minute)) {
                updateDisplay(hourOfDay, minute);
            }
        });

        submitTime = findViewById(R.id.submit_time);
        submitTime.setOnClickListener( (View view) -> {
            if (!checkPreparationTime(floatTime.getHour(), floatTime.getMinute())) {
                Toast.makeText(getApplicationContext(), "Май совість, дай хоча б 15 хвилин на приготування", Toast.LENGTH_SHORT).show();
                //return;
                //floatTime.setHour(floatTime.getCurrentHour() + ((floatTime.getCurrentMinute() + 15 < minutesInHour) ? 1 : 0));
                //floatTime.setMinute((floatTime.getCurrentMinute()+15) % minutesInHour);
            }
            Intent OpenMyOrder = new Intent(TimeActivity.this, MyOrdersActivity.class);
            OpenMyOrder.putExtra("UserName", userName);
            OpenMyOrder.putExtra("cafe", cafe);
            OpenMyOrder.putExtra("order time",String.format("%02d:%02d", floatTime.getHour(), floatTime.getMinute()));
            startActivity(OpenMyOrder);
            overridePendingTransition(R.anim.from_bottom_to_top, R.anim.from_bottom_to_top_exit);
        });

        ImageView backButton =  findViewById(R.id.back_from_time);
        backButton.setOnClickListener( (View v) -> finish() );

        ImageView buttonToMain = findViewById(R.id.horse_icon_from_time);
        buttonToMain.setOnClickListener( (View v) -> {
            Intent toMainActivity = new Intent(TimeActivity.this, MainActivity.class);
            startActivity(toMainActivity);
            overridePendingTransition(R.anim.from_top_to_bottom_exit, R.anim.from_top_to_bottom);
        });
    }

    private void updateDisplay(int hour, int minute) {
        Integer orderHour = hour;
        Integer orderMinute = minute;
        orderTime.setText(String.format("%02d:%02d", orderHour, orderMinute));
    }

    private String updateDisplay() {
        Integer currentHour = floatTime.getHour();
        Integer currentMinute = floatTime.getMinute();
        return String.format("%02d:%02d",currentHour,currentMinute);
    }

    private boolean isAllowableTime(int orderHour, int orderMinute) {
        int currentHour = floatTime.getCurrentHour();
        int currentMinute = floatTime.getCurrentMinute();

        if (orderHour < currentHour ||
                (orderHour == currentHour && orderMinute < currentMinute)
        ) {
            if (!wasShownToastForPast) {
                Toast.makeText(this, "Ей, не можна робити замовлення в минулому часі", Toast.LENGTH_SHORT).show();
                wasShownToastForPast = true;
            }
            floatTime.setHour(currentHour);
            floatTime.setMinute(currentMinute);
            return false;
        }
        return true;
    }

    private boolean isNearNewHour() {
        return minutesInHour - preparationTime <= floatTime.getCurrentMinute();
    }

    private boolean isCafeOpen(int orderHour, int orderMinute) {
        if (orderHour > closingHour || (orderHour == closingHour && orderMinute > 0)) {
            Toast.makeText(this, "Вибач, але кафе вже зачинено", Toast.LENGTH_SHORT).show();
            floatTime.setHour(closingHour % 24);
            floatTime.setMinute(0);
            return false;
        }
        if (orderHour < openingHour) {
            Toast.makeText(this, "Вибач, але кафе ще зачинено", Toast.LENGTH_SHORT).show();
            floatTime.setHour(openingHour);
            floatTime.setMinute(0);
            return false;
        }
        return true;
    }

    private boolean checkPreparationTime(int orderHour, int orderMinute) {
        int currentHour = floatTime.getCurrentHour();
        int currentMinute = floatTime.getCurrentMinute();

        if ((isNearNewHour() && (orderHour == currentHour ||
                ((orderHour == currentHour + 1) &&
                        (orderMinute < ((currentMinute + preparationTime) % minutesInHour))
                ))) ||
                (orderHour == currentHour &&
                        (orderMinute < currentMinute + preparationTime)
                )
        ) return false;
        return true;
        *//*
        if (isNearNewHour() &&
                (orderHour == currentHour ||
                        (orderHour == currentHour + 1 &&
                             orderMinute < ((currentMinute + preparationTime) % minutesInHour)
                        )
                )
        )
                return false;
        return (!(
                orderHour == currentHour &&
                orderMinute < currentMinute + preparationTime
        ));
        *//*
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.from_top_to_bottom_exit, R.anim.from_top_to_bottom);
    }*/
}