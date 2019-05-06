package ua.lviv.iot.phoenix.noq.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ua.lviv.iot.phoenix.noq.R;
import ua.lviv.iot.phoenix.noq.models.Cafe;


public class CafeAdapter extends RecyclerView.Adapter<CafeAdapter.MyViewHolder> {

    private List<Cafe> cafesList;

    public CafeAdapter(List<Cafe> cafesList) {
        this.cafesList = cafesList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView cafeName;
        public TextView cafeLocation;
        public TextView cafeEmail;
        public TextView temp_mDrawableId;
        public int mDrawableId;

        public MyViewHolder(View view) {
            super(view);
            cafeName = view.findViewById(R.id.name_of_cafe);
            cafeLocation = view.findViewById(R.id.location_of_cafe);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cafe_list_item, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(CafeAdapter.MyViewHolder holder, int position) {
        Cafe cafe = cafesList.get(position);
        holder.cafeName.setText(cafe.getCafeName());
        holder.cafeLocation.setText(cafe.getCafeLocation());
    }

    @Override
    public int getItemCount() {
        return cafesList.size();
    }
}

   /* @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.cafe_list_item, parent, false);
        }
        Cafe currentCafe = getItem(position);

        TextView cafeNameTextView = listItemView.findViewById(R.id.cafe_name_text_view);
        cafeNameTextView.setText(currentCafe.getCafeName());

        ImageView imageView = listItemView.findViewById(R.id.cafe_icon_view);
        if (currentCafe.hasImage()) {
            imageView.setImageResource(currentCafe.getDrawableId());
        } else {
            imageView.setVisibility(View.GONE);
        }
        TextView cafeLocationTextView = listItemView.findViewById(R.id.cafe_location_text_view);
        cafeLocationTextView.setText(currentCafe.getCafeLocation());
        return listItemView;

    }*/
