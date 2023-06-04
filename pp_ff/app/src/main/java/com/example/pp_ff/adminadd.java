package com.example.pp_ff;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//import com.muddzdev.styleabletoastlibrary.StyleableToast;


public class adminadd extends Fragment {
    public adminadd() {
        // Required empty public constructor
    }
    CardView c1add,c2add;
    Button bb, bins;
    EditText bname,insname;
    Book book;
    DatabaseReference reff;
    FirebaseAuth auth;
    Instructor ins;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_adminadd, container, false);
        c1add=v.findViewById(R.id.c1add);
        c2add=v.findViewById(R.id.c2add);
        c1add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final View customLayout = getLayoutInflater().inflate(R.layout.addbookdiloge, null);
                builder.setView(customLayout);
                final AlertDialog dialog = builder.create();
                dialog.show();
                book = new Book();
                bb = customLayout.findViewById(R.id.bookb);
                bname = customLayout.findViewById(R.id.bname);
                reff= FirebaseDatabase.getInstance().getReference().child("Book");
                auth = FirebaseAuth.getInstance();
                bb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String n = bname.getText().toString().trim();
                        if(n.isEmpty()){
                            bname.setError("Please enter book name");
                            bname.requestFocus();
                        }else{
                            book.setName(bname.getText().toString().trim());
                            try{
                                reff.child(book.getName()).setValue(book);
                              Toast.makeText(customLayout.getContext(),"Subject added successfully",Toast.LENGTH_LONG).show();
                            }catch(Exception e){
                                Toast.makeText(customLayout.getContext(),"Error!" + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                            dialog.cancel();
                        }}
                });

            }
        });


        c2add.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingInflatedId")
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                final View customLayout = getLayoutInflater().inflate(R.layout.addinstrctordiloge, null);
                builder.setView(customLayout);
                final AlertDialog dialog = builder.create();
                dialog.show();
                ins = new Instructor();
                bins = customLayout.findViewById(R.id.bins);
                insname = customLayout.findViewById(R.id.insname);
                reff= FirebaseDatabase.getInstance().getReference().child("Instructor");
                auth = FirebaseAuth.getInstance();
                bins.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String n = insname.getText().toString().trim();
                        if(n.isEmpty()){
                            insname.setError("Please enter instructor name");
                            insname.requestFocus();
                        }else{
                            ins.setName(insname.getText().toString().trim());
                            try{
                                reff.child(ins.getName()).setValue(ins);
                              Toast.makeText(customLayout.getContext(),"Instructor added successfully..",Toast.LENGTH_LONG).show();
                            }catch(Exception e){
                                Toast.makeText(customLayout.getContext(),"Error!" + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                            dialog.cancel();
                        }}
                });

            }
        });
        return v;
    }

}
