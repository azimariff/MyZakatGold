package com.example.mynewzakatgold;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    Button btnCalculate, btnReset;
    EditText goldW, goldV;
    RadioGroup radioGroup;
    RadioButton radioButton;
    TextView tvOutput1, tvOutput2, tvOutput3;
    float gw, gv;

    SharedPreferences sharedPref;
    SharedPreferences sharedPref2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = findViewById(R.id.radioGroup);
        goldV = (EditText) findViewById(R.id.goldV);
        goldW = (EditText) findViewById(R.id.goldW);
        btnCalculate = (Button) findViewById(R.id.btnCalculate);
        btnReset = (Button) findViewById(R.id.btnReset);
        tvOutput1 = findViewById(R.id.tvOutput1);
        tvOutput2 = findViewById(R.id.tvOutput2);
        tvOutput3 = findViewById(R.id.tvOutput3);
        btnCalculate.setOnClickListener(this);
        btnReset.setOnClickListener(this);

        sharedPref = this.getSharedPreferences("weight", Context.MODE_PRIVATE);
        gw = sharedPref.getFloat("weight", 0.0F);

        sharedPref2 = this.getSharedPreferences("value", Context.MODE_PRIVATE);
        gv = sharedPref2.getFloat("value", 0.0F);

        goldW.setText(""+gw);
        goldV.setText(""+gv);

    }
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate (R.menu.menu, menu);

        return true;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void checkButton(View v) {
        int radioId = radioGroup.getCheckedRadioButtonId();

        radioButton = findViewById(radioId);
        Toast.makeText(this, "Type of Gold : " + radioButton.getText(), Toast.LENGTH_SHORT).show();
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.Homepage:

                Intent intent1 = new Intent(this, Homepage.class);
                startActivity(intent1);
                break;

            case R.id.About:

                Intent intent2 = new Intent(this, AboutActivity.class);
                startActivity(intent2);
                break;

            case R.id.Calculator:
                Intent intent3 = new Intent(this, MainActivity.class);
                startActivity(intent3);
                break;

        }
        return super.onOptionsItemSelected(item);

    }
    @Override
    public void onClick(View v) {
        int radioId = radioGroup.getCheckedRadioButtonId();

        radioButton = findViewById(radioId);
        try {
            String output = radioButton.getText().toString();
        }catch (java.lang.NullPointerException npe){
            Toast toast = Toast.makeText(this,"Please select type of gold", Toast.LENGTH_SHORT);
            toast.show();
        }


        try {
            switch (v.getId()) {

                case R.id.btnCalculate:
                    calc();
                    break;

                case R.id.btnReset:
                    goldW.setText("");
                    goldV.setText("");
                    tvOutput1.setText("");
                    tvOutput2.setText("");
                    tvOutput3.setText("");
                    radioGroup.clearCheck();

                    break;

            }
        } catch (java.lang.NumberFormatException nfe) {
            Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show();

        } catch (Exception exp) {
            Toast.makeText(this, "Unknown Exception" + exp.getMessage(), Toast.LENGTH_SHORT).show();

            Log.d("Exception", exp.getMessage());

        }
    }


    private void calc() {

        DecimalFormat df = new DecimalFormat("##.00");
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        float gw = Float.parseFloat(goldW.getText().toString());
        float gv = Float.parseFloat(goldV.getText().toString());
        double totGoldValue = 0, uruf = 0, zakatPay = 0, totZakat = 0;

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putFloat("weight", gw);
        editor.apply();
        SharedPreferences.Editor editor2 = sharedPref2.edit();
        editor2.putFloat("value", gv);
        editor2.apply();

        if (radioButton.getText().toString().equals("keep")) {
            totGoldValue = gw * gv;
            uruf = gw - 85;

            if (uruf >= 0.0) {
                zakatPay = uruf * gv;
                totZakat = zakatPay * 0.025;
            } else
                zakatPay = 0.0;
                totZakat = zakatPay * 0.025;
                tvOutput1.setText("Value of the gold: RM " + df.format(totGoldValue));
                tvOutput2.setText("Zakat Payable: RM " + zakatPay);
                tvOutput3.setText("Total zakat: RM " + totZakat);
        }else{
            totGoldValue = gw * gv;
            uruf = gw - 200;
            if (uruf >= 0.0) {
                zakatPay = uruf * gv;
                totZakat = zakatPay * 0.025;
            } else {
                zakatPay = 0.0;
                totZakat = zakatPay * 0.025;
            }
            tvOutput1.setText("Value of the gold: RM " + totGoldValue);
            tvOutput2.setText("Zakat Payable: RM " + zakatPay);
            tvOutput3.setText("Total zakat: RM " + totZakat);
        }
    }
}