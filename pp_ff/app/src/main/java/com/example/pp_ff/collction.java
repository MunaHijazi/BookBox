package com.example.pp_ff;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class collction extends AppCompatActivity {
    private DatabaseReference table;
    ListView list1,list2,list3;
    List<Resources> list11,list22,list33;
    FirebaseDatabase db;
    private Query mQueryCurrentUser;
    DatabaseReference reff;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collction);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getInstance().getCurrentUser();
        String cuid = mAuth.getCurrentUser().getUid();
        reff = FirebaseDatabase.getInstance().getReference().child("Resources");
        mQueryCurrentUser = reff.orderByChild("uid").equalTo(cuid);


        Intent i=getIntent();
        final Button b1 = findViewById(R.id.b1);
        final Button b2 = findViewById(R.id.b2);
        final Button b3 = findViewById(R.id.b3);
        final Button b4 = findViewById(R.id.left);
        final LinearLayout l1 = findViewById(R.id.ex1);
        final LinearLayout l2 = findViewById(R.id.ex2);
        final LinearLayout l3 = findViewById(R.id.ex3);




        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(l1.getVisibility()==View.GONE) {
                    expand(l1,  300);
                    b1.setBackgroundResource(R.drawable.drop);
                }
                else if (l1.getHeight()==0){
                    expand(l1,  300);
                    b1.setBackgroundResource(R.drawable.drop);
                }
                else { collapse(l1,  0);
                    b1.setBackgroundResource(R.drawable.arrow);}
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(l2.getVisibility()==View.GONE) {
                    expand(l2,  300);
                    b2.setBackgroundResource(R.drawable.drop);
                }
                else if (l2.getHeight()==0){
                    expand(l2,  300);
                    b2.setBackgroundResource(R.drawable.drop);
                }
                else { collapse(l2,  0);
                    b2.setBackgroundResource(R.drawable.arrow);}
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(l3.getVisibility()==View.GONE) {
                    expand(l3,  300);
                    b3.setBackgroundResource(R.drawable.drop);
                }
                else if (l3.getHeight()==0){
                    expand(l3,  300);
                    b3.setBackgroundResource(R.drawable.drop);
                }
                else { collapse(l3,  0);
                    b3.setBackgroundResource(R.drawable.arrow);}
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(collction.this,mainpage.class);
                startActivity(i);

            }
        });

        list2=(ListView)findViewById(R.id.list2);
        list3=(ListView)findViewById(R.id.list3);
        list1 = (ListView) findViewById(R.id.list1);
        // db = FirebaseDatabase.getInstance();
        // table=db.getReference("Resources");
        list11 = new ArrayList<Resources>();
        list22 = new ArrayList<Resources>();
        list33 = new ArrayList<Resources>();
        onStart();


    }

    public static void expand(final View v, int targetHeight) {

        final int prevHeight  = v.getHeight();

        v.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.start();
        valueAnimator.setDuration(1000);
    }

    public static void collapse(final View v,  int targetHeight) {

        int prevHeight  = v.getHeight();

        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.start();
        valueAnimator.setDuration(1000);
    }

    protected void onStart() {

        super.onStart();

        //DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Resources");
        mQueryCurrentUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list11.clear();
                list22.clear();
                list33.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Resources r = postSnapshot.getValue(Resources.class);
                    if(r.getType().equals("Book")){
                        list11.add(r); }
                    if(r.getType().equals("Exams")){
                        list22.add(r);}
                    if(r.getType().equals("Summaries")){
                        list33.add(r);;}

                }
                CustomAdapter adapter1= new CustomAdapter(collction.this,list11);
                list1.setAdapter(adapter1);
                CustomAdapter adapter2= new CustomAdapter(collction.this,list22);
                list2.setAdapter(adapter2);
                CustomAdapter adapter3= new CustomAdapter(collction.this,list33);
                list3.setAdapter(adapter3);


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }




}
