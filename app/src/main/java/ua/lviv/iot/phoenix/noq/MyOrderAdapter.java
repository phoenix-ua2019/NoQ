package ua.lviv.iot.phoenix.noq;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyOrderAdapter extends ArrayAdapter<Meal> {
    MyOrderAdapter(Activity context, ArrayList<Meal> meals) {
        super(context, 0, meals);
    }

    /*@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.my_order_item, parent, false);
        }
        final Meal currentMeal = getItem(position);

        TextView mealNameTextView = listItemView.findViewById(R.id.dish_name);
        mealNameTextView.setText(currentMeal.getMealName());

        TextView priceTypeTextView = listItemView.findViewById(R.id.dish_price);
        priceTypeTextView.setText(currentMeal.getMealPrice() * currentMeal.getQuantity() + " грн");

        TextView quantityTextView = listItemView.findViewById(R.id.dish_quantity);
        quantityTextView.setText(Integer.toString(currentMeal.getQuantity()));
        return listItemView;
    }*/
}


