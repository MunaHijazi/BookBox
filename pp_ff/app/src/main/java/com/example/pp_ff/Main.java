package com.example.pp_ff;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.pedant.SweetAlert.SweetAlertDialog;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.zip.Inflater;


/**
 * A simple {@link Fragment} subclass.
 */
public class Main extends Fragment {

    ArrayList<cardviewitem> exampleList = new ArrayList<>();
    public Main() { }
    private RecyclerView mRecyclerView;
    private cardadapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    DatabaseReference mDatabase,reff;
    FirebaseAuth mAuth;
    private Query firebaseSearchQuery;
    FirebaseUser currentUser;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v=inflater.inflate(R.layout.fragment_contact, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference("Resources");
        mRecyclerView =v.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                exampleList.clear();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Resources r = dataSnapshot1.getValue(Resources.class);
                    String p = mDatabase.push().getKey();
                    mAuth = FirebaseAuth.getInstance();
                    currentUser = mAuth.getInstance().getCurrentUser();
                    String userid = mAuth.getCurrentUser().getUid();
                    if(r.getType().equals("Exams")){
                        exampleList.add(new cardviewitem(R.drawable.exam, r.getname(), r.getType(),r.getYear(),r.getuid(),r.getRuid(),p,userid,r.getInstructor()));
                    }else{
                        if(r.getType().equals("Summaries")){
                            exampleList.add(new cardviewitem(R.drawable.summ, r.getname(), r.getType(),r.getYear(),r.getuid(),r.getRuid(),p,userid,r.getInstructor()));
                        }else{
                            if(r.getType().equals("Book")){
                                exampleList.add(new cardviewitem(R.drawable.bookmark, r.getname(), r.getType(),r.getYear(),r.getuid(),r.getRuid(),p,userid,r.getInstructor()));
                            }
                        }
                    }


                }
                mAdapter = new cardadapter(exampleList);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.setOnItemClickListener(new cardadapter.OnItemClickListener() {
                    @SuppressLint("MissingInflatedId")
                    @Override
                    public void onItemClick(int position) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        final View customLayout = getLayoutInflater().inflate(R.layout.dialogcv, null);
                        builder.setView(customLayout);
                        TextView stv,ttv,ytv,itv;
                        ImageView iv;
                        Button b = customLayout.findViewById(R.id.bclose);
                        stv = customLayout.findViewById(R.id.stv);
                        ttv = customLayout.findViewById(R.id.ttv);
                        ytv = customLayout.findViewById(R.id.ytv);
                        itv = customLayout.findViewById(R.id.instv);
                        iv = customLayout.findViewById(R.id.iv);
                        stv.setText(exampleList.get(position).getText1());
                        ttv.setText(exampleList.get(position).getText2());
                        ytv.setText(exampleList.get(position).getText3());
                        itv.setText(exampleList.get(position).getText8());
                        iv.setImageResource(R.drawable.more);
                        final AlertDialog dialog = builder.create();
                        dialog.show();
                        b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.cancel();
                            }
                        });
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        return v;





    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(s.isEmpty()){

                }else{
                    mAdapter.getFilter().filter(s);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(s.isEmpty()){

                }else{
                    if(mAdapter!=null){
                        mAdapter.getFilter().filter(s);
                    }

                }
                return false;
            }
        });

        super.onCreateOptionsMenu(menu,inflater);
    }





}



