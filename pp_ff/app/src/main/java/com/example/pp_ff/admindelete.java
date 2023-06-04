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
//import com.muddzdev.styleabletoastlibrary.StyleableToast;


/**
 * A simple {@link Fragment} subclass.
 */
public class admindelete extends Fragment {


    public admindelete() {
        // Required empty public constructor
    }
    Button dbb, dib;
    EditText dbname, diname;
    String dbnamee, dinamee;
    DatabaseReference reffb , reffi;
    FirebaseDatabase database;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_admindelete, container, false);
        CardView c1del,c2del;
        c1del=v.findViewById(R.id.c1del);
        c2del=v.findViewById(R.id.c2del);
        c1del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final View customLayout = getLayoutInflater().inflate(R.layout.deletebookadmin, null);
                builder.setView(customLayout);
                final AlertDialog dialog = builder.create();
                dialog.show();
                dbb = customLayout.findViewById(R.id.dbb);
                dbname = customLayout.findViewById(R.id.dbname);
                dbb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try{
                            dbnamee = dbname.getText().toString();
                            database = FirebaseDatabase.getInstance();
                            reffb = database.getReference("Book").child(dbnamee);
                            reffb.removeValue();
                           Toast.makeText(customLayout.getContext(),"Removed Successfully",Toast.LENGTH_LONG).show();
                            dialog.cancel();
                        }catch(Exception e){
                          Toast.makeText(customLayout.getContext(),"Error!" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });


            }
        });
        c2del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final View customLayout = getLayoutInflater().inflate(R.layout.deleteinsdiloge, null);
                builder.setView(customLayout);
                final AlertDialog dialog = builder.create();
                dialog.show();
                dib = customLayout.findViewById(R.id.dib);
                diname = customLayout.findViewById(R.id.diname);
                dib.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dinamee = diname.getText().toString();
                        database = FirebaseDatabase.getInstance();
                        reffi = database.getReference("Instructor").child(dinamee);
                        reffi.removeValue();
                       Toast.makeText(customLayout.getContext(),"Removed successfully",Toast.LENGTH_LONG).show();
                        dialog.cancel();
                    }
                });

            }
        });


        return v;
    }

}
