package com.kartik.lenovo_pc.cinfokorder;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

/**
 * Created by Lenovo-pc on 9/17/2017.
 */

public class MyOrders extends AppCompatActivity {
    ListView list;
    OrderAdapter mAdapter;
    Cursor c;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_orders);

        list = (ListView) findViewById(R.id.ordersList);
        mAdapter = new OrderAdapter(this, R.layout.orders_list, c, 0);
        list.setAdapter(mAdapter);
        c = readOrders();
        c.moveToFirst();
        mAdapter.swapCursor(c);
    }
    public static Cursor readOrders()
    {
        return MainActivity.dataBase3.getWritableDatabase().query(DataBaseAdapter.ORDERS,
                DataBaseAdapter.orderColumns, null, new String[] {}, null, null,
                null);


    }
}
