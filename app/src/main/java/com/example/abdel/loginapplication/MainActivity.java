package com.example.abdel.loginapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
    List<ModelClass> list;
    RecyclerView recyclerview;
    CountriesNamesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerview = (RecyclerView) findViewById(R.id.rvNames);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        //database
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference("Countries");
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
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference("Countries");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<ModelClass>();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                     ModelClass p = dataSnapshot.child("name").getValue(ModelClass.class);
                    list.add(p);
                 Object object;
                    ArrayList<String> values = new ArrayList<String>();
                    object = dataSnapshot.child("Countries").child("name").getValue();
                    assert object != null;
//                    values.add(object.toString());
                    String value = dataSnapshot1.child("name").getValue(String.class);

                    Log.v("Value is ", value);
                }
                ;
                recyclerview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


    }
}
