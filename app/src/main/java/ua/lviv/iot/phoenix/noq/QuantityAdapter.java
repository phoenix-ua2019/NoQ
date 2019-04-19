package ua.lviv.iot.phoenix.noq;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuantityAdapter extends ArrayAdapter<Meal> {
    QuantityAdapter(Activity context, ArrayList<Meal> meals) {
        super(context, 0, meals);
    }

    /*@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.quantity_item, parent, false);
        }
        final Meal currentMeal = getItem(position);

        ((TextView) listItemView.findViewById(R.id.meal_name_text_view))
            .setText(currentMeal.getMealName());

        final TextView priceTypeTextView = listItemView.findViewById(R.id.price_type_text_view);
        priceTypeTextView.setText(currentMeal.getMealPrice() + " грн");

        final TextView quantityTextView = listItemView.findViewById(R.id.quantity_text_view);
        quantityTextView.setText(Integer.toString(currentMeal.getQuantity()));


        Button plusButton = listItemView.findViewById(R.id.plus_button);
        plusButton.setOnClickListener( (View v) -> {
            int quantity = currentMeal.incrementQuantity();
            quantityTextView.setText(Integer.toString(quantity));
            int total = currentMeal.getMealPrice() * (quantity == 0 ? 1 : quantity);
            priceTypeTextView.setText(total + " грн");
        });


        Button minusButton = listItemView.findViewById(R.id.minus_button);
        minusButton.setOnClickListener( (View v) -> {
            int quantity = currentMeal.decrementQuantity();
            if (quantity < 0) {
                Toast.makeText(getContext(), "Не можна обрати від'ємну кількість страв", Toast.LENGTH_SHORT).show();
                return;
            }
            quantityTextView.setText(Integer.toString(quantity));
            int total = currentMeal.getMealPrice() * (quantity == 0 ? 1 : quantity);
            priceTypeTextView.setText(total + " грн");
        });

        return listItemView;
    }*/
}
