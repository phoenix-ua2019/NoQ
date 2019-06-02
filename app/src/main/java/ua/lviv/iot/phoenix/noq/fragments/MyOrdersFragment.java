package ua.lviv.iot.phoenix.noq.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ua.lviv.iot.phoenix.noq.R;
import ua.lviv.iot.phoenix.noq.activities.MainActivity;
import ua.lviv.iot.phoenix.noq.activities.Useful;
import ua.lviv.iot.phoenix.noq.adapters.OrderAdapter;
import ua.lviv.iot.phoenix.noq.listeners.RecyclerTouchListener;
import ua.lviv.iot.phoenix.noq.models.Order;


public class MyOrdersFragment extends Fragment {

    private List<Order> orderList = new ArrayList<>();
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private boolean cafeUpdated = false, userUpdated = false;
    private View view;

    MainActivity currentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_my_orders, container, false);
        DatabaseReference userReference = Useful.orderRef
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        try {
            Order finalOrder = getArguments().getParcelable("order");
            DatabaseReference cafeReference = Useful.orderRef.child(finalOrder.getCafe().getCid());
            userReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (!userUpdated) {
                        long countUser = dataSnapshot.getChildrenCount();
                        finalOrder.setUserPos(countUser);
                        userUpdated = true;
                        cafeReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (!cafeUpdated) {
                                    long countCafe = dataSnapshot.getChildrenCount();
                                    finalOrder.setPos(countCafe);
                                    userReference.child("" + countUser).setValue(finalOrder);
                                    cafeReference.child("" + countCafe).setValue(finalOrder);
                                    cafeUpdated = true;
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) { }
                        });
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        setArguments(new Bundle());

        recyclerView = view.findViewById(R.id.user_orders_recycler_view);
        orderAdapter = new OrderAdapter(orderList);
        orderAdapter.setFragment(this);
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
                        b.putParcelable("see_order", orderList.get(position));
                        setArguments(b);
                        currentActivity.b5(view);
                    }

            @Override
            public void onLongClick(View v, int position) {
            }
        }));

        userReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                int size = orderList.size();
                orderList.add(new Order(dataSnapshot.getValue()));
                orderAdapter.notifyItemChanged(size);
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Order order = new Order(dataSnapshot.getValue());
                System.out.println(dataSnapshot.getValue());
                System.out.println(s);
                int pos;
                if (s==null) {
                    pos = 0;
                } else {
                    pos = Integer.parseInt(s)+1;
                }
                //int pos = (s == null ? 0 : Integer.parseInt(s))+1;
                orderList.set(pos,order);
                orderAdapter.notifyItemChanged(pos);
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                System.out.println(dataSnapshot);
                int pos = Integer.parseInt(dataSnapshot.getKey());
                orderList.remove(pos);
                orderAdapter.notifyItemChanged(pos);
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        });

        return view;
    }

}
