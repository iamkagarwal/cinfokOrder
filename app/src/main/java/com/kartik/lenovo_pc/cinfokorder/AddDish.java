package com.kartik.lenovo_pc.cinfokorder;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by Lenovo-pc on 9/17/2017.
 */

public class AddDish extends AppCompatActivity {
        EditText name,description,price;
    Button submit,cancel;
    String dishName,desc,cost,vnv;
    RadioGroup type;
    RadioButton dishType,veg,nonVeg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_dish);
        type=(RadioGroup)findViewById(R.id.type);
        veg=(RadioButton)findViewById(R.id.veg);
        nonVeg=(RadioButton)findViewById(R.id.nonveg);

        submit=(Button)findViewById(R.id.submit);
        cancel=(Button)findViewById(R.id.cancel);
        name=(EditText)findViewById(R.id.name);
        description=(EditText)findViewById(R.id.desc);
        price=(EditText)findViewById(R.id.price);



        if (getIntent().getIntExtra("flag", 0) == 1) {
            Cursor cursor = MainActivity.dataBase1.getWritableDatabase().query(DataBaseAdapter.NAME, DataBaseAdapter.columns
                    ,DataBaseAdapter._ID + "=?",new String[]{String.valueOf(getIntent().getIntExtra("id", -1))}, null, null, null);
            cursor.moveToFirst();
            name.setText(cursor.getString(cursor.getColumnIndex(DataBaseAdapter.DISH_NAME)));
            description.setText(cursor.getString(cursor.getColumnIndex(DataBaseAdapter.DESCRIPTION)));
            price.setText(cursor.getString(cursor.getColumnIndex(DataBaseAdapter.PRICE)));

            if(cursor.getString(cursor.getColumnIndex(DataBaseAdapter.TYPE)).equals("Veg dish"))
            {
                veg.setChecked(true);
            }
            if(cursor.getString(cursor.getColumnIndex(DataBaseAdapter.TYPE)).equals("Non-Veg"))
            {
                nonVeg.setChecked(true);
            }

        }


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                dishName=name.getText().toString();
                desc=description.getText().toString();
                cost=price.getText().toString();
                int selectedID=type.getCheckedRadioButtonId();
                dishType=(RadioButton)findViewById(selectedID);
                try {
                    vnv=dishType.getText().toString();
                }catch (NullPointerException n)
                {
                    vnv="";
                }



                if(dishName.trim().equals("") || desc.trim().equals("") || cost.equals("")||vnv.equals("")  )
                {
                    new AlertDialog.Builder(AddDish.this).setTitle("Input not correct").setMessage
                            ("Fields cannot be left Blank")
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();
                }
                else {

                    insertDish();
                    setResult(RESULT_OK, null);
                    finish();
                }
            }
        });
    }
    public void insertDish()
    {
        ContentValues cv=new ContentValues();

        cv.put(DataBaseAdapter.DISH_NAME,dishName);


        cv.put(DataBaseAdapter.DESCRIPTION,desc);


        cv.put(DataBaseAdapter.PRICE,cost);

        cv.put(DataBaseAdapter.TYPE,vnv);


        MainActivity.dataBase1.getWritableDatabase().insert(DataBaseAdapter.NAME,null,cv);

        cv.clear();

    }
    public static Cursor readEverything()
    {
        return MainActivity.dataBase1.getWritableDatabase().query(DataBaseAdapter.NAME,
                DataBaseAdapter.columns, null, new String[] {}, null, null,
                null);
    }
}
