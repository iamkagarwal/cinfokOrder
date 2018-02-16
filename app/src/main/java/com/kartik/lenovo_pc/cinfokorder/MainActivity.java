package com.kartik.lenovo_pc.cinfokorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Lenovo-pc on 9/17/2017.
 */

public class MainActivity extends AppCompatActivity {

static  DataBaseAdapter dataBase1;
    static DataBaseAdapter dataBase2;
    static DataBaseAdapter dataBase3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        dataBase1 = new DataBaseAdapter(getApplicationContext());
        dataBase2 = new DataBaseAdapter(getApplicationContext());
        dataBase3 = new DataBaseAdapter(getApplicationContext());

        Button customerPage=(Button)findViewById(R.id.cust);
        Button adminPage=(Button)findViewById(R.id.admin);

        customerPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,CustomerMainPage.class);
                startActivity(intent);
            }
        });
        adminPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,AdminLogin.class);
                startActivity(intent);
            }
        });
    }
}
