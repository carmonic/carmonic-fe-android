package com.camsys.carmonic.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.camsys.carmonic.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmLocationFragment extends Fragment {

    private static int TIME_OUT = 2000;

    public ConfirmLocationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_confirm_location, container, false);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                //    startActivity(new Intent(locating_mechanic.this, billing_main.class));
                // ((MainActivity)this.getActivity()).GoToFragment(new ComplementFragment());

            }
        }, TIME_OUT);

        return inflater.inflate(R.layout.activity_locating_mechanic, container, false);

    }

}
