package com.example.pp_ff;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class CustomAdapter extends ArrayAdapter<Resources> {
    Activity context;
    List<Resources> list;
    public CustomAdapter(Activity context , List<Resources> list) {
        super(context, R.layout.customlist,list);
        this.context = context;
        this.list = list;
    }

    @SuppressLint("MissingInflatedId")
    @NotNull
    @Override
    public View getView(int i, View view, @NotNull ViewGroup viewGroup) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint("ViewHolder") final View listViewItem = inflater.inflate(R.layout.customlist,null,true);
        final TextView title,sub1,sub2,ruid;
        Button delete;
        sub1 = listViewItem.findViewById(R.id.subtitle1);
        title = listViewItem.findViewById(R.id.titi);
        sub2 = listViewItem.findViewById(R.id.subtitle2);
        ruid = listViewItem.findViewById(R.id.ruid);
        delete = listViewItem.findViewById(R.id.d);
        final Resources r = list.get(i);
        title.setText(r.getname());
        sub1.setText(r.getYear());
        sub2.setText(r.getInstructor());
        ruid.setText(r.getRuid());
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference reff,reff1;
                final String uid = r.getRuid();
                Query mQueryCurrentUser;
                reff = FirebaseDatabase.getInstance().getReference("Resources");
                reff.child(uid).removeValue();
                reff1 = FirebaseDatabase.getInstance().getReference("sendrequest");
                mQueryCurrentUser = reff1.orderByChild("resourceId").equalTo(uid);
                mQueryCurrentUser.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(final DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                            sendrequest s = postSnapshot.getValue(sendrequest.class);
                            String c = Objects.requireNonNull(s).getUid();
                            reff1.child(c).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getContext(),"Deleted Successfully", Toast.LENGTH_LONG);
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });


        return listViewItem;
    }
}
