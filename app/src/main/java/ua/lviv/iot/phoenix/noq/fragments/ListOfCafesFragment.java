package ua.lviv.iot.phoenix.noq.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import ua.lviv.iot.phoenix.noq.R;
import ua.lviv.iot.phoenix.noq.activities.MainActivity;
import ua.lviv.iot.phoenix.noq.activities.Useful;
import ua.lviv.iot.phoenix.noq.adapters.CafeAdapter;
import ua.lviv.iot.phoenix.noq.listeners.RecyclerTouchListener;
import ua.lviv.iot.phoenix.noq.models.Cafe;


public class ListOfCafesFragment extends Fragment implements ValueEventListener {

    private List<Cafe> cafesList = new ArrayList<>();
    private RecyclerView cafesRecyclerView;
    private CafeAdapter cafesAdapter;
    private View view;

    MainActivity currentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_of_cafes, container, false);

        Useful.cafeRef.addValueEventListener(this);

        cafesRecyclerView = view.findViewById(R.id.cafe_recycler_view);
        cafesAdapter = new CafeAdapter(new ArrayList<>());
        cafesRecyclerView.setAdapter(cafesAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        cafesRecyclerView.setLayoutManager(mLayoutManager);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        currentActivity = (MainActivity) getActivity();

        cafesRecyclerView.setLayoutManager(new LinearLayoutManager(currentActivity));
        cafesRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // row click listener
        cafesRecyclerView.addOnItemTouchListener( new RecyclerTouchListener(
                currentActivity.getApplicationContext(), cafesRecyclerView,
                new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle b = new Bundle();
                b.putParcelable("cafe", cafesList.get(position));
                setArguments(b);
                currentActivity.b1(view);
                //Toast.makeText(getApplicationContext(), meal.getMealName() + " is selected!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onLongClick(View v, int position) {
            }
        }));
        return view;
    }
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        cafesList = (ArrayList<Cafe>) (new ArrayList(((HashMap<String, HashMap<String,?>>)
                dataSnapshot.getValue()).values())).stream().map(Cafe::new).collect(Collectors.toList());
        cafesAdapter.setFragment(this);
        prepareCafeData();

        cafesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        System.out.println("The read failed: " + databaseError.getCode());
    }


    private void prepareCafeData() {
        cafesAdapter.setList(cafesList);
        cafesAdapter.notifyDataSetChanged();
    }

}
