package ua.lviv.iot.phoenix.noq.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ua.lviv.iot.phoenix.noq.R;

public class OrderFragment extends Fragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_order, container, false);

        String time = getArguments().getString("time");

        ((TextView) view.findViewById(R.id.selected_time_show)).setText(time);

        return view;
    }

}
