package com.example.pp_ff;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.Objects;

public class profile extends AppCompatActivity {
    private TextView name,email,password;
    private FirebaseDatabase database;
    private DatabaseReference userRef, reff,reffBN,reffRN;
    private static final String USER="user";
    String emaili,userUid,userUid1;
    FirebaseAuth mAuth,mAuthBN,mAuthRN,auth;
    FirebaseUser currentUser,getCurrentUserBN,getCurrentUserRN,cu;
    EditText ppass, npass;
    TextView cb,rn,bname;
    String ppasss, npasss;
    Button mpassb;
    int countBN,countRN;
    private Query mQueryCurrentUser,mQueryCurrentUser1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getInstance().getCurrentUser();
        emaili = currentUser.getEmail();
        setContentView(R.layout.activity_profile);
        Intent i=getIntent();
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        cb = findViewById(R.id.collectioncount);
        rn = findViewById(R.id.rn);
        bname = findViewById(R.id.Bname);
        password = findViewById(R.id.password);
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference(USER);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    if(Objects.equals(ds.child("email").getValue(), emaili)){
                        name.setText(ds.child("name").getValue(String.class));
                        bname.setText(ds.child("name").getValue(String.class));
                        email.setText(ds.child("email").getValue(String.class));
                        password.setText(ds.child("password").getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Button b=findViewById(R.id.person);
        b.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingInflatedId")
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(profile.this);
                final View customLayout = getLayoutInflater().inflate(R.layout.personaldiloge, null);
                builder.setView(customLayout);
                final AlertDialog dialog = builder.create();
                dialog.show();
                ppass = customLayout.findViewById(R.id.ppass);
                npass = customLayout.findViewById(R.id.npass);
                mpassb = customLayout.findViewById(R.id.mpassb);
                mpassb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try{
                            ppasss=ppass.getText().toString();
                            npasss=npass.getText().toString();
                            database = FirebaseDatabase.getInstance();
                            reff = database.getReference("user");
                            if(ppasss.equals(password.getText().toString())){
                                if(!ppasss.equals(npasss)){
                                    auth = FirebaseAuth.getInstance();
                                    cu = auth.getInstance().getCurrentUser();
                                    cu.updatePassword(npasss).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                database = FirebaseDatabase.getInstance();
                                                reff = database.getReference("user");
                                                reff.child(currentUser.getUid()).child("password").setValue(npasss);
                                                dialog.cancel();
                                                Toast.makeText(profile.this, "", Toast.LENGTH_SHORT).show();Toast.makeText(customLayout.getContext(),"Password changed successfully",Toast.LENGTH_LONG).show();
                                            }else{
                                                dialog.cancel();
                                              Toast.makeText(customLayout.getContext(),task.getException().toString(),Toast.LENGTH_LONG).show();
                                            }

                                        }
                                    });

                                }else{
                                    Toast.makeText(customLayout.getContext(),"You wrote the same current password..",Toast.LENGTH_LONG).show();
                                }
                            }else{
                               Toast.makeText(customLayout.getContext(),"You wrote current password wrong",Toast.LENGTH_LONG).show();
                            }
                        }catch(Exception e){
                           Toast.makeText(customLayout.getContext(),"Error!" + e.getMessage(),Toast.LENGTH_LONG).show();
                            dialog.cancel();
                        }


                    }
                });
            }
        });


        mAuthBN = FirebaseAuth.getInstance();
        getCurrentUserBN = mAuthBN.getInstance().getCurrentUser();
        userUid = mAuthBN.getCurrentUser().getUid();
        reffBN = FirebaseDatabase.getInstance().getReference().child("Resources");
        mQueryCurrentUser = reffBN.orderByChild("uid").equalTo(userUid);
        mQueryCurrentUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    countBN = (int) dataSnapshot.getChildrenCount();
                    cb.setText(Integer.toString(countBN));
                }else{
                    cb.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mAuthRN = FirebaseAuth.getInstance();
        getCurrentUserRN = mAuthRN.getInstance().getCurrentUser();
        userUid1 = mAuthRN.getCurrentUser().getUid();
        reffRN = FirebaseDatabase.getInstance().getReference().child("sendrequest");
        mQueryCurrentUser1 = reffRN.orderByChild("recciverId").equalTo(userUid1);
        mQueryCurrentUser1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    countRN = (int) dataSnapshot.getChildrenCount();
                    rn.setText(Integer.toString(countRN));
                }else{
                    rn.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
