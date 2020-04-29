package com.camsys.carmonic.History;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.camsys.carmonic.R;
import com.camsys.carmonic.adapters.HistoryFragmentAdapter;
import com.camsys.carmonic.constants.Constants;
import com.camsys.carmonic.model.HistoryItem;
import com.camsys.carmonic.model.HistoryModel;
import com.camsys.carmonic.model.Mechanic;
import com.camsys.carmonic.model.Result;
import com.camsys.carmonic.model.User;
import com.camsys.carmonic.networking.BackEndDAO;
import com.camsys.carmonic.utilities.SharedData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HistoryActivity extends AppCompatActivity {


    private OnListFragmentInteractionListener mListener;
    Context mContext =  null;
    private Toolbar toolbar = null;

    SharedData sharedData = null;
    Gson gson = null;
    User user = null;

    LinearLayout wait_icon = null;
    RecyclerView recyclerView = null;
    LinearLayout noAvailableHistory = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedData = new SharedData(getApplicationContext());
        gson = new Gson();
        String userkey = sharedData.Get(Constants.SharedDataCst.USER_KEY, "");
        user = gson.fromJson(userkey, User.class);



        setContentView(R.layout.fragment_history);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        mContext =  HistoryActivity.this;

        TextView txtTitle = toolbar.findViewById(R.id.txtTitle);
        txtTitle.setText("History");

        wait_icon = findViewById(R.id.wait_icon);
        noAvailableHistory = findViewById(R.id.noAvailableHistory);
        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(15);
        recyclerView.setVisibility(View.GONE);
        noAvailableHistory.setVisibility(View.GONE);
        wait_icon.setVisibility(View.VISIBLE);

        AppCompatImageButton button = toolbar.findViewById(R.id.appBackButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        getCustomerHistory(user.getId(), user.getToken());






//        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),R.drawable.transparent);
//        recyclerView.addItemDecoration(dividerItemDecoration);
        // recyclerView.setAdapter(new HistoryFragmentAdapter(list, mListener,mContext));
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(HistoryItem item);
        void onItemClick(View view, int position);
    }


    private void getCustomerHistory(int customerId, String token) {

        //customerPosition.longitude, customerPosition.latitude
        BackEndDAO.getHistoryWithPost(customerId, token, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        noAvailableHistory.setVisibility(View.VISIBLE);
                        wait_icon.setVisibility(View.GONE);
                    }
                });


                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBodyString = response.body().string();
                System.out.println("responseBodyString for History  " + responseBodyString);
                Gson gson = new Gson();
                HistoryModel items = gson.fromJson(responseBodyString, HistoryModel.class);
                System.out.println("history item" + items.getResult().get(0).getId());
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        if (items != null) {

                            if (items.getMessage().equalsIgnoreCase("success")) {

                                noAvailableHistory.setVisibility(View.GONE);
                                wait_icon.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);


                                recyclerView.setAdapter(new HistoryFragmentAdapter(items.getResult(), mListener, mContext));

                            } else {

                            }
                        } else {
                            noAvailableHistory.setVisibility(View.VISIBLE);
                            wait_icon.setVisibility(View.GONE);
                            System.out.println("No History available");
                        }


                    }
                });


            }
        });
    }

}
