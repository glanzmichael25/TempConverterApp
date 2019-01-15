package com.glanz.tempconverter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements TextView.OnEditorActionListener{

    //instance variables
    private EditText inputEditText;
    private TextView outputViewText;
    private String fahrenheitString;
    private SharedPreferences savedValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get references of the widgets from the R class
        inputEditText = findViewById(R.id.editText);
        outputViewText = findViewById(R.id.textViewCelciusConversion);
        inputEditText.setOnEditorActionListener(this);
        //get shared preferences object
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);

    }

    @Override
    public void onPause(){
        //save the instance variables
        SharedPreferences.Editor editor = savedValues.edit();
        editor.putString("fahrenheitString", fahrenheitString);
        editor.commit();
        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();
        //get the instance variables
        fahrenheitString = savedValues.getString("fahrenheitString", "");
        //set fahrenheit on inputEditText
        inputEditText.setText(fahrenheitString);
        //calculate and display
        calculateAndDisplay();
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if(actionId == EditorInfo.IME_ACTION_DONE) {
            calculateAndDisplay();
        }
        return false;
    }

    private void calculateAndDisplay() {
        //get fahrenheit
        fahrenheitString = inputEditText.getText().toString();
        double fahrenheit;
        if (fahrenheitString.equals("")) {
            fahrenheit = 0;
        } else {
            fahrenheit = Double.parseDouble(fahrenheitString);
        }
        //calculate conversion to celcius
        double conversion = 0;
        conversion = (fahrenheit - 32) * 5/9;
        //display the conversion to celcius
        String outputConversion = Double.toString(conversion);
        outputViewText.setText(outputConversion);
    }
}
