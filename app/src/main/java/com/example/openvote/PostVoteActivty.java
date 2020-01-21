package com.example.openvote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.openvote.pojo.Vote;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class PostVoteActivty extends AppCompatActivity {

    private EditText voteTopicET;
    private Button startVoteBTN;
    private TextView pickDateTV, pickTimeTV;
    private CheckBox checkBoxForNotification;


    private String voteCode, voteTopic, endTime, creatorName, creatorId, date, time;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_vote_activty);
        getSupportActionBar().hide();
        init();
        getCreatorName();
        pickDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate();
            }
        });
        pickTimeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickTime();
            }
        });


        startVoteBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataFromField();

                postVote();

                startActivity(new Intent(PostVoteActivty.this, MainActivity.class));


            }
        });
    }

    private void getCreatorName() {
        final DatabaseReference userInfoRef = databaseReference.child("UserInfo").child(creatorId);
        userInfoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                creatorName = dataSnapshot.child("userName").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        userInfoRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()){
//
//                    for(DataSnapshot data : dataSnapshot.getChildren()){
//                        if(data.equals(creatorId)){
//                            UserProfile userProfile = data.getValue(UserProfile.class);
//                            creatorName = userProfile.getUserName();
//                            startVoteBTN.setText(creatorName);
//                        }
//
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

    }

    private void pickTime() {


        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                String time = i + " : " + i1+ " ";
                pickTimeTV.setText(time);

            }
        };

        Calendar mTime = Calendar.getInstance();
        int hour = mTime.get(Calendar.HOUR_OF_DAY);
        int minute = mTime.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,onTimeSetListener,hour,minute,false);
        timePickerDialog.show();

    }

    private void pickDate() {

        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String date = day + "/" + (month+1) +"/" + year;
                pickDateTV.setText(date);

            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,onDateSetListener,year,month,day);
        datePickerDialog.show();

    }

    private void getDataFromField() {
        voteCode = "#4561";
        voteTopic = voteTopicET.getText().toString().trim();


    }


    private void postVote() {

        Vote vote = new Vote(voteCode, voteTopic, creatorId, creatorName, 10, 0, 0);
        DatabaseReference userRef = databaseReference.child("Votes").child(creatorId);
        userRef.setValue(vote).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(PostVoteActivty.this, "Posted successfully", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void init() {
        voteTopicET = findViewById(R.id.voteTopicPVETID);
        startVoteBTN = findViewById(R.id.startVotePVBTN);
        pickDateTV = findViewById(R.id.pickDatePVTVID);
        pickTimeTV = findViewById(R.id.pickTimePVTVID);
        checkBoxForNotification = findViewById(R.id.checkboxPVID);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        creatorId = firebaseAuth.getCurrentUser().getUid();
    }
}
