package com.camsys.carmonic.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.camsys.carmonic.History.HistoryActivity;
import com.camsys.carmonic.History.HistoryDetailActivity;
import com.camsys.carmonic.R;
import com.camsys.carmonic.model.HistoryItem;
import com.camsys.carmonic.model.HistoryModel;
import com.camsys.carmonic.model.Result;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.IntStream;

public class HistoryFragmentAdapter extends RecyclerView.Adapter<HistoryFragmentAdapter.ViewHolder> {

    private final ArrayList<Result> accList;
    private final HistoryActivity.OnListFragmentInteractionListener mListener;

    Context context;
    Gson gson = new Gson();

    public HistoryFragmentAdapter(ArrayList<Result> accList, HistoryActivity.OnListFragmentInteractionListener listener, Context context) {
        this.accList = accList;
        mListener = listener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_item, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Date date = null;
        try {
            date = new SimpleDateFormat("D yyyy").parse(accList.get(position).getDate() + " " + Calendar.getInstance().get(Calendar.YEAR));

            System.out.println(date.toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.txtItemDate.setText(date + "");
//        holder.txtRequester.setText(accList.get(position).getItemRequester());
//        holder.txtAmount.setText(accList.get(position).getAmount());
        setRating(holder, Integer.parseInt(accList.get(position).getStarRating()));
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, HistoryDetailActivity.class);
                intent.putExtra("historyitem", gson.toJson(accList.get(position)));
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return accList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView txtItemDate;
        final TextView txtRequester;
        final TextView txtAmount;
        final ImageView star1;
        final ImageView star2;
        final ImageView star3;
        final ImageView star4;
        final ImageView star5;

        public HistoryItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            txtItemDate = view.findViewById(R.id.txtItemDate);
            txtRequester = view.findViewById(R.id.txtItemRequester);
            txtAmount = view.findViewById(R.id.txtAmount);
            star1 = view.findViewById(R.id.start1);
            star2 = view.findViewById(R.id.start2);
            star3 = view.findViewById(R.id.start3);
            star4 = view.findViewById(R.id.start4);
            star5 = view.findViewById(R.id.start5);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + txtItemDate.getText() + "'";
        }
    }

    public void setRating(final ViewHolder holder,int  numberOfStart){
        if(numberOfStart == 1){
            holder.star1.setVisibility(View.VISIBLE);
        }
        if(numberOfStart == 2){
            holder.star1.setVisibility(View.VISIBLE);
            holder.star2.setVisibility(View.VISIBLE);
        }
        if(numberOfStart == 3){
            holder.star1.setVisibility(View.VISIBLE);
            holder.star2.setVisibility(View.VISIBLE);
            holder.star3.setVisibility(View.VISIBLE);
        }
        if(numberOfStart == 4){
            holder.star1.setVisibility(View.VISIBLE);
            holder.star2.setVisibility(View.VISIBLE);
            holder.star3.setVisibility(View.VISIBLE);
            holder.star4.setVisibility(View.VISIBLE);
        }
        if(numberOfStart == 5){
            holder.star1.setVisibility(View.VISIBLE);
            holder.star2.setVisibility(View.VISIBLE);
            holder.star3.setVisibility(View.VISIBLE);
            holder.star4.setVisibility(View.VISIBLE);
            holder.star5.setVisibility(View.VISIBLE);

        }
    }
}
