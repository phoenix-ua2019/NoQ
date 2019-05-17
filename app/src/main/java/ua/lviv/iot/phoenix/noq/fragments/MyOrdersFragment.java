package ua.lviv.iot.phoenix.noq.fragments;

import android.content.Context;
import android.net.Uri;
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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ua.lviv.iot.phoenix.noq.R;
import ua.lviv.iot.phoenix.noq.activities.MainActivity;
import ua.lviv.iot.phoenix.noq.adapters.OrderAdapter;
import ua.lviv.iot.phoenix.noq.listeners.RecyclerTouchListener;
import ua.lviv.iot.phoenix.noq.models.Order;


public class MyOrdersFragment extends Fragment {

    private List<Order> orderList = new ArrayList<>();
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private View view;

    MainActivity currentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_my_orders, container, false);

        setArguments(new Bundle());

        recyclerView = view.findViewById(R.id.user_orders_recycler_view);
        orderAdapter = new OrderAdapter(orderList);
        orderAdapter.setR(getResources());
        recyclerView.setAdapter(orderAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        currentActivity = (MainActivity) getActivity();

        recyclerView.setLayoutManager(new LinearLayoutManager(currentActivity));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // row click listener
        recyclerView.addOnItemTouchListener(
                new RecyclerTouchListener(currentActivity.getApplicationContext(),
                        recyclerView, new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Bundle b = new Bundle();
                        b.putParcelable("order", orderList.get(position));
                        setArguments(b);
                        currentActivity.b5(view);
                    }

            @Override
            public void onLongClick(View v, int position) {
            }
        }));

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("orders")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (int i=0; i<dataSnapshot.getChildrenCount(); i++)
                    orderList.add(new Order(((List<Map<String,Map>>) dataSnapshot.getValue()).get(i)));
                System.out.println(orderList);
                System.out.println(1000000);
                orderAdapter.setList(orderList);
                orderAdapter.notifyDataSetChanged();
               /* for (int i=0; i<dataSnapshot.getChildrenCount(); i++)
                    orderList.add(((List<Order>)((List<Map<String,Map>>) dataSnapshot.getValue()).get(i).get("cafe").get("cafeOrders")).get(0));
                orderAdapter.setList(orderList);
                orderAdapter.notifyDataSetChanged();*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

}
