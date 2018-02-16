package com.kartik.lenovo_pc.cinfokorder;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

/**
 * Created by Lenovo-pc on 9/17/2017.
 */

public class CursorAdapter extends ResourceCursorAdapter {
Context c;
    TextView dishName,description,price,type,id;
    public CursorAdapter(Context c,int layout,Cursor cursor,int flags)
    {
        super(c,layout,cursor,flags);
        this.c=c;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        return layoutInflater.inflate(R.layout.list,parent,false);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        dishName=view.findViewById(R.id.dish);
        description=view.findViewById(R.id.descr);
        price=view.findViewById(R.id.priceDish);
        type=view.findViewById(R.id.dishType);
        id=view.findViewById(R.id._id);

        dishName.setText(cursor.getString(cursor.getColumnIndex(DataBaseAdapter.DISH_NAME)));
        description.setText(cursor.getString(cursor.getColumnIndex(DataBaseAdapter.DESCRIPTION)));
        price.setText("Rs. "+cursor.getString(cursor.getColumnIndex(DataBaseAdapter.PRICE)));
        type.setText(cursor.getString(cursor.getColumnIndex(DataBaseAdapter.TYPE)));
        id.setText(cursor.getString(cursor.getColumnIndex(DataBaseAdapter._ID)));
    }
}
