package com.kartik.lenovo_pc.cinfokorder;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Lenovo-pc on 9/17/2017.
 */

public class Cart extends AppCompatActivity {
    ListView list;
    CartAdapter mAdapter;
    Cursor c, c3, c4;
    String amt, no;
    final int EDIT=0;
    static int sum=0;
    static Double sgst,cgst,delch,total;
    String allDishes;
    static TextView subtotal,cgstText, sgstText, delivery, totalOrder,text;
    Button order,applyPromo;
    EditText promo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        applyPromo=(Button)findViewById(R.id.enterPromo);
        promo=(EditText)findViewById(R.id.promoText);
        subtotal=(TextView)findViewById(R.id.subtotal);
        cgstText=(TextView)findViewById(R.id.cgst);
        sgstText=(TextView)findViewById(R.id.sgst);
        delivery=(TextView)findViewById(R.id.delivery_charge);
        totalOrder=(TextView)findViewById(R.id.totalCart);
        order=(Button)findViewById(R.id.confirmorder);
        text=(TextView)findViewById(R.id.textForNoItems);
        list = (ListView) findViewById(R.id.cartList);
        mAdapter = new CartAdapter(this, R.layout.cart_list, c, 0);



        subtotal();

        list.setAdapter(mAdapter);
        c = readCart();
        c.moveToFirst();
        mAdapter.swapCursor(c);

        if(c.getCount()==0)
        {

            text.setVisibility(View.VISIBLE);
        }
        applyPromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(promo.getText().toString().equals("30OFF"))
                {
                    if(total>30) {
                        total = total - 30.00;
                        total = ((double) (Math.round(total * 100))) / 100;
                        new AlertDialog.Builder(Cart.this).setTitle("Promo Applied you get Rs.30 off")
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).show();
                    }
                    else
                    {
                        total=0.0;
                        total = ((double) (Math.round(total * 100))) / 100;
                        new AlertDialog.Builder(Cart.this).setTitle("Promo Applied you get Rs.30 off")
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).show();
                    }
                    totalOrder.setText("Rs."+total);
                }
                else
                {
                    new AlertDialog.Builder(Cart.this).setTitle("Invalid Promo")
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();
                }
            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allDishes = "";
                ContentValues cv = new ContentValues();
                cv.put(DataBaseAdapter.TOTALPRICE, total);

                Cursor c = readCart();
                c.moveToFirst();

                if (c.getCount() == 0)
                {
                    new AlertDialog.Builder(Cart.this).setTitle("Cart empty")
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();
                }
                else {
                    while (!c.isLast()) {
                        allDishes = allDishes + "" + c.getString(c.getColumnIndex(DataBaseAdapter.NO)) + " " + c.getString(c.getColumnIndex(DataBaseAdapter.DISH_NAME)) + ", ";
                        c.moveToNext();
                    }
                    if (c.isLast()) {
                        allDishes = allDishes + c.getString(c.getColumnIndex(DataBaseAdapter.NO)) + " " + c.getString(c.getColumnIndex(DataBaseAdapter.DISH_NAME)) + ".";
                    }
                    cv.put(DataBaseAdapter.DISHES, allDishes);

                    MainActivity.dataBase3.getWritableDatabase().insert(DataBaseAdapter.ORDERS, null, cv);


                    cv.clear();

                    deletcart();
                    Intent i = new Intent(Cart.this, MyOrders.class);
                    startActivity(i);

                }
            }
        });




        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(Cart.this,DishData.class);
                intent.putExtra("flag",1);


                String idInString=((TextView)view.findViewById(R.id.id_)).getText().toString();
                int idInInt=Integer.parseInt(idInString);
                intent.putExtra("id",idInInt);
                startActivityForResult(intent,EDIT);

            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == EDIT)
        {
            c = readCart();
            mAdapter.swapCursor(c);
        }

    }

    public void deletcart()
    {
        MainActivity.dataBase2.getWritableDatabase().delete(DataBaseAdapter.CARTNAME,null,null);
        MainActivity.dataBase2.getWritableDatabase().execSQL("delete from "+DataBaseAdapter.CARTNAME);
//        MainActivity.dataBase2.getWritableDatabase().execSQL("TRUNCATE table"+DataBaseAdapter.CARTNAME);
        MainActivity.dataBase2.getWritableDatabase().close();
    }



    public static void subtotal()
    {
        sum=0;
       Cursor c=readCart();
        c.moveToFirst();
        while(c.isAfterLast()!=true)
        {
            sum=sum+Integer.parseInt(c.getString(c.getColumnIndex(DataBaseAdapter.AMOUNT)));
            c.moveToNext();
        }
        sgst=(6.0/100)*sum;
        sgst = ((double) (Math.round(sgst * 100))) / 100;
        cgst=(6.0/100)*sum;
        cgst = ((double) (Math.round(cgst * 100))) / 100;
        if(c.getCount()==0)
        {
            delch=0.0;
        }
        else{
            delch=30.0;
        }

        total=sum+sgst+cgst+delch;
        total = ((double) (Math.round(total * 100))) / 100;

        subtotal.setText("Rs."+sum);
        cgstText.setText("Rs."+cgst);
        sgstText.setText("Rs."+sgst);
        delivery.setText("Rs."+delch);
        totalOrder.setText("Rs."+total);
    }

    public static Cursor readCart()
    {
        return MainActivity.dataBase2.getWritableDatabase().query(DataBaseAdapter.CARTNAME,
                DataBaseAdapter.cartColumns, null, new String[] {}, null, null,
                null);


    }

    @Override
    protected void onResume() {
        super.onResume();
        list.setAdapter(mAdapter);
        Cursor c = readCart();
        c.moveToFirst();
        mAdapter.swapCursor(c);
        promo.setText("");
        if(c.getCount()==0)
        {
            text.setVisibility(View.VISIBLE);
        }
        subtotal();


    }
}
