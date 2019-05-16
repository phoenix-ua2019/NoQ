package ua.lviv.iot.phoenix.noq.adapters;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ua.lviv.iot.phoenix.noq.R;
import ua.lviv.iot.phoenix.noq.models.Cafe;


public class CafeAdapter extends RecyclerView.Adapter<CafeAdapter.MyViewHolder> {

    private List<Cafe> cafesList;

    private Resources r;

    public CafeAdapter(List<Cafe> cafesList) {
        this.cafesList = cafesList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView cafeName;
        public TextView cafeLocation;
        public TextView cafeEmail;
        public ImageView mDrawable;

        public MyViewHolder(View view) {
            super(view);
            cafeName = view.findViewById(R.id.name_of_cafe);
            cafeLocation = view.findViewById(R.id.location_of_cafe);
            mDrawable = view.findViewById(R.id.photo_of_cafe);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cafe_list_item, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull CafeAdapter.MyViewHolder holder, int position) {
        Cafe cafe = cafesList.get(position);
        System.out.println("onBindViewHolder!!!!!!!!!!!!");
        System.out.println(cafe);
        holder.cafeName.setText(cafe.getCafeName());
        holder.cafeLocation.setText(cafe.getCafeLocation());
        if (cafe.hasImage()) {
            holder.mDrawable.setImageResource(r.getIdentifier(cafe.getIcon(),
                    "drawable", "ua.lviv.iot.phoenix.noq"));
        } else {
            holder.mDrawable.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return cafesList.size();
    }

    public void setList(List<Cafe> lst) {
        cafesList = lst;
    }


    public void setR(Resources r) {
        this.r = r;
    }
}
