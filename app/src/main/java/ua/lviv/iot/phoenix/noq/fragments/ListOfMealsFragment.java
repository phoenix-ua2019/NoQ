package ua.lviv.iot.phoenix.noq.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

import ua.lviv.iot.phoenix.noq.R;
import ua.lviv.iot.phoenix.noq.activities.MainActivity;
import ua.lviv.iot.phoenix.noq.adapters.MealAdapter;
import ua.lviv.iot.phoenix.noq.listeners.RecyclerTouchListener;
import ua.lviv.iot.phoenix.noq.models.Cafe;
import ua.lviv.iot.phoenix.noq.models.Meal;


public class ListOfMealsFragment extends Fragment {

    private Cafe cafe;
    private ArrayList<Meal> mealList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MealAdapter mealAdapter;
    private View view;

    ImageView plus, minus;
    FloatingActionButton chooseTimeBtn;
    Dialog quantityDialog;
    MainActivity currentActivity;

    int commonSelectedAmount = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_of_menu, container, false);
        currentActivity = (MainActivity) getActivity();

        recyclerView = view.findViewById(R.id.recycler_view);
        mealAdapter = new MealAdapter(new ArrayList<>());
        mealAdapter.setFragment(this);
        recyclerView.setAdapter(mealAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        recyclerView.setLayoutManager(new LinearLayoutManager(currentActivity));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        FloatingActionButton fab = view.findViewById(R.id.choose_time);
        fab.attachToRecyclerView(recyclerView);
        //mealList form db
        cafe = getArguments().getParcelable("cafe");
        mealList = cafe.getMeals();

        // row click listener
        recyclerView.addOnItemTouchListener(
                new RecyclerTouchListener(currentActivity.getApplicationContext(),
                        recyclerView, new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Meal meal = mealList.get(position);
                        //int itemPosition = recyclerView.getChildLayoutPosition(view);
                        quantityDialogCaller(meal, mealList);
                        cafe.setMeals(mealList);
                        Bundle b = getArguments();
                        b.putParcelable("time_cafe", cafe);
                        setArguments(b);
                    }

                    @Override
                    public void onLongClick(View v, int position) {
                    }
                }));
        for (Meal meal : mealList) {
            commonSelectedAmount += meal.getSelectedQuantity();
        }
        mealAdapter.setList(mealList);
        mealAdapter.notifyDataSetChanged();
        mealAdapter.setFragment(this);
        chooseTimeBtn = view.findViewById(R.id.choose_time);

        if (commonSelectedAmount > 0) {
            chooseTimeBtn.setVisibility(View.VISIBLE);
        } else {
            chooseTimeBtn.setVisibility(View.INVISIBLE);
        }

        return view;
    }

    public void quantityDialogCaller(Meal meal, ArrayList<Meal> mealList) {
        quantityDialog = new Dialog(currentActivity);
        quantityDialog.setContentView(R.layout.quantity_item);

        plus = quantityDialog.findViewById(R.id.plus_button);
        minus = quantityDialog.findViewById(R.id.minus_button);
        //TextView selectedQuantity = view.findViewById(R.id.selected_quantity);
        TextView dialogQuantity = quantityDialog.findViewById(R.id.dialog_quantity);

        dialogQuantity.setText(meal.selectedQuantityToString());

        plus.setEnabled(true);
        minus.setEnabled(true);


        plus.setOnClickListener((View v) -> {
            chooseTimeBtn.setVisibility(View.VISIBLE);
            commonSelectedAmount++;
            meal.setSelectedQuantity(meal.getSelectedQuantity() + 1);
            dialogQuantity.setText(meal.selectedQuantityToString());
            //recyclerView.setAdapter(mealAdapter);
            //System.out.println(mealList.indexOf(meal));
            /*mealAdapter.notifyItemChanged(mealList.indexOf(meal));
            for (Meal mealItem : mealList) {
                System.out.println(mealItem.getSelectedQuantity());
            }
            System.out.println("_______");
            */
            mealAdapter.notifyItemChanged(mealList.indexOf(meal));
        });

        minus.setOnClickListener((View v) -> {
            if  (meal.getSelectedQuantity() <= 0) { return; }
            commonSelectedAmount--;
            if (commonSelectedAmount == 0) { chooseTimeBtn.setVisibility(View.INVISIBLE); }
            meal.setSelectedQuantity(meal.getSelectedQuantity() - 1);
            dialogQuantity.setText(meal.selectedQuantityToString());
            //recyclerView.setAdapter(mealAdapter);
            //System.out.println(mealList.indexOf(meal));
            mealAdapter.notifyItemChanged(mealList.indexOf(meal));
        });
        quantityDialog.show();
    }

}
