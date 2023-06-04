package com.example.pp_ff;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pp_ff.SendNotification.APIService;
import com.example.pp_ff.SendNotification.Client;
import com.example.pp_ff.SendNotification.Data;
import com.example.pp_ff.SendNotification.MyResponse;
import com.example.pp_ff.SendNotification.NotificationSender;
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
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class cardadapterRequest extends RecyclerView.Adapter<cardadapterRequest.ExampleViewHolder> implements Filterable {
    private final Context c;
    public ArrayList<cardviewitem> mExampleList;
    private ArrayList<cardviewitem> arrayListFiltered;
    CustomFilter11 filter;
    APIService apiService;
    private cardadapterRequest.OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(cardadapterRequest.OnItemClickListener listener){
        mListener = listener;
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView3;
        public TextView mTextView4;
        public TextView mTextView5;
        public TextView mTextView6;
        public TextView mTextView7;
        public TextView mTextView8;
        public Button deleteB;
        FirebaseDatabase database;
        public FirebaseUser fuser;
        Button bA;
        DatabaseReference reff,counterreff,notreff,rreff;
        String sender, receiver,booktype,bookname,ruid;
        not n;
        private Query mQueryCurrentUser,q;




        public ExampleViewHolder(final View itemView,final OnItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.img1);
            mTextView1 = itemView.findViewById(R.id.t11);
            mTextView2 = itemView.findViewById(R.id.t22);
            mTextView3 = itemView.findViewById(R.id.t33);
            mTextView4 = itemView.findViewById(R.id.t44);
            mTextView5 = itemView.findViewById(R.id.t55);
            mTextView6 = itemView.findViewById(R.id.t66);
            mTextView7 = itemView.findViewById(R.id.t77);
            mTextView8 = itemView.findViewById(R.id.t88);
            bA = itemView.findViewById(R.id.AcceptB);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
            deleteB = itemView.findViewById(R.id.delete);
            deleteB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String r = mTextView6.getText().toString().trim();
                    database = FirebaseDatabase.getInstance();
                    reff = database.getReference("sendrequest").child(r);
                    reff.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(itemView.getContext(),"Deleted successfully" ,Toast.LENGTH_LONG).show();
                                notifyDataSetChanged();
                            }else{
                                Toast.makeText(itemView.getContext(),"Delete Failed" + task.getException().toString(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            });


            fuser = FirebaseAuth.getInstance().getCurrentUser();
            bA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    sender = mTextView4.getText().toString();
                    receiver = mTextView7.getText().toString();
                    bookname = mTextView1.getText().toString();
                    booktype = mTextView2.getText().toString();
                    ruid = mTextView5.getText().toString();
                    notreff = FirebaseDatabase.getInstance().getReference("not");
                    counterreff = FirebaseDatabase.getInstance().getReference("Counter");
                    counterreff.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            try {
                                mExampleList.clear();
                                notifyDataSetChanged();
                                final String num = Objects.requireNonNull(dataSnapshot.getValue()).toString();
                                final String s = notreff.push().getKey();
                                n = new not(s, sender, receiver, num, bookname, booktype, ruid);
                                notreff.child(s).setValue(n).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            FirebaseDatabase.getInstance().getReference("Tokens").child(sender).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    String usertoken = dataSnapshot.getValue(String.class);
                                                    sendNotifiaction("You got new notification", receiver, c, usertoken);
                                                    SweetAlertDialog dialog = new SweetAlertDialog(itemView.getContext(), SweetAlertDialog.CUSTOM_IMAGE_TYPE);
                                                    dialog.setTitleText("You accept the requset").setCustomImage(R.drawable.d)
                                                            .setContentText("Check your notification for book number")
                                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                                @Override
                                                                public void onClick(SweetAlertDialog sDialog) {
                                                                    sDialog.dismissWithAnimation();
                                                                }
                                                            }).setConfirmButton("Go", new SweetAlertDialog.OnSweetClickListener() {
                                                                @Override
                                                                public void onClick(SweetAlertDialog sDialog) {
                                                                    Intent i6 = new Intent(itemView.getContext(), notefication.class);
                                                                    c.startActivity(i6);
                                                                }
                                                            }).setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                                                                @Override
                                                                public void onClick(SweetAlertDialog sDialog) {
                                                                    sDialog.dismissWithAnimation();
                                                                }
                                                            }).show();
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
                                            FirebaseDatabase.getInstance().getReference("Tokens").child(receiver).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    String usertoken = dataSnapshot.getValue(String.class);
                                                    sendNotifiaction("You got new notification", receiver, c, usertoken);
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
                                            counterreff.setValue(Integer.parseInt(num) + 1);
                                            rreff = FirebaseDatabase.getInstance().getReference("Resources");
                                            rreff.child(ruid).removeValue();
                                            database = FirebaseDatabase.getInstance();
                                            reff = database.getReference("sendrequest");
                                            mQueryCurrentUser = reff.orderByChild("resourceId").equalTo(ruid);
                                            mQueryCurrentUser.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                    for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                                        sendrequest s = postSnapshot.getValue(sendrequest.class);
                                                        String c = Objects.requireNonNull(s).getUid();
                                                        reff.child(c).removeValue();
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });

                                        }
                                    }
                                });

                            }catch (NullPointerException ex){
                                ex.printStackTrace();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            });

        }
    }




    public cardadapterRequest(Context c, ArrayList<cardviewitem> exampleList) {
        mExampleList = exampleList;
        this.arrayListFiltered = exampleList;
        this.c = c;

    }


    public void sendNotifiaction(final String message, final String userid, final Context c, final String tt){
        final FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        Data data = new Data(fuser.getUid(), R.drawable.bbb, message, "New Notification", userid);
        NotificationSender sender = new NotificationSender(data, tt);
        apiService.sendNotification(sender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (response.code() == 200){
                    if (response.body().success != 1){
                        Toast.makeText(c, "Notification Failed!",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {
            }
        });
    }


    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewrequset, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        cardviewitem currentItem = mExampleList.get(position);
        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mTextView1.setText(currentItem.getText1());
        holder.mTextView2.setText(currentItem.getText2());
        holder.mTextView3.setText(currentItem.getText3());
        holder.mTextView4.setText(currentItem.getText4());
        holder.mTextView5.setText(currentItem.getText5());
        holder.mTextView6.setText(currentItem.getText6());
        holder.mTextView7.setText(currentItem.getText7());
        holder.mTextView8.setText(currentItem.getText8());
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }


    @Override
    public Filter getFilter() {
        if(!arrayListFiltered.isEmpty()){
            if(filter == null){
                filter = new CustomFilter11(arrayListFiltered, this);
            }

        }

        return filter;
    }
}