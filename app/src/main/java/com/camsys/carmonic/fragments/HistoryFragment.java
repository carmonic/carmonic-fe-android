//package com.camsys.carmonic.fragments;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.util.DisplayMetrics;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.camsys.carmonic.R;
//import com.camsys.carmonic.adapters.HistoryFragmentAdapter;
//import com.camsys.carmonic.model.HistoryItem;
//
//import java.util.ArrayList;
//
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//public class HistoryFragment extends Fragment {
//
//    private OnListFragmentInteractionListener mListener;
//
//    public HistoryFragment() {
//    }
//
//    public static HistoryFragment newInstance() {
//        return new HistoryFragment();
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        View view  = inflater.inflate(R.layout.fragment_history, container, false);
//
//        ArrayList<HistoryItem> list =  new ArrayList<>();
//
//        HistoryItem acc1 =  new HistoryItem();
//        acc1.setJobDate("March 14, 2017, 1:20pm");
//        acc1.setMechanic("Requested by Abeeb");
//        acc1.setNumberOfStars(4);
//        acc1.setAmount("N8765");
//        list.add(acc1);
//
//        RecyclerView recyclerView = view.findViewById(R.id.list);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.setHasFixedSize(true);
//
//        recyclerView.setItemViewCacheSize(15);
//        // recyclerView.setAdapter(new HistoryFragmentAdapter(list, mListener,getActivity()));
//        recyclerView.setAdapter(new HistoryFragmentAdapter(list, mListener,getActivity()));
//
//        return view;
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnListFragmentInteractionListener) {
//            mListener = (OnListFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnListFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    public interface OnListFragmentInteractionListener {
//        void onListFragmentInteraction(HistoryItem item);
//        void onItemClick(View view, int position);
//    }
//}
