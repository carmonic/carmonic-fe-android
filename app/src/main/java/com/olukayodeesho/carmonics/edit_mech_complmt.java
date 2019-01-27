package com.olukayodeesho.carmonics;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

public class edit_mech_complmt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mech_complmt);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    public void onclick_close_edit_mech_complmt(View view) {
        Intent i = new Intent(getApplicationContext(), billing_main.class);
        startActivity(i);
    }


    public void ComplimentImageSelector(ComplimentImageState complimentImageState) {
        ImageButton btn1a = findViewById(R.id.comImageIa);
        btn1a.setVisibility(View.VISIBLE);
        TextView txtVw1a = findViewById(R.id.comTxt1a);
        txtVw1a.setVisibility(View.VISIBLE);
        TextView txtVw1b = findViewById(R.id.comTxt1b);
        txtVw1b.setVisibility(View.INVISIBLE);

        ImageButton btn2a = findViewById(R.id.comImg2a);
        btn2a.setVisibility(View.VISIBLE);
        TextView txtVw2a = findViewById(R.id.comTxv2a);
        txtVw2a.setVisibility(View.VISIBLE);
        TextView txtVw2b = findViewById(R.id.comTxt2b);
        txtVw2b.setVisibility(View.INVISIBLE);


        switch (complimentImageState) {
            case One:
                txtVw1a.setVisibility(View.VISIBLE);
                btn1a.setVisibility(View.VISIBLE);

                txtVw1b.setVisibility(View.INVISIBLE);

                break;
            case OneYellow:
                txtVw1a.setVisibility(View.INVISIBLE);
                btn1a.setVisibility(View.INVISIBLE);

                txtVw1b.setVisibility(View.VISIBLE);
                break;
            case Two:
                txtVw2a.setVisibility(View.VISIBLE);
                btn2a.setVisibility(View.VISIBLE);

                txtVw2b.setVisibility(View.INVISIBLE);
                break;
            case TwoYellow:
                txtVw2a.setVisibility(View.INVISIBLE);
                btn2a.setVisibility(View.INVISIBLE);

                txtVw2b.setVisibility(View.VISIBLE);
                break;

            case Three:
                break;
            case ThreeYellow:
                break;

            case Four:
                break;
            case FourYellow:
                break;

            case Five:
                break;
            case FiveYellow:
                break;
            default:

                break;
        }

    }

    public void OnClick_Com1b(View view) {

        ComplimentImageSelector(ComplimentImageState.One);
    }


    public void OnClick_Com1aImg(View view) {
        ComplimentImageSelector(ComplimentImageState.OneYellow);
    }

    public void OnClick_Com1aTxv(View view) {

        ComplimentImageSelector(ComplimentImageState.OneYellow);
    }

    public void OnClick_Com2aImg(View view) {
        ComplimentImageSelector(ComplimentImageState.TwoYellow);
    }

    public void OnClick_Com2aTxv(View view) {

        ComplimentImageSelector(ComplimentImageState.TwoYellow);
    }

    public void OnClick_Com2b(View view) {

        ComplimentImageSelector(ComplimentImageState.Two);
    }


    enum ComplimentImageState {
        One,
        OneYellow,
        Two,
        TwoYellow,
        Three,
        ThreeYellow,
        Four,
        FourYellow,
        Five,
        FiveYellow

    }


}
