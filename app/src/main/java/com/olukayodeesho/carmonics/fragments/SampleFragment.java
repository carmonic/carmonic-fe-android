package com.olukayodeesho.carmonics.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.olukayodeesho.carmonics.MainActivity;
import com.olukayodeesho.carmonics.R;

public class SampleFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // return super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.main_landing, container, false);
        return view;
    }

    public void GoToXPage() {
        ((MainActivity) this.getActivity()).GoToFragment(new SampleFragment());
    }


}
