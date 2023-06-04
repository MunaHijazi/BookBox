package com.example.pp_ff;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class wishlist extends AppCompatActivity {
    FloatingActionButton b;
    ArrayList<String> bookspinnerDataList;
    DatabaseReference reff;
    ChildEventListener listener;
    ArrayAdapter<String> bookadapter;
    Spinner sbname,tname;
    Button mbb;
    DatabaseReference dbreff;
    FirebaseUser currentUser;
    Resources r;
    FirebaseAuth mAuth;
    wishitem w;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        Intent i=getIntent();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getInstance().getCurrentUser();
        final String cuid = mAuth.getCurrentUser().getUid();
        setUpRecyclerView(cuid);
        b=findViewById(R.id.floting) ;


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(wishlist.this);
                final View customLayout = getLayoutInflater().inflate(R.layout.wishdiloge, null);
                sbname = customLayout.findViewById(R.id.sbname);
                tname = customLayout.findViewById(R.id.tname);
                tname.setSelection(0);
                bookspinnerDataList = new ArrayList<>();
                bookadapter = new ArrayAdapter<String>(wishlist.this, R.layout.support_simple_spinner_dropdown_item,bookspinnerDataList);
                sbname.setAdapter(bookadapter);
                bookdata();
                builder.setView(customLayout);
                final AlertDialog dialog = builder.create();
                dialog.show();
                mbb = customLayout.findViewById(R.id.mbb);
                mbb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mAuth = FirebaseAuth.getInstance();
                        currentUser = mAuth.getInstance().getCurrentUser();
                        String n = sbname.getSelectedItem().toString().trim();
                        String t = tname.getSelectedItem().toString().trim();
                        String uid = currentUser.getUid();
                        if(n.equals("Select Name")){
                            Toast.makeText(wishlist.this,"Please select book name ",Toast.LENGTH_LONG).show();
                        }else {
                            if (t.equals("Select Type")) {
                                Toast.makeText(wishlist.this, "Please select type ", Toast.LENGTH_LONG).show();
                            } else {
                                dbreff = FirebaseDatabase.getInstance().getReference("WishList");
                                String p = dbreff.push().getKey();
                                w = new wishitem(n,t,p,uid);
                                dbreff.child(p).setValue(w);
                                dialog.cancel();
                            }
                        }
                    }
                });
            }
        });


    }

    private void setUpRecyclerView(String cuid) {
        DatabaseReference reff;
        reff = FirebaseDatabase.getInstance().getReference("WishList");
        Query q;
        q = reff.orderByChild("uid").equalTo(cuid);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        final ArrayList<wishitem> exampleList = new ArrayList<>();
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                exampleList.clear();
                for(final DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    wishitem w = postSnapshot.getValue(wishitem.class);
                    exampleList.add(new wishitem( w.getName(),w.getType(),w.getWID(),w.getUID()));
                }
                final wishadapter recyclerViewAdapter = new wishadapter(wishlist.this, exampleList);
                recyclerView.setAdapter(recyclerViewAdapter);
                SwipeableRecyclerViewTouchListener swipeTouchListener = new SwipeableRecyclerViewTouchListener(recyclerView, new SwipeableRecyclerViewTouchListener.SwipeListener() {
                    @Override
                    public boolean canSwipeLeft(int position) {
                        //enable/disable left swipe on checkbox base else use true/false
                        return true;
                    }

                    @Override
                    public boolean canSwipeRight(int position) {
                        //enable/disable right swipe on checkbox base else use true/false
                        return true;
                    }

                    @Override
                    public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
                        //on cardview swipe left dismiss update adapter
                        onCardViewDismiss(reverseSortedPositions, exampleList, recyclerViewAdapter);
                    }

                    @Override
                    public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                        //on cardview swipe right dismiss update adapter
                        onCardViewDismiss(reverseSortedPositions, exampleList, recyclerViewAdapter);
                    }
                });

                //add item touch listener to recycler view
                recyclerView.addOnItemTouchListener(swipeTouchListener);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }


    private void onCardViewDismiss(int[] reverseSortedPositions, ArrayList<wishitem> stringArrayList, wishadapter recyclerViewAdapter) {
        for (int position : reverseSortedPositions) {
            DatabaseReference wreff = FirebaseDatabase.getInstance().getReference("WishList");
            wreff.child(stringArrayList.get(position).getWID()).removeValue();
            stringArrayList.remove(position);
            recyclerViewAdapter.notifyItemRemoved(position);
        }
        Toast.makeText(this, getResources().getString(R.string.card_view_dismiss_success), Toast.LENGTH_SHORT).show();
        recyclerViewAdapter.notifyDataSetChanged();
    }



    public void bookdata(){
        bookspinnerDataList.add("Select Name");
        reff= FirebaseDatabase.getInstance().getReference().child("Book");
        listener = reff.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                for(DataSnapshot item:dataSnapshot.getChildren()){
                    bookspinnerDataList.add(item.getValue().toString());
                }
                bookadapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                for(DataSnapshot item:dataSnapshot.getChildren()) {
                    bookspinnerDataList.add(item.getValue().toString());
                }
                bookadapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}


