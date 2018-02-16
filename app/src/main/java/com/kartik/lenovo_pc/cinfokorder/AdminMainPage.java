package com.kartik.lenovo_pc.cinfokorder;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by Lenovo-pc on 9/17/2017.
 */

public class AdminMainPage extends AppCompatActivity {
CursorAdapter mAdapter;
    ListView list;
    Cursor c=null;
    final int ADD=0;
    static DataBaseAdapter dataBase;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_main_page);
        list=(ListView)findViewById(R.id.adminList);
        mAdapter=new CursorAdapter(this,R.layout.list,c,0);
        dataBase = new DataBaseAdapter(getApplicationContext());
        list.setAdapter(mAdapter);
        c=AddDish.readEverything();
        mAdapter.swapCursor(c);
        registerForContextMenu(list);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        getMenuInflater().inflate(R.menu.admin_context,menu);
    }
static int edit=1;
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int i=(int)info.id;
        switch (item.getItemId()) {
            case R.id.delete:
                int row =  dataBase.getWritableDatabase().delete(DataBaseAdapter.NAME,DataBaseAdapter._ID+"=?",new String[]{String.valueOf(i)});
                c=AddDish.readEverything();
                mAdapter.swapCursor(c);
                return true;
            case R.id.update:
                Intent intent=new Intent(AdminMainPage.this,AddDish.class);

                intent.putExtra("id",i);
                intent.putExtra("flag",1);

                startActivityForResult(intent,edit);

            default:return false;
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode==RESULT_OK && requestCode==ADD)
        {
            c=AddDish.readEverything();
            mAdapter.swapCursor(c);
        }

        if(resultCode==RESULT_OK && requestCode==edit)
        {
            c=AddDish.readEverything();
            mAdapter.swapCursor(c);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.add:
                Intent intent=new Intent(AdminMainPage.this,AddDish.class);
                startActivityForResult(intent,ADD);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
