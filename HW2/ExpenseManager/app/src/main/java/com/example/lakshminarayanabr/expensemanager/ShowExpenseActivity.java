package com.example.lakshminarayanabr.expensemanager;

import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

public class ShowExpenseActivity extends AppCompatActivity {
 TextView txtName,txtCategory,txtAmount,txtDate;
    ImageView imgReceipt,imgPrev,imgNext,imgFirst,imgLast;

    ArrayList<Expense> expenseArrayList=new ArrayList<Expense>();
    Expense expensSown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_expense);

        txtName=(TextView)findViewById(R.id.textNameValue);
        txtAmount=(TextView)findViewById(R.id.textViewAmountValue);
        txtCategory=(TextView)findViewById(R.id.textViewCategoryValue);
        txtDate=(TextView)findViewById(R.id.textViewDateValue);

        imgFirst=(ImageView)findViewById(R.id.imageViewFirst);
        imgPrev=(ImageView)findViewById(R.id.imageViewPrev);
        imgNext=(ImageView)findViewById(R.id.imageViewNext);
        imgLast=(ImageView)findViewById(R.id.imageViewLast);

        if(getIntent().getExtras().getParcelableArrayList(MainActivity.EXPENSE_ARRAYLIST)!=null)
        {
            expenseArrayList=getIntent().getExtras().getParcelableArrayList(MainActivity.EXPENSE_ARRAYLIST);


        }

        expensSown=expenseArrayList.get(0);

        if(expensSown!=null)
        {
            txtName.setText(expensSown.expenseName);
            txtCategory.setText(expensSown.category);
            txtAmount.setText("$ "+expensSown.amount.toString());
            txtDate.setText(expensSown.expenseDate);
            imgReceipt=(ImageView)findViewById(R.id.imageViewReceipt);

            if(expensSown.imgURI!=null)
            {
                try {

                    Uri imageUri=Uri.parse(expensSown.imgURI);
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(expensSown.imgURI));
                    imgReceipt.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }



        imgFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                expensSown=expenseArrayList.get(0);

                if(expensSown!=null)
                {
                    txtName.setText(expensSown.expenseName);
                    txtCategory.setText(expensSown.category);
                    txtAmount.setText("$ "+expensSown.amount.toString());
                    txtDate.setText(expensSown.expenseDate);
                    imgReceipt=(ImageView)findViewById(R.id.imageViewReceipt);

                    if(expensSown.imgURI!=null)
                    {
                        try {

                            Uri imageUri=Uri.parse(expensSown.imgURI);
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(ShowExpenseActivity.this.getContentResolver(), Uri.parse(expensSown.imgURI));
                            imgReceipt.setImageBitmap(bitmap);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }




            }
        });

        imgLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                expensSown=expenseArrayList.get(expenseArrayList.size()-1);
                if(expensSown!=null)
                {
                    txtName.setText(expensSown.expenseName);
                    txtCategory.setText(expensSown.category);
                    txtAmount.setText("$ "+expensSown.amount.toString());
                    txtDate.setText(expensSown.expenseDate);
                    imgReceipt=(ImageView)findViewById(R.id.imageViewReceipt);

                    if(expensSown.imgURI!=null)
                    {
                        try {

                            Uri imageUri=Uri.parse(expensSown.imgURI);
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(ShowExpenseActivity.this.getContentResolver(), Uri.parse(expensSown.imgURI));
                            imgReceipt.setImageBitmap(bitmap);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }


            }
        });
        imgPrev.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {

                if(expenseArrayList.indexOf(expensSown)-1>=0)
                {
                    expensSown=expenseArrayList.get(expenseArrayList.indexOf(expensSown)-1);

                    if(expensSown!=null)
                    {
                        txtName.setText(expensSown.expenseName);
                        txtCategory.setText(expensSown.category);
                        txtAmount.setText("$ "+expensSown.amount.toString());
                        txtDate.setText(expensSown.expenseDate);
                        imgReceipt=(ImageView)findViewById(R.id.imageViewReceipt);

                        if(expensSown.imgURI!=null)
                        {
                            try {

                                Uri imageUri=Uri.parse(expensSown.imgURI);
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(ShowExpenseActivity.this.getContentResolver(), Uri.parse(expensSown.imgURI));
                                imgReceipt.setImageBitmap(bitmap);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }

            }
        });
        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(expenseArrayList.indexOf(expensSown)+1<expenseArrayList.size())
                {
                    expensSown=expenseArrayList.get(expenseArrayList.indexOf(expensSown)+1);

                    if(expensSown!=null)
                    {
                        txtName.setText(expensSown.expenseName);
                        txtCategory.setText(expensSown.category);
                        txtAmount.setText("$ "+expensSown.amount.toString());
                        txtDate.setText(expensSown.expenseDate);
                        imgReceipt=(ImageView)findViewById(R.id.imageViewReceipt);

                        if(expensSown.imgURI!=null)
                        {
                            try {

                                Uri imageUri=Uri.parse(expensSown.imgURI);
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(ShowExpenseActivity.this.getContentResolver(), Uri.parse(expensSown.imgURI));
                                imgReceipt.setImageBitmap(bitmap);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }


            }
        });
















        findViewById(R.id.buttonFinish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
