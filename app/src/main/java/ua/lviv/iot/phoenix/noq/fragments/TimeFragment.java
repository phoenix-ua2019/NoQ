package ua.lviv.iot.phoenix.noq.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ua.lviv.iot.phoenix.noq.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimeFragment extends Fragment {


    public TimeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_time, container, false);
    }

}
