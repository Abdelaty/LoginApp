package com.example.abdel.loginapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.abdel.loginapplication.AccountsActivity.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    Button btnSignOut;
    FirebaseAuth auth;
    FirebaseUser user;
    ProgressDialog PD;
    FirebaseDatabase database;
    DatabaseReference dbRef;
    List<ModelClass> list= new ArrayList<>();
    RecyclerView recyclerView;
    CountriesNamesAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.rvNames);

        recyclerAdapter = new CountriesNamesAdapter(list, MainActivity.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(recyclerAdapter);
        //database

        dbRef = FirebaseDatabase.getInstance().getReference().child("Countries");
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        PD = new ProgressDialog(this);
        PD.setMessage("Loading...");
        PD.setCancelable(true);
        PD.setCanceledOnTouchOutside(false);
        btnSignOut = (Button) findViewById(R.id.sign_out_button);
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();

            }
        });
        getFirebaseData();

    }

    @Override
    protected void onResume() {
        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
        super.onResume();
    }

    void getFirebaseData() {
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot mySnapshot : dataSnapshot.getChildren()) {

                    ModelClass modelClass = mySnapshot.getValue(ModelClass.class);
                    String name = modelClass.getCountryName();
                    String code = modelClass.getCode();
                    modelClass.setCountryName(name);
                    modelClass.setCode(code);

                    list.add(modelClass);
                    Log.d("FIREBASE_EVENET", name + ", " + code);
                    recyclerAdapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
}