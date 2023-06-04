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
import java.util.Objects;

public class MyRequset extends AppCompatActivity {

    private Query mQueryCurrentUser,q;
    DatabaseReference reff,reff1,r;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    final ArrayList<myrequsetitem> exampleList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_requset);
        Button bad;
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getInstance().getCurrentUser();
        final String cuid = mAuth.getCurrentUser().getUid();
        reff = FirebaseDatabase.getInstance().getReference().child("sendrequest");
        reff1 = FirebaseDatabase.getInstance().getReference().child("Resources");
        q = reff.orderByChild("senderId").equalTo(cuid);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                exampleList.clear();
                for(final DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    sendrequest s = postSnapshot.getValue(sendrequest.class);
                    final String suid = s.uid;
                    reff1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                                if(Objects.equals(dataSnapshot1.child("ruid").getValue(), postSnapshot.child("resourceId").getValue())){
                                    final Resources r = dataSnapshot1.getValue(Resources.class);
                                    if(r.getType().equals("Exams")){
                                        exampleList.add(new myrequsetitem(R.drawable.exam, r.getname(), r.getType(),r.getYear(), r.getInstructor(),suid));
                                    }else{
                                        if(r.getType().equals("Summaries")){
                                            exampleList.add(new myrequsetitem(R.drawable.summ, r.getname(), r.getType(),r.getYear(), r.getInstructor(),suid));
                                        }else{
                                            if(r.getType().equals("Book")){
                                                exampleList.add(new myrequsetitem(R.drawable.bookmark, r.getname(), r.getType(),r.getYear(), r.getInstructor(),suid));
                                            }
                                        }
                                    }
                                }
                            }
                            mAdapter = new myrequsetadapter(exampleList);
                            mRecyclerView.setAdapter(mAdapter);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Intent i=getIntent();
        bad=findViewById(R.id.requsetpage);
        bad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MyRequset.this,mainpage.class);
                startActivity(i);
            }
        });
    }
}
