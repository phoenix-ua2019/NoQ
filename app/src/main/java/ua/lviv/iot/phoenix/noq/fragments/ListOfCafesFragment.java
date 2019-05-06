package ua.lviv.iot.phoenix.noq.fragments;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import ua.lviv.iot.phoenix.noq.R;
import ua.lviv.iot.phoenix.noq.activities.Useful;
import ua.lviv.iot.phoenix.noq.adapters.CafeAdapter;
import ua.lviv.iot.phoenix.noq.adapters.MealAdapter;
import ua.lviv.iot.phoenix.noq.listeners.MealRecyclerTouchListener;
import ua.lviv.iot.phoenix.noq.models.Cafe;
import ua.lviv.iot.phoenix.noq.models.Meal;


public class ListOfCafesFragment extends Fragment implements ValueEventListener {

    private List<Cafe> cafesList = new ArrayList<>();
    private RecyclerView cafesRecyclerView;
    private CafeAdapter cafesAdapter;
    private View view;

    FragmentActivity currentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_of_cafes, container, false);

        cafesRecyclerView = view.findViewById(R.id.cafe_recycler_view);
        cafesAdapter = new CafeAdapter(cafesList);
        cafesRecyclerView.setAdapter(cafesAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        cafesRecyclerView.setLayoutManager(mLayoutManager);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        currentActivity = getActivity();

        cafesRecyclerView.setLayoutManager(new LinearLayoutManager(currentActivity));
        cafesRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // row click listener
        cafesRecyclerView.addOnItemTouchListener( new MealRecyclerTouchListener(
                currentActivity.getApplicationContext(), cafesRecyclerView,
                new MealRecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //Cafe cafe = cafesList.get(position);
                ((ListView) view.findViewById(R.id.list_of_cafe)).setOnItemClickListener(
                        (AdapterView<?> adapter, View v, int p, long l) -> {
                            Bundle b = new Bundle();
                            b.putParcelable("cafe", cafesList.get(position));
                            setArguments(b);
                        });
                //Toast.makeText(getApplicationContext(), meal.getMealName() + " is selected!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onLongClick(View v, int position) {
            }
        }));
        Useful.cafeRef.addValueEventListener(this);
        return view;
    }
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        //ArrayList<HashMap> temp_cafes =;
        cafesList = (ArrayList<Cafe>) (new ArrayList(((HashMap<String, HashMap<String,?>>)
                dataSnapshot.getValue()).values())).stream().map(Cafe::new).collect(Collectors.toList());
        System.out.println(cafesList);
        /*if (!isAdded()) {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
        cafesList = cafesList.stream()
                .map(c -> c.setDrawable(getContext().getResources(), getActivity().getPackageName())
                ).collect(Collectors.toList());
        System.out.println(cafesList);
        cafesAdapter.notifyDataSetChanged();
        /*((ListView) view.findViewById(R.id.list_of_cafe)).setAdapter(new ArrayAdapter(getActivity(),
            R.layout.fragment_list_of_cafes,
            cafesList));*/
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        System.out.println("The read failed: " + databaseError.getCode());
    }


    /*private void prepareCafeData() {

        Cafe cafe_1 = new Cafe("Cafe_1","Location_1");
        cafesList.add(cafe_1);

        Cafe cafe_2 = new Cafe("Cafe_2","Location_2");
        cafesList.add(cafe_2);

        Cafe cafe_3 = new Cafe("Cafe_3","Location_3");
        cafesList.add(cafe_3);

        Cafe cafe_4 = new Cafe("Cafe_4","Location4");
        cafesList.add(cafe_4);

        cafesAdapter.notifyDataSetChanged();
    }*/

}
