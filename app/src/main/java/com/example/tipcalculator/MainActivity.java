package com.example.tipcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    public RadioButton five;
    public RadioButton ten;
    public RadioButton fifthteen;
    public TextView total;
    public TextView Cost;
    public HashMap<String,Float> sharedValues;
    Toast warning;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        five = (RadioButton)findViewById(R.id.fivePercent);
        ten = (RadioButton)findViewById(R.id.tenPercent);
        fifthteen=(RadioButton)findViewById(R.id.fifthteenPercent);
        total=(TextView)findViewById(R.id.TotalAmount);
        Cost = (TextView)findViewById(R.id.cost);
        //sharedValues= new HashMap<String,Float>();
        warning = new Toast(getApplicationContext());
        selectFive();
        selectTen();
        selectFifthteen();


    }


    public void selectFive(){

        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( !Cost.getText().toString().equals("")) {
                    //Untoggle other buttons
                    ten.setChecked(false);
                    fifthteen.setChecked(false);

                    //Calculate
                    String coststr = Cost.getText().toString();
                    float cost = Float.parseFloat(coststr);
                    float tip = cost * 0.05f;
                    DecimalFormat roundedValue = new DecimalFormat("##.00");
                    float totalCost = cost + Float.parseFloat(roundedValue.format(tip));



                    total.setText("Tip: " + String.valueOf(roundedValue.format(tip)) + " Total:" + roundedValue.format(totalCost));
                }else{
                    warning.cancel();

                    five.setChecked(false);
                    ten.setChecked(false);
                    fifthteen.setChecked(false);

                    total.setText("");

                    String msg= "Please enter the cost";
                    
                    warning=warning.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT);
                    warning.show();
                }
            }
        });



    }

    public void selectTen(){


        ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( !Cost.getText().toString().equals("")) {
                five.setChecked(false);
                fifthteen.setChecked(false);

                String coststr=Cost.getText().toString();
                float cost = Float.parseFloat(coststr);
                float tip = cost *0.10f;
                DecimalFormat roundedValue = new DecimalFormat("##.00");
                float totalCost = cost + Float.parseFloat(roundedValue.format(tip));

                total.setText("Tip: "+String.valueOf(roundedValue.format(tip))+" Total:"+ roundedValue.format(totalCost));

                }else{
                    warning.cancel();

                    five.setChecked(false);
                    ten.setChecked(false);
                    fifthteen.setChecked(false);

                    total.setText("");

                    String msg= "Please enter the cost";

                    warning=warning.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT);
                    warning.show();
                }
            }
        });



    }

    public void selectFifthteen(){


        fifthteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( !Cost.getText().toString().equals("")) {
                five.setChecked(false);
                ten.setChecked(false);


                String coststr=Cost.getText().toString();
                float cost = Float.parseFloat(coststr);
                float tip = cost *0.15f;
                DecimalFormat roundedValue = new DecimalFormat("##.00");
                float totalCost = cost + Float.parseFloat(roundedValue.format(tip));

                total.setText("Tip: "+String.valueOf(roundedValue.format(tip))+" Total:"+ roundedValue.format(totalCost));
                }else{
                    warning.cancel();

                    five.setChecked(false);
                    ten.setChecked(false);
                    fifthteen.setChecked(false);

                    total.setText("");

                    String msg= "Please enter the cost";
                    warning=warning.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT);
                    warning.show();
                }
            }
        });



    }
}