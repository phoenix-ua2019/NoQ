package ua.lviv.iot.phoenix.noq;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderAdapter extends ArrayAdapter<Order> {

    OrderAdapter(Activity context, ArrayList<Order> orders) {
        super(context, 0, orders);
    }

    /**
     * цей метод підставляє дані в форму замовлення.
     * тобто він для кожної позиції в кожному елементі ListView підставляє те що потрібно.
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    /*@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout., parent, false);
        }

        Order currentOrder = getItem(position);

        TextView cafeTextView = listItemView.findViewById(R.id.cafe_text_view);
        cafeTextView.setText(currentOrder.getCafe().getCafeName());


        TextView sumTextView = listItemView.findViewById(R.id.sum_text_view);
        sumTextView.setText(currentOrder.getSum());

        TextView timeTextView = listItemView.findViewById(R.id.time_text_view);
        timeTextView.setText(currentOrder.getTime());

        TextView dateTextView = listItemView.findViewById(R.id.date_text_view);
        dateTextView.setText(currentOrder.getDate().toString());

        return listItemView;

    }*/


}
