package ua.lviv.iot.phoenix.noq.adapters;

import android.content.res.Resources;
import android.support.annotation.NonNull;
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


public class CafeAdapter extends RecyclerView.Adapter<CafeAdapter.MyViewHolder> {

    private List<Cafe> cafesList;

    private Resources r;
    private Fragment f;
    private View v;

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
        holder.cafeName.setText(cafe.getName());
        holder.cafeLocation.setText(cafe.getLocation());
        if (cafe.hasImage()) {
            Useful.iconsRef.child(cafe.getIcon()+".png").getDownloadUrl().addOnSuccessListener(uri ->
                GlideApp.with(f)
                        .load(uri)
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(holder.mDrawable)
            ).addOnFailureListener(Exception::printStackTrace);
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
    public void setFragment(Fragment f) {
        this.f = f;
    }
}
