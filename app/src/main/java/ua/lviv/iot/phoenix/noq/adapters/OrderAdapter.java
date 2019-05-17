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
import ua.lviv.iot.phoenix.noq.models.Cafe;
import ua.lviv.iot.phoenix.noq.models.Order;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    private List<Order> orderList;

    private Resources r;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, time, location;
        public ImageView icon;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name_of_cafe_for_UO);
            time = view.findViewById(R.id.time_of_order);
            location = view.findViewById(R.id.location_of_cafe_for_UO);
            icon = view.findViewById(R.id.photo_of_cafe_for_UO);
        }
    }

    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_orders_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Order order = orderList.get(position);
        Cafe cafe = order.getCafe();
        holder.name.setText(cafe.getName());
        holder.time.setText(order.getTime());
        holder.location.setText(cafe.getLocation());
        System.out.println(cafe.hasImage());
        System.out.println(cafe.getIcon());
        if (cafe.hasImage()) {
            holder.icon.setImageResource(r.getIdentifier(cafe.getIcon(),
                    "drawable", "ua.lviv.iot.phoenix.noq_cashier"));
        } else {
            holder.icon.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public void setList(List<Order> list) {
        orderList = list;
    }

    public void setR(Resources r) {
        this.r = r;
    }
}
