package com.example.openvote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {

    private EditText emailET, passwordET;
    private Button loginBTN;
    private Button signupBTN;
    private String email, password;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        init();
        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailET.getText().toString().trim();
                String password = passwordET.getText().toString().trim();
                logIn(email, password);
            }
        });
        signupBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });




    }

    private void logIn( String email, String password) {
        if(email.isEmpty()){
            emailET.setError("Enter an email address");
            emailET.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailET.setError("Enter a valid email address");
            emailET.requestFocus();
        }
        if(password.isEmpty()){
            passwordET.setError("Enter a password");
            passwordET.requestFocus();
            return;
        }
        if(password.length()<5){
            passwordET.setError("Minimum length of password is 5");
            passwordET.requestFocus();
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LogInActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            startActivity(new Intent(LogInActivity.this, MainActivity.class));
                        }
                        else {
                            Toast.makeText(LogInActivity.this, "Email and password didn't matched", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void init() {
        emailET = findViewById(R.id.emailETLI);
        passwordET = findViewById(R.id.passwordETLI);
        loginBTN = findViewById(R.id.loginBTNLI);
        signupBTN = findViewById(R.id.signupBTNLI);
        firebaseAuth = FirebaseAuth.getInstance();
    }


}
