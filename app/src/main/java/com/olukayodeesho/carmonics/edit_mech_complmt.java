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


        ImageButton btn3a = findViewById(R.id.comImg3a);
        btn3a.setVisibility(View.VISIBLE);
        TextView txtVw3a = findViewById(R.id.comTxv3a);
        txtVw3a.setVisibility(View.VISIBLE);
        TextView txtVw3b = findViewById(R.id.comTxt3b);
        txtVw3b.setVisibility(View.INVISIBLE);

        ImageButton btn4a = findViewById(R.id.comImg4a);
        btn4a.setVisibility(View.VISIBLE);
        TextView txtVw4a = findViewById(R.id.comTxv4a);
        txtVw4a.setVisibility(View.VISIBLE);
        TextView txtVw4b = findViewById(R.id.comTxt4b);
        txtVw4b.setVisibility(View.INVISIBLE);


        ImageButton btn5a = findViewById(R.id.comImg5a);
        btn5a.setVisibility(View.VISIBLE);
        TextView txtVw5a = findViewById(R.id.comTxv5a);
        txtVw5a.setVisibility(View.VISIBLE);
        TextView txtVw5b = findViewById(R.id.comTxt5b);
        txtVw5b.setVisibility(View.INVISIBLE);


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
                txtVw3a.setVisibility(View.VISIBLE);
                btn3a.setVisibility(View.VISIBLE);

                txtVw3b.setVisibility(View.INVISIBLE);

                break;
            case ThreeYellow:
                txtVw3a.setVisibility(View.INVISIBLE);
                btn3a.setVisibility(View.INVISIBLE);

                txtVw3b.setVisibility(View.VISIBLE);
                break;

            case Four:
                txtVw4a.setVisibility(View.VISIBLE);
                btn4a.setVisibility(View.VISIBLE);

                txtVw4b.setVisibility(View.INVISIBLE);
                break;
            case FourYellow:
                txtVw4a.setVisibility(View.INVISIBLE);
                btn4a.setVisibility(View.INVISIBLE);

                txtVw4b.setVisibility(View.VISIBLE);
                break;

            case Five:
                txtVw5a.setVisibility(View.VISIBLE);
                btn5a.setVisibility(View.VISIBLE);

                txtVw5b.setVisibility(View.INVISIBLE);
                break;
            case FiveYellow:
                txtVw5a.setVisibility(View.INVISIBLE);
                btn5a.setVisibility(View.INVISIBLE);

                txtVw5b.setVisibility(View.VISIBLE);
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

    //segment 3
    public void OnClick_Com3aImg(View view) {
        ComplimentImageSelector(ComplimentImageState.ThreeYellow);
    }

    public void OnClick_Com3aTxv(View view) {

        ComplimentImageSelector(ComplimentImageState.ThreeYellow);
    }

    public void OnClick_Com3b(View view) {

        ComplimentImageSelector(ComplimentImageState.Three);
    }

    //segment 4
    public void OnClick_Com4aImg(View view) {
        ComplimentImageSelector(ComplimentImageState.FourYellow);
    }

    public void OnClick_Com4aTxv(View view) {

        ComplimentImageSelector(ComplimentImageState.FourYellow);
    }

    public void OnClick_Com4b(View view) {

        ComplimentImageSelector(ComplimentImageState.Four);
    }

    //segment 5
    public void OnClick_Com5aImg(View view) {
        ComplimentImageSelector(ComplimentImageState.FiveYellow);
    }

    public void OnClick_Com5aTxv(View view) {

        ComplimentImageSelector(ComplimentImageState.FiveYellow);
    }

    public void OnClick_Com5b(View view) {

        ComplimentImageSelector(ComplimentImageState.Five);
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
