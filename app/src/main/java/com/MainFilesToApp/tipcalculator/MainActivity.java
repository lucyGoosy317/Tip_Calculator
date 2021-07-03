package com.MainFilesToApp.tipcalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.slider.Slider;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    public RadioButton five;
    public RadioButton ten;
    public RadioButton fifthteen;
    public TextView total;
    public TextView Cost;
    public Slider CustomTipSlider;

    public HashMap<String,Float> sharedValues;
    Toast warning;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Controller values
        five = (RadioButton)findViewById(R.id.fivePercent);
        ten = (RadioButton)findViewById(R.id.tenPercent);
        fifthteen=(RadioButton)findViewById(R.id.fifthteenPercent);
        total=(TextView)findViewById(R.id.TotalAmount);
        Cost = (TextView)findViewById(R.id.cost);
        CustomTipSlider=(Slider)findViewById(R.id.customTipSlider);


        //sharedValues= new HashMap<String,Float>();
        warning = new Toast(getApplicationContext());
        selectFive();
        selectTen();
        selectFifthteen();
        customTipSlider();


    }


    public void selectFive(){

        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( !Cost.getText().toString().equals("")) {
                    //Untoggle other buttons
                    setAllButtons(true,false,false);

                    total.setText(performCalculations(0.05f,0));
                }else{
                    warning.cancel();
                    setAllButtons(false,false,false);


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

                    setAllButtons(false,true,false);

                    total.setText(performCalculations(0.10f,0));
                }else{
                    warning.cancel();
                    setAllButtons(false,false,false);


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

                setAllButtons(false,false,true);

                 total.setText(performCalculations(0.15f,0));
                }else{
                    produceWaring();
                    //warning.cancel();
                    //setAllButtons(false,false,false);


                    //total.setText("");

                    //String msg= "Please enter the cost";
                    //warning=warning.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT);
                    //warning.show();
                }
            }
        });



    }

    /*
     ******Error upon empty Cost field********
     */
    public void customTipSlider(){
        //Custom Label to change add USD code to value
        CustomTipSlider.setLabelFormatter(value ->  {
            //Formatter object
            NumberFormat format = NumberFormat.getCurrencyInstance();
            format.setMaximumFractionDigits(0);
            //Establish currency code to formatter
            format.setCurrency(Currency.getInstance("USD"));
            //Convert format object with instance value, merge them
            String CovertedLabel =format.format(value);
            //Return the converted label to be displayed
            return CovertedLabel;
        });

        //Slider Action Start and stop values
        CustomTipSlider.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(Slider slider) {
               // System.out.println("slider Value Start:"+slider.getValue());

                    setAllButtons(false, false, false);

            }

            @Override
            public void onStopTrackingTouch(Slider slider) {
                System.out.println("slider Value Stop:"+slider.getValue());
            }
        });

        //Slider Action Observe changes
        CustomTipSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(Slider slider, float value, boolean fromUser) {
               // if(Cost.getText()!="") {
                    System.out.println("Changing the value Continous use: " + value);
                    total.setText(performCalculations(0.0f, value));
              //  }else{
                //   produceWaring();
               // }
            }
        });




    }

    public String performCalculations(Float percent, float value){
        String ConvertedTipAmount="";

        if (percent == 0.0f ){
            String coststr=Cost.getText().toString();
            float cost = Float.parseFloat(coststr);
            DecimalFormat roundedValue = new DecimalFormat("##.00");
            float totalCost= cost+Float.parseFloat(roundedValue.format(value));

            ConvertedTipAmount="Tip: "+String.valueOf((int) value)+" Total:"+ roundedValue.format(totalCost);

        }else{
            String coststr=Cost.getText().toString();
            float cost = Float.parseFloat(coststr);
            float tip = cost *percent;
            DecimalFormat roundedValue = new DecimalFormat("##.00");
            float totalCost = cost + Float.parseFloat(roundedValue.format(tip));

            ConvertedTipAmount="Tip: "+String.valueOf(roundedValue.format(tip))+" Total:"+ roundedValue.format(totalCost);
        }

        return ConvertedTipAmount;
    }


    public void setAllButtons(Boolean fiveSetting,Boolean tenSetting,Boolean fifthteenSetting){
        five.setChecked(fiveSetting);
        ten.setChecked(tenSetting);
        fifthteen.setChecked(fifthteenSetting);
    }


    public void produceWaring(){
        warning.cancel();
        setAllButtons(false,false,false);


        total.setText("");

        String msg= "Please enter the cost";
        warning=warning.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT);
        warning.show();
    }
}