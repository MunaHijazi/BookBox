package com.example.pp_ff;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class notefication extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter,Adapter;
    private LinearLayoutManager mLayoutManager,LayoutManager;
    private Query mQueryCurrentUser,q;
    DatabaseReference reff1,r,notreff;
    FirebaseAuth mAuth, Auth;
    FirebaseUser currentUser, cu;

    Button but;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notefication);
        Intent i=getIntent();
        but=findViewById(R.id.notebutton);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i3=new Intent(notefication.this,mainpage.class);
                startActivity(i3);
            }
        });
        final ArrayList<noteficationitem> exampleList = new ArrayList<>();
        final ArrayList<noteficationitem> exampleList1 = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getInstance().getCurrentUser();
        final String cuid = mAuth.getCurrentUser().getUid();
        final DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("not");
        mRecyclerView = findViewById(R.id.recycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mQueryCurrentUser = reff.orderByChild("receiver").equalTo(cuid);
        mQueryCurrentUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                exampleList.clear();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    final not n = dataSnapshot1.getValue(not.class);
                    if(n.getNum().equals("000000")){
                        exampleList.add(new noteficationitem("Someone request " + n.getBookname() + " " + n.getBooktype() + " from your collection, check your request tab. "));
                    }else {
                        if(n.getNum().equals("0")){
                            exampleList.add(new noteficationitem("Someone add " + n.getBookname() + " " + n.getBooktype() + " that in your WISH LIST, search in MAIN PAGE for it. "));
                        }else{
                            exampleList.add(new noteficationitem("Your request for " + n.getBookname() + " " + n.getBooktype() + " was accepted " + "you can pick it using the following number: " + n.getNum()));
                        }
                    }
                }

                mAdapter = new noteficationadapter(exampleList);

                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Auth = FirebaseAuth.getInstance();
        cu = mAuth.getInstance().getCurrentUser();
        final String cuidd = Auth.getCurrentUser().getUid();
        reff1 = FirebaseDatabase.getInstance().getReference().child("not");
        LayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(LayoutManager);
        q = reff1.orderByChild("sender").equalTo(cuidd);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    not n = dataSnapshot1.getValue(not.class);
                    if(n.getNum().equals("000000")){
                    }else {
                        if(n.getNum().equals("0")){
                        }else{
                            exampleList.add(new noteficationitem(  "You accept request for " + n.getBookname() + " " + n.getBooktype() + " you should put it with the following number: "+ n.getNum()));
                        }
                    }
                }
                Adapter = new noteficationadapter(exampleList);
                mRecyclerView.setAdapter(Adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }



}
