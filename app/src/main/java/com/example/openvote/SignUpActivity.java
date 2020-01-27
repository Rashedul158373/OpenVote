package com.example.openvote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.openvote.pojo.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {

    private EditText userNameET, mobileNumberET, emailET, passwordET, confirmPasswordET;
    private RadioGroup genderRG;
    private TextView goForSignUp, dateOfBirth_tv;
    private Button registerBTN;
    private String userName, mobileNumber, email, dateOfBirth, gender, password, confirmPassword;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();

        genderRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = findViewById(i);
                gender= rb.getText().toString();
            }
        });
        init();

        registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataFromFields();


                if (confirmPassword.equals(password)) {
                    createUser();
                } else {
                    Toast.makeText(SignUpActivity.this, "Password didn't mached", Toast.LENGTH_SHORT).show();
                }

                goForSignUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
                    }
                });


            }
        });


    }
    private void pickDate() {

        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String date = day + "/" + (month+1) +"/" + year;
                dateOfBirth_tv.setText(date);

            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,onDateSetListener,year,month,day);
        datePickerDialog.show();

    }


    // collect data form fields
    private void getDataFromFields() {

        userName = userNameET.getText().toString().trim();
        mobileNumber = mobileNumberET.getText().toString().trim();
        email = emailET.getText().toString().trim();
        dateOfBirth = dateOfBirth_tv.getText().toString().trim();
        password = passwordET.getText().toString().trim();
        confirmPassword = confirmPasswordET.getText().toString().trim();
    }

    //Register users
    private void createUser() {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            saveInfo();
                        } else {
                            String error = task.getException().toString().trim();
                            Toast.makeText(SignUpActivity.this, "Message: " + error + "", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


    //save data to db
    private void saveInfo() {
        String uId = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference userRef = databaseReference.child("UserInfo").child(uId);
        UserProfile userProfile = new UserProfile(userName, mobileNumber, email, dateOfBirth, gender, password);

        userRef.setValue(userProfile).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                }
                else {
                    Toast.makeText(SignUpActivity.this, "Error", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }


    //init fields
    private void init() {
        genderRG = findViewById(R.id.genderRG);
        userNameET = findViewById(R.id.userNameETSU);
        mobileNumberET = findViewById(R.id.mobileNumberETSU);
        goForSignUp = findViewById(R.id.goForSignUp);
        emailET = findViewById(R.id.emailETSU);
        dateOfBirth_tv = findViewById(R.id.dob_tv);
        passwordET = findViewById(R.id.passwordETSU);
        confirmPasswordET = findViewById(R.id.confirmPasswordETSU);
        registerBTN = findViewById(R.id.registerBTNSU);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }
}
