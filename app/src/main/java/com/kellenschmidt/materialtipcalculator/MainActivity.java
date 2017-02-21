package com.kellenschmidt.materialtipcalculator;

import android.app.Activity;
import android.content.Context;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;
import java.text.NumberFormat;


public class MainActivity extends Activity {

    TextView subtotalTextView, tipInputTextView, percentNumberTextView, peopleTextView,
            totalCalculationTextView, totalTextView, tipTextView, tipCalculationTextView, dollarTextView,
            dolarTextView;
    EditText subtotalEditText, peopleEditText;
    SeekBar tipSeekBar;
    Button calculateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        subtotalTextView = (TextView) findViewById(R.id.subtotalTextView);
        tipInputTextView = (TextView) findViewById(R.id.tipInputTextView);
        percentNumberTextView = (TextView) findViewById(R.id.percentNumberTextView);
        peopleTextView = (TextView) findViewById(R.id.peopleTextView);
        totalCalculationTextView = (TextView) findViewById(R.id.totalCalculationTextView);
        totalTextView = (TextView) findViewById(R.id.totalTextView);
        tipTextView = (TextView) findViewById(R.id.tipTextView);
        tipCalculationTextView = (TextView) findViewById(R.id.tipCalculationTextView);
        dollarTextView = (TextView) findViewById(R.id.dollarTextView);
        dolarTextView = (TextView) findViewById(R.id.dolarTextView);
        subtotalEditText = (EditText) findViewById(R.id.subtotalEditText);
        peopleEditText = (EditText) findViewById(R.id.peopleEditText);
        tipSeekBar = (SeekBar) findViewById(R.id.tipSeekBar);
        calculateButton = (Button) findViewById(R.id.calculateButton);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(subtotalEditText.getText().toString().equals("")) {
                    hideKeyboard(subtotalEditText);
                    Toast.makeText(MainActivity.this, "Enter a subtotal value", Toast.LENGTH_SHORT).show();
                } else if (peopleEditText.getText().toString().equals("")) {
                    hideKeyboard(peopleEditText);
                    Toast.makeText(MainActivity.this, "Enter a number of people", Toast.LENGTH_SHORT).show();
                } else {
                    displayCalcs();
                    hideKeyboard(peopleEditText);
                }
            }
        });

        tipSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                percentNumberTextView.setText(progress+"%");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    public double doTipCalculation(EditText subtotal, TextView percent, EditText people) {
        return ((editToNumber(subtotal)*(textToNumber(percent)/100))/editToNumber(people));
    }
    public double doTotalCalculation(EditText subtotal, TextView percent, EditText people) {
        return (editToNumber(subtotal)+(((editToNumber(subtotal)*(textToNumber(percent)/100))/editToNumber(people))*editToNumber(people)));
    }
    public double textToNumber(TextView percent) {
        String percentString = percent.getText().toString();
        return (double) Integer.parseInt(percentString.substring(0,percentString.length()-1));
    }
    public double editToNumber(EditText text) {
        return Double.parseDouble(text.getText().toString());
    }

    public void displayCalcs() {
        tipCalculationTextView.setText(NumberFormat.getCurrencyInstance().format(
                doTipCalculation(subtotalEditText, percentNumberTextView, peopleEditText)));
        totalCalculationTextView.setText(NumberFormat.getCurrencyInstance().format(
                doTotalCalculation(subtotalEditText, percentNumberTextView, peopleEditText)));
    }
    public void hideKeyboard(EditText myEditText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(myEditText.getWindowToken(), 0);
    }
}
