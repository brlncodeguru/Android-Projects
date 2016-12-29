package com.example.welcome.baccalculator;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class BACCalc extends AppCompatActivity {
int weight=0,drinkSize=1;
    String gender;
    String statusMessage;
    Double bacLevel;


   static ArrayList<Drink> arrLstDrinks=new ArrayList<Drink>();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baccalc);





        final EditText txtFieldWeight=(EditText)findViewById(R.id.editTextWeight);
        final Switch swGender=(Switch)findViewById(R.id.switchGender);
        final Button btnSave=(Button)findViewById(R.id.buttonSave);
        final Button btnAddDrink=(Button)findViewById(R.id.buttonAdddrink);
        Button btnReset=(Button)findViewById(R.id.buttonReset);
        final RadioGroup rdgrpDrink=(RadioGroup)findViewById(R.id.radioGroupDrinkSize);
        final RadioButton rdBtn1oz=(RadioButton)findViewById(R.id.radioButton1Oz);
        final RadioButton rdBtn5oz=(RadioButton)findViewById(R.id.radioButton5oz);
        final RadioButton rdBtn12oz=(RadioButton)findViewById(R.id.radioButton12oz);
        final SeekBar skBar=(SeekBar)findViewById(R.id.seekBarAlcohol);
        final TextView txtStatus= (TextView)findViewById(R.id.textViewStatusValue);


        /* Progress bar */
        final ProgressBar pgBar=(ProgressBar)findViewById(R.id.progressBarStatus);
        final TextView textVwBACvalue=(TextView)findViewById(R.id.textViewBACLevelValue);


        final TextView txtVwAlcoholPerCentValue=(TextView)findViewById(R.id.textViewAlcoholValue);
        String progressValue=String.valueOf(skBar.getProgress()*5)+"%";
        txtVwAlcoholPerCentValue.setText(progressValue);
        /*RadioGroup */
        rdgrpDrink.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==rdBtn1oz.getId())
                {
                    drinkSize=1;
                }
                else if(checkedId==rdBtn5oz.getId())
                {
                    drinkSize=5;

                }
                else if(checkedId==rdBtn12oz.getId())
                {
                    drinkSize=12;

                }

            }
        });

        /* Buttons */
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               try{
                   String strWeight = txtFieldWeight.getText().toString();

                   if(TextUtils.isEmpty(strWeight))
                   {

                       txtFieldWeight.setError("Enter the Weight in lbs");



                   }
                   else
                   {
                       txtFieldWeight.setError(null);

                       weight=Integer.parseInt(txtFieldWeight.getText().toString());
                       if(swGender.isChecked())
                       {
                           gender="M";
                       }
                       else
                       {
                           gender="F";
                       }
                       if(!arrLstDrinks.isEmpty())
                       {
                           bacLevel=calculateBACLevel();
                           if(bacLevel>=0.25)
                           {
                               bacLevel=0.25;

                               rdgrpDrink.setEnabled(false);
                               btnSave.setEnabled(false);
                               btnAddDrink.setEnabled(false);
                               rdBtn1oz.setEnabled(false);
                               rdBtn5oz.setEnabled(false);
                               rdBtn12oz.setEnabled(false);
                               skBar.setEnabled(false);
                               Context context = getApplicationContext();
                               CharSequence text = "No More Drinks For You!";
                               int duration = Toast.LENGTH_SHORT;

                               Toast toast = Toast.makeText(context, text, duration);
                               toast.show();
                               statusMessage="Over the limit!";



                           }
                           else
                           {

                               if(bacLevel<=0.08)
                               {
                                   statusMessage="You're Safe.";

                                   txtStatus.setBackgroundResource(R.drawable.rounded_corner_safe);
                               }
                               else if(bacLevel>0.08 && bacLevel<0.20)
                               {
                                   statusMessage="Be Careful..";
                                   txtStatus.setBackgroundResource(R.drawable.rounded_corner_careful);
                               }
                               else if(bacLevel>=0.20)
                               {
                                   statusMessage="Over the limit!";
                                   txtStatus.setBackgroundResource(R.drawable.rounded_corner_safe);
                               }
                           }
                           textVwBACvalue.setText(bacLevel.toString());
                           Double progressDouble=bacLevel*100;
                           int progressValue=progressDouble.intValue();
                           pgBar.setProgress(progressValue);
                           txtStatus.setText(statusMessage);
                   }

                }
               } catch(Exception e){
                   Log.d("BAC Calculator",e.getMessage());
               }
            }
        });

         btnAddDrink.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 try{
                     if(weight==0&&TextUtils.isEmpty(txtFieldWeight.getText().toString()))
                     {
//                Context context = getApplicationContext();
//                CharSequence text = "Enter the Weight in lbs";
//                int duration = Toast.LENGTH_SHORT;
//
//                Toast toast = Toast.makeText(context, text, duration);
//                toast.show();

                         txtFieldWeight.setError("Enter the Weight in lbs");



                     }
                     else if(Integer.parseInt(txtFieldWeight.getText().toString())>=0&& weight==0)
                     {
                         txtFieldWeight.setError("Save the weight");

                     }
                     else
                     {
                         txtFieldWeight.setError(null);
                         Drink drnkObj=new Drink(drinkSize,skBar.getProgress()*5);
                         arrLstDrinks.add(drnkObj);
                         bacLevel=calculateBACLevel();
                         if(bacLevel>=0.25)
                         {
                             bacLevel=0.25;

                             rdgrpDrink.setEnabled(false);
                             btnSave.setEnabled(false);
                             btnAddDrink.setEnabled(false);
                             rdBtn1oz.setEnabled(false);
                             rdBtn5oz.setEnabled(false);
                             rdBtn12oz.setEnabled(false);
                             skBar.setEnabled(false);
                             Context context = getApplicationContext();
                             CharSequence text = "No More Drinks For You!";
                             int duration = Toast.LENGTH_SHORT;

                             Toast toast = Toast.makeText(context, text, duration);
                             toast.show();
                             statusMessage="Over the limit!";



                         }
                         else
                         {
                             if(bacLevel<=0.08)
                             {
                                 statusMessage="You're Safe.";
                                 txtStatus.setBackgroundResource(R.drawable.rounded_corner_safe);

                             }
                             else if(bacLevel>0.08 && bacLevel<0.20)
                             {
                                 statusMessage="Be Careful..";
                                 txtStatus.setBackgroundResource(R.drawable.rounded_corner_careful);

                             }
                             else if(bacLevel>=0.20)
                             {
                                 statusMessage="Over the limit!";
                                 txtStatus.setBackgroundResource(R.drawable.rounded_corner_safe);

                             }
                         }
                         textVwBACvalue.setText(bacLevel.toString());
                         Double progressDouble=bacLevel*100;
                         int progressValue=progressDouble.intValue();
                         pgBar.setProgress(progressValue);
                         txtStatus.setText(statusMessage);


                     }
                 }
                 catch (Exception e)
                 {
                     Log.d("BAC Calculator",e.getMessage());
                 }


             }
         });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                rdgrpDrink.setEnabled(true);
                btnSave.setEnabled(true);
                btnAddDrink.setEnabled(true);
                    swGender.setChecked(false);
                rdBtn1oz.setChecked(true);
                skBar.setProgress(1);
                    weight=0;
                gender="";
                drinkSize=1;
                bacLevel=0.00 ;
                Double BACLevelValue=bacLevel*100;
                int progressVal=BACLevelValue.intValue();
                pgBar.setProgress(progressVal);
                txtStatus.setText("");
                textVwBACvalue.setText(bacLevel.toString());
                arrLstDrinks.clear();
                txtFieldWeight.setText("");
                rdBtn1oz.setEnabled(true);
                rdBtn5oz.setEnabled(true);
                rdBtn12oz.setEnabled(true);
                skBar.setEnabled(true);


                } catch(Exception e){
                    Log.d("BAC Calculator",e.getMessage());
                }
                        

            }
        });


        /* SeekBar */
        skBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                try {
                String progressValue=String.valueOf(progress*5)+"%";
                txtVwAlcoholPerCentValue.setText(progressValue);
                } catch(Exception e){
                    Log.d("BAC Calculator",e.getMessage());

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public Double calculateBACLevel()
    {
        Double bacLevelValue=0.00;
        Double r;
        if(gender=="M")
        {
            r=0.68;
        }
        else
        {
            r=0.55;
        }
        for(Drink d : arrLstDrinks)
        {

            bacLevelValue=bacLevelValue+(((d.drinkSize *d.alcoholPercent)*6.24/(weight*r))/100);

        }
        bacLevelValue= Math.round(bacLevelValue*100)/100D;
        return bacLevelValue;

    }
}
