package com.kartik.lenovo_pc.cinfokorder;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Lenovo-pc on 9/17/2017.
 */

public class DishData extends AppCompatActivity {
        int no=0;
    Button plus,minus,addToCart;
    TextView name,price,desc,type,num,total;
    int pr,pr1=0,pri;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dish_data);
        name=(TextView)findViewById(R.id.dish);
        price=(TextView)findViewById(R.id.priceOfDish);
        desc=(TextView)findViewById(R.id.descrip);
        type=(TextView)findViewById(R.id.vegOrNonVeg);
        plus=(Button)findViewById(R.id.plus);
        minus=(Button)findViewById(R.id.minus);
        addToCart=(Button)findViewById(R.id.addToCart);
        num=(TextView)findViewById(R.id.number);
        total=(TextView)findViewById(R.id.total);


        if (getIntent().getIntExtra("flag", 0) == 1)
        {
            Cursor cursor = MainActivity.dataBase2.getWritableDatabase().query(DataBaseAdapter.CARTNAME, DataBaseAdapter.cartColumns
                    ,DataBaseAdapter._ID + "=?",new String[]{String.valueOf(getIntent().getIntExtra("id", -1))}, null, null, null);
            cursor.moveToFirst();
            name.setText(cursor.getString(cursor.getColumnIndex(DataBaseAdapter.DISH_NAME)));
            desc.setText(cursor.getString(cursor.getColumnIndex(DataBaseAdapter.DESCRIPTION)));
            type.setText(cursor.getString(cursor.getColumnIndex(DataBaseAdapter.TYPE)));

            pr=Integer.parseInt(cursor.getString(cursor.getColumnIndex(DataBaseAdapter.PRICE)));
            price.setText("Rs. "+cursor.getString(cursor.getColumnIndex(DataBaseAdapter.PRICE)));
            addToCart.setText("Update");
            no=Integer.parseInt(cursor.getString(cursor.getColumnIndex(DataBaseAdapter.NO)));

            if(no>0 && no<10) {
                num.setText("0"+cursor.getString(cursor.getColumnIndex(DataBaseAdapter.NO)));
            }
            else
            {
                num.setText(""+cursor.getString(cursor.getColumnIndex(DataBaseAdapter.NO)));
            }


        }


       else {

            num.setText("00");

            Cursor cursor = MainActivity.dataBase1.getWritableDatabase().query(DataBaseAdapter.NAME, DataBaseAdapter.columns
                    , DataBaseAdapter._ID + "=?", new String[]{getIntent().getStringExtra("i_d")}, null, null, null);
            cursor.moveToFirst();
            name.setText(cursor.getString(cursor.getColumnIndex(DataBaseAdapter.DISH_NAME)));
            desc.setText(cursor.getString(cursor.getColumnIndex(DataBaseAdapter.DESCRIPTION)));
            pr = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DataBaseAdapter.PRICE)));
            price.setText("Rs. " + cursor.getString(cursor.getColumnIndex(DataBaseAdapter.PRICE)));
            type.setText(cursor.getString(cursor.getColumnIndex(DataBaseAdapter.TYPE)));
            pri = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DataBaseAdapter.PRICE)));
            no=0;
        }

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(no==0)
                {

                    num.setText("00");
                    pr1=no*pr;
                    total.setText("Rs. "+pr1);
                }
                else if(no>0 && no<=10)
                {
                    --no;
                    pr1=no*pr;
                    num.setText("0"+no);
                    total.setText("Rs. "+pr1);

                }
                else
                {

                    --no;
                    pr1=no*pr;
                    num.setText(""+no);
                    total.setText("Rs. "+pr1);


                }
            }
        });



        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(no>=0 && no<9)
                {
                    ++no;
                    pr1=no*pr;
                    num.setText("0"+no);
                    total.setText("Rs. "+pr1);

                }
                else
                {
                    ++no;
                    pr1=no*pr;
                    num.setText(""+no);
                    total.setText("Rs. "+pr1);

                }




            }
        });

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(getIntent().getIntExtra("flag",0)==1 && no==0)
                {
                    int i=getIntent().getIntExtra("id",-1);
                    MainActivity.dataBase2.getWritableDatabase().delete(DataBaseAdapter.CARTNAME,DataBaseAdapter._ID+"=?",new String[]{String.valueOf(i)});
                    finish();
                }
                else if (getIntent().getIntExtra("flag", 0) == 1)
                {
                    update();
                    finish();
                }
                else if(no==0)
                {
                   showdialogue();
                }
                else
                {
                    insertToCart();
                    finish();
                }






            }
        });


    }

    public void update()
    {
        ContentValues cv=new ContentValues();
        cv.put(DataBaseAdapter.NO,no);
        cv.put(DataBaseAdapter.AMOUNT,pr1);
        int idFromIntent = getIntent().getIntExtra("id", -1);
        MainActivity.dataBase2.getWritableDatabase().update(DataBaseAdapter.CARTNAME, cv,
                DataBaseAdapter._ID + "=" + idFromIntent, new String[]{});

    }

    public void showdialogue()
    {
        new AlertDialog.Builder(DishData.this).setTitle("You need to add atleast one item to the cart")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }

    public void insertToCart()
    {
        Cursor c=Cart.readCart();
        c.moveToFirst();
        int check=0;

            while (!c.isAfterLast()) {
//                String str1=c.getString(c.getColumnIndex(DataBaseAdapter.DISH_NAME));
//                String str2=name.getText().toString();

                if (c.getString(c.getColumnIndex(DataBaseAdapter.DISH_NAME)).equals(name.getText().toString()) ) {
                    check=1;
                    int amt1 = Integer.parseInt(c.getString(c.getColumnIndex(DataBaseAdapter.AMOUNT)));
                    int amt2 = pr1;
                    amt1 = amt1 + amt2;
                    int no1 = Integer.parseInt(c.getString(c.getColumnIndex(DataBaseAdapter.NO)));
                    int no2 = no;
                    no1 = no1 + no2;
                    int id = Integer.parseInt(c.getString(c.getColumnIndex(DataBaseAdapter._ID)));
                    ContentValues cv = new ContentValues();
                    cv.put(DataBaseAdapter.AMOUNT, amt1);
                    cv.put(DataBaseAdapter.NO, no1);
                    MainActivity.dataBase2.getWritableDatabase().update(DataBaseAdapter.CARTNAME, cv,
                            DataBaseAdapter._ID + "=" + id, new String[]{});
                }c.moveToNext();
            }
                if(check==0)
                {
                    ContentValues cv=new ContentValues();

                    cv.put(DataBaseAdapter.DISH_NAME,name.getText().toString());


                    cv.put(DataBaseAdapter.DESCRIPTION,desc.getText().toString());


                    cv.put(DataBaseAdapter.PRICE,pri);

                    cv.put(DataBaseAdapter.TYPE,type.getText().toString());

                    cv.put(DataBaseAdapter.NO,no);

                    cv.put(DataBaseAdapter.AMOUNT,pr1);


                    MainActivity.dataBase2.getWritableDatabase().insert(DataBaseAdapter.CARTNAME,null,cv);

                    cv.clear();
                    c.moveToNext();
                }
            }


    }

