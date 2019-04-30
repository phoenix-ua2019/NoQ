package ua.lviv.iot.phoenix.noq.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ua.lviv.iot.phoenix.noq.R;
import ua.lviv.iot.phoenix.noq.models.Meal;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MyViewHolder> {

    private List<Meal> mealList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView mealPicture;
        public TextView mealName, selectedQuantity;

        public MyViewHolder(View view) {
            super(view);
            mealName = (TextView) view.findViewById(R.id.name_of_meal);
            selectedQuantity = (TextView) view.findViewById(R.id.selected_quantity);
        }
    }

    public MealAdapter(List<Meal> mealList) {
        this.mealList = mealList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Meal meal = mealList.get(position);
        holder.mealName.setText(meal.getMealName());
        if (meal.getSelectedQuantity() > 0) {
            holder.selectedQuantity.setText(meal.selectedQuantityToString());
        }
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }
}

    /*MealAdapter(Activity context, ArrayList<Meal> meals) {
        super(context, 0, meals);
    }

    @Override
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

