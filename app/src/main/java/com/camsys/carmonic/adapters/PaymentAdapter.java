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
import com.camsys.carmonic.model.Card;
import com.camsys.carmonic.model.HistoryItem;
import com.camsys.carmonic.payment.PaymentActivity;

import java.util.ArrayList;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {

    private final ArrayList<Card> accList;
    //private final HistoryActivity.OnListFragmentInteractionListener mListener;

    Context context;

    public PaymentAdapter(ArrayList<Card> accList, Context context) {
        this.accList = accList;

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.payment_card_layout, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.txtRequester.setText(accList.get(position).getCardDescription());
        holder.txtAmount.setText(accList.get(position).getLastUsed());
        if(accList.get(position).getCardType().equalsIgnoreCase("MasterCard")) {
            holder.cardLogo.setImageResource(R.drawable.mastercard);
        }else if( accList.get(position).getCardType().equalsIgnoreCase("visa")){
            holder.cardLogo.setImageResource(R.drawable.visa);
        }else{
            holder.cardLogo.setImageResource(android.R.drawable.btn_plus);
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HistoryDetailActivity.class);
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

        final TextView txtRequester;
        final TextView txtAmount;
        final ImageView cardLogo;


        public HistoryItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            txtRequester = (TextView) view.findViewById(R.id.txtItemRequester);
            txtAmount = (TextView) view.findViewById(R.id.txtAmount);
            cardLogo = (ImageView) view.findViewById(R.id.item_picture);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + txtRequester.getText() + "'";
        }
    }


}
