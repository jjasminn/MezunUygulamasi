package com.example.mezunuygulamasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ForgetPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        EditText username=(EditText) findViewById(R.id.username);
        EditText new_password=(EditText) findViewById(R.id.password);
        EditText re_password=(EditText) findViewById(R.id.re_password);
        Button changePasswordButton=(Button) findViewById(R.id.changePassword);
        TextView goToLogin= (TextView)findViewById(R.id.goToLogin);
        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }

        });
        changePasswordButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(new_password.getText()==re_password.getText()){
                    //password degistirme islemi

                }

            }
        });

    }
}