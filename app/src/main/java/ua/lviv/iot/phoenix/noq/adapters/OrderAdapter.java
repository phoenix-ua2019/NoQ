package ua.lviv.iot.phoenix.noq.adapters;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.List;

import ua.lviv.iot.phoenix.noq.R;
import ua.lviv.iot.phoenix.noq.activities.Useful;
import ua.lviv.iot.phoenix.noq.models.Cafe;
import ua.lviv.iot.phoenix.noq.models.GlideApp;
import ua.lviv.iot.phoenix.noq.models.Order;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    private List<Order> orderList;

    private Fragment f;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, time, location, status, pos;
        public ImageView icon;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name_of_cafe_for_UO);
            time = view.findViewById(R.id.time_of_order);
            location = view.findViewById(R.id.location_of_cafe_for_UO);
            icon = view.findViewById(R.id.photo_of_cafe_for_UO);
            status = view.findViewById(R.id.state_of_order);
            //pos = view.findViewById(R.id.order_number);
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
        if (cafe.hasImage()) {
            Useful.iconsRef.child(cafe.getIcon()+".png").getDownloadUrl().addOnSuccessListener(uri ->
                GlideApp.with(f)
                        .load(uri)
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(holder.icon)
            ).addOnFailureListener(Exception::printStackTrace);
        } else {
            holder.icon.setVisibility(View.GONE);
        }
        int s = order.getStatus();
        holder.status.setText(s == 1 ? "Прийнято" : s == -1 ? "Відхилено" : "Розглядається");
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public void setFragment(Fragment f) {
        this.f = f;
    }
}
