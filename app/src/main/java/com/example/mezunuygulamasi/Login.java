package com.example.mezunuygulamasi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), Announcements.class);
            startActivity(intent);
            finish();

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);
        EditText editTextmail=(EditText) findViewById(R.id.email);
        EditText editTextpassword=(EditText) findViewById(R.id.password);
        Button loginButton=(Button) findViewById(R.id.loginButton);
        TextView goToSignUp= (TextView)findViewById(R.id.goSignIn);
        ProgressBar progressBar=(ProgressBar) findViewById(R.id.progressBar);
        TextView forgetPasswordButton = (TextView) findViewById(R.id.forget_password);
        goToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
                finish();
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String mail,password;
                mail= String.valueOf(editTextmail.getText());
                password= String.valueOf(editTextpassword.getText());
                if(TextUtils.isEmpty(mail)||TextUtils.isEmpty(password)){
                    Toast.makeText(Login.this,"Mail veya sifre bos!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    mAuth.signInWithEmailAndPassword(mail, password)
                            .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        Toast.makeText(Login.this,"Login Succesfull",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), Profil.class);
                                        startActivity(intent);
                                        finish();

                                    } else {


                                        Toast.makeText(Login.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                }

                progressBar.setVisibility(View.GONE);
            }
        });
        forgetPasswordButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ForgetPassword.class);
                startActivity(intent);
                finish();

            }
        });

    }
}