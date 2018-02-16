package com.kartik.lenovo_pc.cinfokorder;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

/**
 * Created by Lenovo-pc on 10/7/2017.
 */

public class OrderAdapter extends ResourceCursorAdapter {
Context c;
    TextView orderNo,dishes,total;
    public OrderAdapter(Context c, int layout, Cursor cursor, int flags)
    {
        super(c,layout,cursor,flags);
        this.c=c;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        return layoutInflater.inflate(R.layout.orders_list,parent,false);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        orderNo=view.findViewById(R.id.orderNo);
        dishes=view.findViewById(R.id.orderedItems);
        total=view.findViewById(R.id.totalPriceOrder);

        orderNo.setText("Order No. "+cursor.getString(cursor.getColumnIndex(DataBaseAdapter._ID)));
        total.setText("Rs. "+cursor.getString(cursor.getColumnIndex(DataBaseAdapter.TOTALPRICE)));
        dishes.setText(cursor.getString(cursor.getColumnIndex(DataBaseAdapter.DISHES)));


    }
}
