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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class adminmodfiy extends Fragment {


    public adminmodfiy() {
        // Required empty public constructor
    }
    Button mbb, mib;
    DatabaseReference reffb , reffi;
    EditText pbname, bname, piname, iname;
    String pbnamee, bnamee, pinamee, inamee;
    FirebaseDatabase  database;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_adminmodfiy, container, false);

        CardView c1mod,c2mod;

        c1mod=v.findViewById(R.id.c1mod);
        c2mod=v.findViewById(R.id.c2mod);
        c1mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final View customLayout = getLayoutInflater().inflate(R.layout.modfiybookdiloge, null);
                builder.setView(customLayout);
                final AlertDialog dialog = builder.create();
                dialog.show();
                mbb = customLayout.findViewById(R.id.mbb);
                pbname = customLayout.findViewById(R.id.pbname);
                bname = customLayout.findViewById(R.id.bname);
                mbb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try{
                            pbnamee = pbname.getText().toString();
                            bnamee = bname.getText().toString();
                            database = FirebaseDatabase.getInstance();
                            reffb = database.getReference("Book");
                            reffb.child(pbnamee).child("name").setValue(bnamee);

                            Toast.makeText(customLayout.getContext(),"Successfully..",Toast.LENGTH_LONG).show();
                        }catch(Exception e){
                         Toast.makeText(customLayout.getContext(),"Error!" + e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                        dialog.cancel();
                    }
                });


            }
        });
        c2mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final View customLayout = getLayoutInflater().inflate(R.layout.modfiyinsdiloge, null);
                builder.setView(customLayout);
                final AlertDialog dialog = builder.create();
                dialog.show();
                mib = customLayout.findViewById(R.id.mib);
                piname = customLayout.findViewById(R.id.piname);
                iname = customLayout.findViewById(R.id.iname);
                mib.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try{
                            pinamee = piname.getText().toString();
                            inamee = iname.getText().toString();
                            database = FirebaseDatabase.getInstance();
                            reffi = database.getReference("Instructor");
                            reffi.child(pinamee).child("name").setValue(inamee);
                          Toast.makeText(customLayout.getContext(),"Modify successfully",Toast.LENGTH_LONG).show();
                        }catch(Exception e){
                           Toast.makeText(customLayout.getContext(),"Error!" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        dialog.cancel();
                    }
                });

            }
        });
        return v;
    }



}
