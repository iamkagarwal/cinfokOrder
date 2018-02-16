package com.kartik.lenovo_pc.cinfokorder;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

/**
 * Created by Lenovo-pc on 9/18/2017.
 */

public class CartAdapter extends ResourceCursorAdapter {
        Context c;
    TextView dishName,amount,no,id;
    public CartAdapter(Context c, int layout, Cursor cursor, int flags)
    {
        super(c,layout,cursor,flags);
        this.c=c;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        return layoutInflater.inflate(R.layout.cart_list,parent,false);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        dishName=view.findViewById(R.id.cartDish);
        amount=view.findViewById(R.id.amountCartList);
        no=view.findViewById(R.id.numCart);
        id=view.findViewById(R.id.id_);

        dishName.setText(cursor.getString(cursor.getColumnIndex(DataBaseAdapter.DISH_NAME)));
        amount.setText("Rs. "+cursor.getString(cursor.getColumnIndex(DataBaseAdapter.AMOUNT)));
        no.setText(cursor.getString(cursor.getColumnIndex(DataBaseAdapter.NO)));
        id.setText(cursor.getString(cursor.getColumnIndex(DataBaseAdapter._ID)));






    }
    }

