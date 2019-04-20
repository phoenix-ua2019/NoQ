package ua.lviv.iot.phoenix.noq.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class ListOfCafes extends AppCompatActivity {

    /*DatabaseReference cafeRef = Useful.cafeRef;
    ArrayList<Cafe> cafes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_cafes);

        cafeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<HashMap> temp_cafes =
                        new ArrayList(((HashMap<String, HashMap<String,?>>) dataSnapshot.getValue()).values());
                cafes = (ArrayList<Cafe>) temp_cafes.stream().map(Cafe::new).collect(Collectors.toList());
                cafes = (ArrayList<Cafe>) cafes.stream()
                        .map( (Cafe c) -> c.setDrawable(getResources(), getPackageName())
                        ).collect(Collectors.toList());
                cafes.forEach(System.out::println);
                final String userName = getIntent().getExtras().getString("UserName");
                ((ListView) findViewById(R.id.cafe_list)).setAdapter(new CafeAdapter(ListOfCafes.this, cafes));

                ListView listView = findViewById(R.id.cafe_list);
                listView.setOnItemClickListener( (AdapterView<?> adapter, View view, int position, long l) -> {
                    Intent OpenMenu = new Intent(ListOfCafes.this, ListOfMeals.class);
                    OpenMenu.putExtra("UserName", userName);
                    OpenMenu.putExtra("cafe", cafes.get(position));
                    startActivity(OpenMenu);
                    overridePendingTransition(R.anim.from_bottom_to_top, R.anim.from_bottom_to_top_exit);
                });

                ImageView buttonToMain = findViewById(R.id.horse_icon_from_cafes);
                buttonToMain.setOnClickListener( (View v) -> {
                    startActivity(new Intent(ListOfCafes.this, MainActivity.class));
                    overridePendingTransition(R.anim.from_top_to_bottom_exit, R.anim.from_top_to_bottom);
                });

                ImageView backButton = findViewById(R.id.back_from_cafes) ;
                backButton.setOnClickListener( (View v) -> finish());
        }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.from_top_to_bottom_exit, R.anim.from_top_to_bottom);
    }*/
}
