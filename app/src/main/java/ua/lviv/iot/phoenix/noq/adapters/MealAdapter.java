package ua.lviv.iot.phoenix.noq.adapters;

import android.content.res.Resources;
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
    private Resources r;


    public Resources getR() {
        return r;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView mealPicture;
        public TextView mealName, selectedQuantity, mealPrice, timeToPrepare, description;

        public MyViewHolder(View view) {
            super(view);
            mealPicture = view.findViewById(R.id.photo_of_meal);
            mealName = view.findViewById(R.id.name_of_meal);
            mealPrice = view.findViewById(R.id.price_of_meal);
            selectedQuantity = view.findViewById(R.id.selected_quantity);
            timeToPrepare = view.findViewById(R.id.time_to_prepare);
            description = view.findViewById(R.id.description);
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
        holder.mealPrice.setText(meal.getPrice()+" грн");
        holder.timeToPrepare.setText(meal.getTime()+" хв");
        holder.description.setText(meal.getDescription());
        if (meal.getSelectedQuantity() > 0) {
            holder.selectedQuantity.setText(meal.selectedQuantityToString());
        } else {
            holder.selectedQuantity.setText("");
        }
        if (meal.hasImage()) {
            holder.mealPicture.setImageResource(r.getIdentifier(meal.getMealPicture(),
                    "drawable", "ua.lviv.iot.phoenix.noq"));
        } else {
            holder.mealPicture.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    public void setList(List<Meal> list) {
        mealList = list;
    }

    public void setR(Resources r) {
        this.r = r;
    }

}

