package com.statickidz.calculadoraprimos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.statickidz.calculadoraprimos.lib.PrimeNumberGenerator;

import org.w3c.dom.Text;

public class CalculatorActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView inputTxt;
    private TextView responseTxt;
    private Button calculateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        //Define components
        inputTxt = (TextView)findViewById(R.id.inputTxt);
        responseTxt = (TextView)findViewById(R.id.responseTxt);
        calculateBtn = (Button)findViewById(R.id.calculateBtn);

        calculateBtn.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calculator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.calculateBtn) {
            calculatePositionFromNumber();
        }

    }

    public void calculatePositionFromNumber() {
        int inputNumber = Integer.parseInt(String.valueOf(inputTxt.getText()));

        PrimeNumberGenerator primeGenerator = new PrimeNumberGenerator(0, inputNumber*10);
        int primeNumber = primeGenerator.getNumberFromPosition(inputNumber);

        responseTxt.setText("El primo en la posición " + inputNumber + " es el " + primeNumber);
    }



}
