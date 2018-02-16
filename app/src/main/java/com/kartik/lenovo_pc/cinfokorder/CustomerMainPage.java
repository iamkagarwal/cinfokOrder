package com.kartik.lenovo_pc.cinfokorder;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class CustomerMainPage extends AppCompatActivity {

ListView list;
    CursorAdapter mAdapter;
    Cursor c;
    final static int DATA=3;
    TextView id;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_login);
        list=(ListView)findViewById(R.id.customerList);
        mAdapter=new CursorAdapter(this,R.layout.list,c,0);


        list.setAdapter(mAdapter);
        c=AddDish.readEverything();
        mAdapter.swapCursor(c);


        list.setAdapter(mAdapter);
       list.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener()
       {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
           {
               id=(TextView)view.findViewById(R.id._id);
               Intent intent=new Intent(CustomerMainPage.this,DishData.class);
               String i_d=id.getText().toString();
               intent.putExtra("i_d",i_d);
               startActivityForResult(intent,DATA);

           }
       });



       }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.customer_menu,menu);
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.cart:
                Intent intent=new Intent(CustomerMainPage.this,Cart.class);
                startActivity(intent);
                break;
            case R.id.myorders:
                Intent intent1=new Intent(CustomerMainPage.this,MyOrders.class);
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}