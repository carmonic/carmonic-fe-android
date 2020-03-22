package com.camsys.carmonic.payment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.camsys.carmonic.History.HistoryActivity;
import com.camsys.carmonic.R;
import com.camsys.carmonic.adapters.HistoryFragmentAdapter;
import com.camsys.carmonic.adapters.PaymentAdapter;
import com.camsys.carmonic.model.Card;
import com.camsys.carmonic.model.HistoryItem;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {

    private HistoryActivity.OnListFragmentInteractionListener mListener;
    Context mContext = null;
    private Toolbar toolbar = null;

    ImageView imgLogo;
    TextView txtAddNewCard;
    TextView txtAmount;

    LinearLayout addNewCard  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        mContext = PaymentActivity.this;

        TextView txtTitle = (TextView) toolbar.findViewById(R.id.txtTitle);
        txtTitle.setText("Account");


        addNewCard  = (LinearLayout) findViewById(R.id.addNewCard);


        AppCompatImageButton button = (AppCompatImageButton) toolbar.findViewById(R.id.appBackButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        ArrayList<Card> cards =  new ArrayList<>();

        Card card1 = new Card();
        card1.setCardDescription("Card Ending with 9087");
        card1.setCardName("MasterCard");
        card1.setCardType("MasterCard");
        card1.setLastUsed("Last used on 08/02/2018");
        cards.add(card1);


        Card card2 = new Card();
        card2.setCardDescription("Card Ending with 9087");
        card2.setCardName("visa");
        card2.setCardType("visa");
        card2.setLastUsed("Last used on 08/02/2019");
        cards.add(card2);




        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(15);
        recyclerView.setAdapter(new PaymentAdapter(cards,  mContext));

        addNewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 startActivity(new Intent(PaymentActivity.this,AddPayment.class));

            }
        });

    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(HistoryItem item);

        void onItemClick(View view, int position);
    }

}
