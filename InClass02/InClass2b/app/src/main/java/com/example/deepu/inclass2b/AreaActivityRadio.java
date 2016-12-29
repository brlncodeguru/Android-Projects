package com.example.deepu.inclass2b;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class AreaActivityRadio extends AppCompatActivity {

    int Length1,Length2;
    String Result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_activity_radio);

        Button calculate=(Button)findViewById(R.id.btncalculate);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txtLength1=(EditText)findViewById(R.id.textFieldLength1);
                EditText txtLength2=(EditText)findViewById(R.id.textFieldLength2);
                RadioButton rbTriangle=(RadioButton)findViewById(R.id.radioTriangle);
                RadioButton rbSquare=(RadioButton)findViewById(R.id.radioSquare);
                RadioButton rbCircle=(RadioButton)findViewById(R.id.radioCircle);
                RadioButton rbRectangle=(RadioButton)findViewById(R.id.radioRectangle);
                RadioButton rbClearAll=(RadioButton)findViewById(R.id.radioClearall);
                rbTriangle.setOnCheckedChangeListener(new );
                int selectedid= RadioGroup.getCheckedRadioButtonId();
                if(selectedid==R.id.radioTriangle))
                {
                    try{
                        if(containsOnlyNumbers(txtLength1.getText().toString())&&containsOnlyNumbers(txtLength2.getText().toString()))
                        {
                            Length1=Integer.parseInt(txtLength1.getText().toString());
                            Length2=Integer.parseInt(txtLength2.getText().toString());

                            Result=String.valueOf(0.5*Length1*Length2);
                            TextView txtVwArea=(TextView)findViewById(R.id.textviewAreavalue);
                            if(Result!=null && Result.length()>0)
                                txtVwArea.setText(Result);

                        }
                        else
                        {
                            Context context =getApplicationContext();
                            CharSequence text="Enter valid input data for Length(numbers only)";
                            int duration= Toast.LENGTH_SHORT;
                            Toast toast=Toast.makeText(context,text,duration);
                            toast.show();
                        }

                    }
                    catch (Exception e)
                    {
                        Log.e("Calculator",e.getMessage());
                    }
                }
                else if(selectedid==R.id.radioSquare)
                {
                    try{
                        if(containsOnlyNumbers(txtLength1.getText().toString())&&containsOnlyNumbers(txtLength2.getText().toString()))
                        {

                            Length1=Integer.parseInt(txtLength1.getText().toString());
                            txtLength2.setText("");

                            Result = String.valueOf(Length1 * Length1);
                            TextView txtVwArea=(TextView)findViewById(R.id.textviewAreavalue);
                            if(Result!=null && Result.length()>0)
                                txtVwArea.setText(Result);

                        }
                        else
                        {
                            Context context =getApplicationContext();
                            CharSequence text="Enter valid input data for Length(numbers only)";
                            int duration= Toast.LENGTH_SHORT;
                            Toast toast=Toast.makeText(context,text,duration);
                            toast.show();
                        }

                    }
                    catch (Exception e)
                    {
                        Log.e("Calculator",e.getMessage());

                    }
                }
                else if(selectedid==R.id.radioCircle)
                {
                    try
                    {
                        if (containsOnlyNumbers(txtLength1.getText().toString()) && containsOnlyNumbers(txtLength2.getText().toString())) {

                            Length1=Integer.parseInt(txtLength1.getText().toString());
                            Length2=Integer.parseInt(txtLength2.getText().toString());

                            Result = String.valueOf(3.14 * Length1 * Length2);
                            TextView txtVwArea=(TextView)findViewById(R.id.textviewAreavalue);
                            if(Result!=null && Result.length()>0)
                                txtVwArea.setText(Result);

                        }
                        else
                        {
                            Context context =getApplicationContext();
                            CharSequence text="Enter valid input data for Length(numbers only)";
                            int duration= Toast.LENGTH_SHORT;
                            Toast toast=Toast.makeText(context,text,duration);
                            toast.show();
                        }
                    }
                    catch (Exception e)
                    {
                        Log.e("Calculator",e.getMessage());

                    }
                }
                else if(selectedid==R.id.radioRectangle)
                {
                    try
                    {
                        if (containsOnlyNumbers(txtLength1.getText().toString()) && containsOnlyNumbers(txtLength2.getText().toString())) {

                            Length1=Integer.parseInt(txtLength1.getText().toString());
                            Length2=Integer.parseInt(txtLength2.getText().toString());

                            Result = String.valueOf(Length1 * Length2);
                            TextView txtVwArea=(TextView)findViewById(R.id.textviewAreavalue);
                            if(Result!=null && Result.length()>0)
                                txtVwArea.setText(Result);

                        }
                        else
                        {
                            Context context =getApplicationContext();
                            CharSequence text="Enter valid input data for Length(numbers only)";
                            int duration= Toast.LENGTH_SHORT;
                            Toast toast=Toast.makeText(context,text,duration);
                            toast.show();
                        }

                    }
                    catch (Exception e)
                    {
                        Log.e("Calculator",e.getMessage());

                    }

                }
                else if(selectedid==R.id.radioClearall)
                {
                    Result="";
                    TextView txtVwArea=(TextView)findViewById(R.id.textviewAreavalue);

                    txtVwArea.setText(Result);
                }
            }
        });
    }
    public boolean containsOnlyNumbers(String str) {

        if (str == null || str.length() == 0)
            return false;
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i)))
                return false;
        }

        return true;
    }
}
