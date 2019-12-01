//package com.camsys.carmonic.adapters;
//
//import android.content.Context;
//import android.os.Build;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.camsys.carmonic.R;
//import com.camsys.carmonic.fragments.HistoryFragment;
//import com.camsys.carmonic.model.HistoryItem;
//
//import java.util.ArrayList;
//
//import androidx.annotation.RequiresApi;
//import androidx.recyclerview.widget.RecyclerView;
//
//public class HistoryFragmentAdapter extends RecyclerView.Adapter<HistoryFragmentAdapter.ViewHolder> {
//
//    private final ArrayList<HistoryItem> accList;
//    private final HistoryFragment.OnListFragmentInteractionListener mListener;
//    Context context;
//
//    public HistoryFragmentAdapter(ArrayList<HistoryItem> accList, HistoryFragment.OnListFragmentInteractionListener listener, Context context) {
//        this.accList = accList;
//        mListener = listener;
//        this.context = context;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.history_item, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    @Override
//    public void onBindViewHolder(final ViewHolder holder, int position) {
//        holder.txtItemDate.setText(accList.get(position).getJobDate());
//        holder.txtRequester.setText(accList.get(position).getMechanic());
//        holder.txtAmount.setText(accList.get(position).getAmount());
//        setRating(holder,accList.get(position).getNumberOfStars());
//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent = new Intent(context, HistoryDetailActivity.class);
////                context.startActivity(intent);
//                System.out.println("Open bill for that date");
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return accList.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        final View mView;
//        final TextView txtItemDate;
//        final TextView txtRequester;
//        final TextView txtAmount;
//        final ImageView star1;
//        final ImageView star2;
//        final ImageView star3;
//        final ImageView star4;
//        final ImageView star5;
//
//        public HistoryItem mItem;
//
//        public ViewHolder(View view) {
//            super(view);
//            mView = view;
//            txtItemDate = (TextView) view.findViewById(R.id.txtItemDate);
//            txtRequester = (TextView) view.findViewById(R.id.txtItemRequester);
//            txtAmount = (TextView) view.findViewById(R.id.txtAmount);
//            star1 = (ImageView) view.findViewById(R.id.start1);
//            star2 = (ImageView) view.findViewById(R.id.start2);
//            star3 = (ImageView) view.findViewById(R.id.start3);
//            star4 = (ImageView) view.findViewById(R.id.start4);
//            star5 = (ImageView) view.findViewById(R.id.start5);
//        }
//
//        @Override
//        public String toString() {
//            return super.toString() + " '" + txtItemDate.getText() + "'";
//        }
//    }
//
//    public void setRating(final ViewHolder holder,int  numberOfStart){
//        if(numberOfStart == 1){
//            holder.star1.setVisibility(View.VISIBLE);
//        }
//        if(numberOfStart == 2){
//            holder.star1.setVisibility(View.VISIBLE);
//            holder.star2.setVisibility(View.VISIBLE);
//        }
//        if(numberOfStart == 3){
//            holder.star1.setVisibility(View.VISIBLE);
//            holder.star2.setVisibility(View.VISIBLE);
//            holder.star3.setVisibility(View.VISIBLE);
//        }
//        if(numberOfStart == 4){
//            holder.star1.setVisibility(View.VISIBLE);
//            holder.star2.setVisibility(View.VISIBLE);
//            holder.star3.setVisibility(View.VISIBLE);
//            holder.star4.setVisibility(View.VISIBLE);
//        }
//        if(numberOfStart == 5){
//            holder.star1.setVisibility(View.VISIBLE);
//            holder.star2.setVisibility(View.VISIBLE);
//            holder.star3.setVisibility(View.VISIBLE);
//            holder.star4.setVisibility(View.VISIBLE);
//            holder.star5.setVisibility(View.VISIBLE);
//
//        }
//    }
//}
