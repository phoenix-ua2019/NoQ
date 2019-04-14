package ua.lviv.iot.phoenix.noq;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class ListOfOrders extends AppCompatActivity {


    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_orders);

        ImageView buttonToMain = findViewById(R.id.horse_icon_from_my_list_of_orders);
        buttonToMain.setOnClickListener( (View v) -> {
            Intent toMainActivity = new Intent(ListOfOrders.this, MainActivity.class);
            startActivity(toMainActivity);
            overridePendingTransition(R.anim.from_top_to_bottom_exit, R.anim.from_top_to_bottom);
        });

        ImageView backButton = findViewById(R.id.back_from_my_list_of_orders) ;
        backButton.setOnClickListener( (View v) -> finish() );
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.from_top_to_bottom_exit, R.anim.from_top_to_bottom);
    }*/
}
