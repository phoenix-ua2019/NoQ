package ua.lviv.iot.phoenix.noq;

import android.widget.ArrayAdapter;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MealAdapter extends ArrayAdapter<Meal> {
    MealAdapter(Activity context, ArrayList<Meal> meals) {
        super(context, 0, meals);
    }

    /*@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.menu_list_item, parent, false);
        }
        final Meal currentMeal = getItem(position);


        TextView mealNameTextView = listItemView.findViewById(R.id.meal_name_text_view);
        mealNameTextView.setText(currentMeal.getMealName());

        TextView priceTypeTextView = listItemView.findViewById(R.id.price_type_text_view);
        priceTypeTextView.setText(currentMeal.getMealPrice() + " грн");

        CheckBox mealCheckBox = listItemView.findViewById(R.id.meal_checkbox);
        currentMeal.setChecked(mealCheckBox.isChecked());
        mealCheckBox.setOnCheckedChangeListener(
                (CompoundButton buttonView, boolean isChecked) ->
                        currentMeal.setChecked(isChecked)
        );
        return listItemView;
    }*/
}
