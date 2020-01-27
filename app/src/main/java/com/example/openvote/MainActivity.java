package com.example.openvote;

import android.content.Intent;
import android.os.Bundle;

import com.example.openvote.adapter.AdapertForHome;
import com.example.openvote.pojo.Vote;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    //declaration
    private ImageView addVoteIV, menuIconIV;
    private RecyclerView activeVoteRecyclerView;
    private DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    private ArrayList<Vote> votes;
    private AdapertForHome adapertForHome;
    private DrawerLayout mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();  //initialize declared elements
        initRecyclerView();  //initialize recyclerview
        getActiveVotes(); //get data from firebase
        /*addVoteIV.setOnClickListener(this); //takes to new vote creation page
        menuIconIV.setOnClickListener(this);*/

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        /*mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.content_frame);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);*/
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(MainActivity.this);
    }

    //response to button click
    @Override
    public void onClick(View v) {
        /*if (v.getId() == R.id.action_addVote) {
            startActivity(new Intent(MainActivity.this, PostVoteActivty.class));
        }

        else if(v.getId()==R.id.action_notification){
            firebaseAuth.signOut();
            startActivity(new Intent(MainActivity.this, NotificationActivity.class));
        }*/
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_addVote) {
            startActivity(new Intent(MainActivity.this, PostVoteActivty.class));
            return true;
        }
        if (id == R.id.action_notification) {
            startActivity(new Intent(MainActivity.this, NotificationActivity.class));
            return true;
        }
        if (id == R.id.action_logout){
            firebaseAuth.signOut();
            Intent intent = new Intent(MainActivity.this,LogInActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();


        }
        return super.onOptionsItemSelected(item);
    }



    //initialize declared elements
    private void init() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        activeVoteRecyclerView = findViewById(R.id.activeVoteRecyclerViewID);
        votes = new ArrayList<>();
        adapertForHome = new AdapertForHome(votes, this);
    }



    //initialize recyclerview
    private void initRecyclerView() {
        activeVoteRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        activeVoteRecyclerView.setAdapter(adapertForHome);
    }



    //get data from firebase
    private void getActiveVotes() {
        votes.clear();

        DatabaseReference activVoteRef = databaseReference.child("Votes");
        activVoteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot data: dataSnapshot.getChildren()){
                        Vote vote = data.getValue(Vote.class);
                        votes.add(vote);
                        adapertForHome.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.content_frame);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
