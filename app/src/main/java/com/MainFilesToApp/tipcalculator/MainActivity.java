package com.MainFilesToApp.tipcalculator;
/**
 * Author: Luziano Reyna
 * Application: Tip Calculator
 *
 */


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
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

    public Button spendingButton;

    /**Testing Number Picker
    public NumberPicker PercentNumPicker;
    */

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

        //Testing switching views
        spendingButton=(Button)findViewById(R.id.spendingButton);

        /** Testing number picker
        PercentNumPicker =(NumberPicker)findViewById(R.id.PercentNumPicker);
        PercentNumPicker.setMinValue(0);
        PercentNumPicker.setMaxValue(100);
        */

        //sharedValues= new HashMap<String,Float>();
        warning = new Toast(getApplicationContext());
        selectFive();
        selectTen();
        selectFifthteen();
        customTipSlider();
        switchToSpending();

        /** Testing number picker
        numberPickerSelection();
        */

    }

    public void switchToSpending(){

        spendingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Changing view");
                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                startActivity(intent);

            }
        });

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
                    produceWarning();
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


    public void customTipSlider(){
        //Custom Label to change add USD code to value
        CustomTipSlider.setLabelFormatter(value ->  {
            //Formatter object
            NumberFormat format = NumberFormat.getCurrencyInstance();
            format.setMaximumFractionDigits(0);
            //Establish currency code to formatter
            //format.setCurrency(Currency.getInstance("USD"));
            //Convert format object with instance value, merge them
            int WholeNum = (int) value;
            String CovertedLabel = "%"+WholeNum; //format.format(value);
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
                    float testNum = value / 100;
                    System.out.println("Changing the value Continous use: " + testNum);
                    total.setText(performCalculations(testNum, value));

            }
        });




    }

    /**
     * performCalculations Description
     * @param percent
     * @param value
     * @return
     */
    public String performCalculations(Float percent, float value){
        String ConvertedTipAmount="";
        try{
        if(Cost.getText() ==""){
            System.out.println("Umm its empty");
            ConvertedTipAmount = "Empty";
            return ConvertedTipAmount;
        }
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

        }catch(NumberFormatException ex){
            System.out.println(ex);
        }
        return ConvertedTipAmount;
    }


    public void setAllButtons(Boolean fiveSetting,Boolean tenSetting,Boolean fifthteenSetting){
        five.setChecked(fiveSetting);
        ten.setChecked(tenSetting);
        fifthteen.setChecked(fifthteenSetting);
    }

    /**
     * produceWarning
     */
    public void produceWarning(){
        warning.cancel();
        setAllButtons(false,false,false);
        total.setText("");
        String msg= "Please enter the cost";
        warning=warning.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT);
        warning.show();
    }


    //Retriving Num Picker values
    /** Choose not use number picker as on a phone the slider would be more efficent and less annoying, the NumberPicker would be better on a smaller device
     * that cant afford the same space as a phone would ie watch
    public void numberPickerSelection(){
        PercentNumPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                int pickedValue = picker.getValue();
                System.out.println("Selected Value: "+pickedValue);
            }
        });
    }
    */

}