package com.kartik.lenovo_pc.cinfokorder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Lenovo-pc on 9/17/2017.
 */

public class AdminLogin extends AppCompatActivity {
    String username,pass;
    EditText uname;
    EditText password;
    Button login;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_login);
        uname=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.login);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                username=uname.getText().toString();
                pass=password.getText().toString();
                if(username.equals("admin") && pass.equals("admin"))
                {
                    Intent intent=new Intent(AdminLogin.this,AdminMainPage.class);
                    startActivity(intent);
                }
                else
                {
                    new AlertDialog.Builder(AdminLogin.this).setTitle("Input not correct").setMessage
                            ("Username or password incorrect")
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();
                }

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        password.setText("");
    }
}
