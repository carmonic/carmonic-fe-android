package com.camsys.carmonic.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.camsys.carmonic.R;

public class ComplementFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // return super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.activity_edit_mech_complmt, container, false);
        return view;
    }

    public void GoToXPage() {
        //  ((MainActivity)this.getActivity()).GoToFragment(new ComplementFragment());
    }


}
