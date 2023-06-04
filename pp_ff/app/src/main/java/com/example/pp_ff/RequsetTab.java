package com.example.pp_ff;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class RequsetTab extends Fragment {

    public RequsetTab() {
        // Required empty public constructor
    }

    private RecyclerView mRecyclerView;
    private cardadapterRequest mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Query mQueryCurrentUser,q;
    DatabaseReference reff,reff1,r;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_about, container, false);
        final ArrayList<cardviewitem> exampleList = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getInstance().getCurrentUser();
        final String cuid = mAuth.getCurrentUser().getUid();
        reff = FirebaseDatabase.getInstance().getReference().child("sendrequest");
        reff1 = FirebaseDatabase.getInstance().getReference().child("Resources");
        mRecyclerView = v.findViewById(R.id.recycler_view2);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mQueryCurrentUser = reff.orderByChild("recciverId").equalTo(cuid);
        mQueryCurrentUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                exampleList.clear();
                for(final DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    reff1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                                if(Objects.equals(dataSnapshot1.child("ruid").getValue(), postSnapshot.child("resourceId").getValue())){
                                    final Resources r = dataSnapshot1.getValue(Resources.class);
                                    if(r.getType().equals("Exams")){
                                        exampleList.add(new cardviewitem(R.drawable.exam, r.getname(), r.getType(),r.getYear(),r.getuid(),r.getRuid(),postSnapshot.child("uid").getValue().toString(),postSnapshot.child("senderId").getValue().toString(),r.getInstructor()));
                                    }else{
                                        if(r.getType().equals("Summaries")){
                                            exampleList.add(new cardviewitem(R.drawable.summ, r.getname(), r.getType(),r.getYear(),r.getuid(),r.getRuid(),postSnapshot.child("uid").getValue().toString(),postSnapshot.child("senderId").getValue().toString(),r.getInstructor()));
                                        }else{
                                            if(r.getType().equals("Book")){
                                                exampleList.add(new cardviewitem(R.drawable.bookmark, r.getname(), r.getType(),r.getYear(),r.getuid(),r.getRuid(),postSnapshot.child("uid").getValue().toString(),postSnapshot.child("senderId").getValue().toString(),r.getInstructor()));
                                            }
                                        }
                                    }
                                }
                                mAdapter = new cardadapterRequest(getActivity(),exampleList);
                                mRecyclerView.setAdapter(mAdapter);
                                mAdapter.setOnItemClickListener(new cardadapterRequest.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(int position) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                        final View customLayout = getLayoutInflater().inflate(R.layout.dialogcv, null);
                                        builder.setView(customLayout);
                                        TextView stv,ttv,ytv,itv;
                                        ImageView iv;
                                        iv = customLayout.findViewById(R.id.iv);
                                        Button b = customLayout.findViewById(R.id.bclose);
                                        stv = customLayout.findViewById(R.id.stv);
                                        ttv = customLayout.findViewById(R.id.ttv);
                                        ytv = customLayout.findViewById(R.id.ytv);
                                        itv = customLayout.findViewById(R.id.instv);
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