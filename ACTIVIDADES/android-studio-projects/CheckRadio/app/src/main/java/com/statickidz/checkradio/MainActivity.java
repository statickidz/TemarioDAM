package com.statickidz.checkradio;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements CheckBox.OnCheckedChangeListener {

    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkBox = (CheckBox)findViewById(R.id.aceptado);
        checkBox.setOnCheckedChangeListener(this);

    }

    public void onRadioButtonClicked(View view) {
        switch (view.getId()) {
            case R.id.radio_paypal:
                showPaymentDialog(getString(R.string.paypal));
                break;
            case R.id.radio_tarjeta:
                showPaymentDialog(getString(R.string.credit_card));
                break;
        }
    }

    private void showPaymentDialog(String method) {
        final AlertDialog.Builder inputAlert = new AlertDialog.Builder(this);
        inputAlert.setTitle(method);
        if(method.equals(getString(R.string.paypal))) {
            inputAlert.setMessage(getString(R.string.paypal_msg));
        } else {
            inputAlert.setMessage(getString(R.string.credit_card_msg));
        }
        final EditText userInput = new EditText(this);
        inputAlert.setView(userInput);
        inputAlert.setPositiveButton(getString(R.string.sell), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String userInputValue = userInput.getText().toString();
                sellMySoul(userInputValue);
            }
        });
        AlertDialog alertDialog = inputAlert.create();
        alertDialog.show();
    }

    private void sellMySoul(String userInputValue) {
        RadioGroup radioPayment = (RadioGroup) findViewById(R.id.radio_payment);
        radioPayment.setVisibility(View.GONE);

        //Success
        TextView successMsgText = (TextView) findViewById(R.id.success_msg);
        successMsgText.setText(successMsgText.getText() + userInputValue);
        LinearLayout successLayout = (LinearLayout) findViewById(R.id.success_layout);
        successLayout.setVisibility(View.VISIBLE);
    }

    public void startAgain(View view) {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        TextView txt = (TextView)findViewById(R.id.texto);
        ImageView ver =(ImageView)findViewById(R.id.imagen);
        ProgressBar bar =(ProgressBar)findViewById(R.id.progressBar);
        if(isChecked){
            txt.setText(getString(R.string.sell_to_hell));
            ver.setVisibility(View.VISIBLE);
            bar.setVisibility(View.VISIBLE);
            yoAsesto();
        } else {
            txt.setText(getString(R.string.no_sell));
            ver.setVisibility(View.GONE);
        }
    }

    private void yoAsesto() {

        checkBox.setEnabled(false);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                RadioGroup radioPayment = (RadioGroup) findViewById(R.id.radio_payment);
                radioPayment.setVisibility(View.VISIBLE);

                LinearLayout startLayout = (LinearLayout) findViewById(R.id.start_layout);
                startLayout.setVisibility(View.GONE);
            }
        }, 4000);
    }
}
