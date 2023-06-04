package com.example.pp_ff;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class wishadapter extends RecyclerView.Adapter<wishadapter.ItemViewHolder> {




    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        Button b;

        ItemViewHolder(final View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.card_view_label);
            textView2 = (TextView) view.findViewById(R.id.card_view_label2);
            textView3 = (TextView) view.findViewById(R.id.WID);
            textView4 = (TextView) view.findViewById(R.id.UID);
            b = view.findViewById(R.id.Available);

        }
    }


    private ArrayList<wishitem> stringArrayList;

    private Context context;

    public wishadapter(Context context, ArrayList<wishitem> stringArrayList) {
        this.stringArrayList = stringArrayList;
        this.context = context;
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wishtemp, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder viewHolder, int i) {
        wishitem currentItem = stringArrayList.get(i);
        viewHolder.textView.setText(currentItem.getName());
        viewHolder.textView2.setText(currentItem.getType());
        viewHolder.textView3.setText(currentItem.getWID());
        viewHolder.textView4.setText(currentItem.getUID());
        DatabaseReference reff;
        reff = FirebaseDatabase.getInstance().getReference("Resources");

        Query q = reff.orderByChild("name").equalTo(viewHolder.textView.getText().toString().trim());
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Resources r= postSnapshot.getValue(Resources.class);
                    assert r != null;
                    if(r.getType().toUpperCase().equals(viewHolder.textView2.getText().toString().trim().toUpperCase())){
                        viewHolder.b.setText("Available");
                        viewHolder.b.setEnabled(true);
                        viewHolder.b.setBackgroundResource(R.drawable.rec2ground);
                        viewHolder.b.setTextColor(Color.parseColor("#FFFFFF"));

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        viewHolder.b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewHolder.b.getText().toString().equals("Available")){
                   Toast.makeText(view.getContext(), "Search in MAIN TAB", Toast.LENGTH_LONG).show();
                }

            }
        });

    }


    @Override
    public int getItemCount() {
        return (null != stringArrayList ? stringArrayList.size() : 0);


    }
}
