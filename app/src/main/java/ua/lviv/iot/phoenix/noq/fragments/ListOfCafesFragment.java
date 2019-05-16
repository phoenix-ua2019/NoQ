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
                //Cafe cafe = cafesList.get(position);
                //((ListView) view.findViewById(R.id.cafe_recycler_view)).setOnItemClickListener(
                        //(AdapterView<?> adapter, View v, int p, long l) -> {
                Bundle b = new Bundle();
                b.putParcelable("cafe", cafesList.get(position));
                setArguments(b);
                System.out.println("I put extra cafe there!!!!!!!!!!!!!!!");
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
        //ArrayList<HashMap> temp_cafes =;
        cafesList = (ArrayList<Cafe>) (new ArrayList(((HashMap<String, HashMap<String,?>>)
                dataSnapshot.getValue()).values())).stream().map(Cafe::new).collect(Collectors.toList());
        System.out.println(R.drawable.aroma_kava);
        System.out.println(cafesList);
        /*if (!isAdded()) {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
        prepareCafeData();

        System.out.println(cafesList);
        cafesAdapter.notifyDataSetChanged();
        /*ListView listView = view.findViewById(R.id.list_of_cafe);
        ArrayAdapter<Cafe> arrayAdapter =new ArrayAdapter<>(currentActivity,
                R.layout.fragment_list_of_cafes, cafesList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(
                (AdapterView<?> adapterView, View view, int index, long l) -> {
                    //Object c = adapterView.getAdapter().getItem(index);
                    Bundle b = new Bundle();
                    b.putParcelable("cafe", cafesList.get(index));
                    setArguments(b);
        });*/
        /*((ListView) view.findViewById(R.id.list_of_cafe)).setAdapter(new ArrayAdapter(getActivity(),
            R.layout.fragment_list_of_cafes,
            cafesList));*/
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        System.out.println("The read failed: " + databaseError.getCode());
    }


    private void prepareCafeData() {
        /*cafesList = cafesList.stream()
                .map(c -> c.setDrawable(getResources(), currentActivity.getPackageName())
                ).collect(Collectors.toList());*/
        System.out.println(currentActivity.getPackageName());
        cafesAdapter.setR(getResources());
        cafesAdapter.setList(cafesList);
        cafesAdapter.notifyDataSetChanged();
    }

}
