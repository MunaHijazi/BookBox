package com.example.pp_ff;

import android.content.Context;
import android.graphics.Color;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class cardadapter extends RecyclerView.Adapter<cardadapter.ExampleViewHolder> implements Filterable{
    public ArrayList<cardviewitem> mExampleList;
    private ArrayList<cardviewitem> arrayListFiltered;
    CustomFilter filter;
    static String senderUserId, receiverUserId, requestedresourceuid,uid;
    static DatabaseReference bookrequsetref;
    private OnItemClickListener mListener;
    APIService apiService;
    String sender,receiver, bookname,booktype,ruid;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
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
        final public Button br;
        FirebaseAuth mAuth;
        FirebaseUser currentUser;
        long countpost;



        public ExampleViewHolder(final View itemView, final OnItemClickListener listener) {
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
            br = itemView.findViewById(R.id.RequestB);
            br.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mAuth = FirebaseAuth.getInstance();
                    currentUser = mAuth.getInstance().getCurrentUser();
                    senderUserId = mAuth.getCurrentUser().getUid();
                    receiverUserId = mTextView4.getText().toString();
                    Context c = view.getContext();
                    View cc = itemView;
                    requestedresourceuid = mTextView5.getText().toString();
                    uid = mTextView6.getText().toString();
                    DatabaseReference notreff = FirebaseDatabase.getInstance().getReference("not");
                    notreff.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                countpost = dataSnapshot.getChildrenCount();
                            }else{
                                countpost = 0;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    sendrequestt(senderUserId,receiverUserId,c,requestedresourceuid,uid,cc,countpost);
                }
            });
        }
    }

    public cardadapter(ArrayList<cardviewitem> exampleList) {
        mExampleList = exampleList;
        arrayListFiltered = new ArrayList<>(exampleList);
        this.arrayListFiltered = exampleList;

    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewtemp, parent, false);
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


    private void sendrequestt(final String senderUserId, final String receiverUserId, final Context c, final String requestedresourceuid,final String uid,final View cc,final long countpost){
        bookrequsetref = FirebaseDatabase.getInstance().getReference().child("sendrequest");

        if(receiverUserId.equals(senderUserId)){
           Toast.makeText(c,"This item is yours",Toast.LENGTH_LONG).show();
        }else{
            bookrequsetref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    boolean b = true;
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        sendrequest s = dataSnapshot1.getValue(sendrequest.class);
                        if (s.resourceId.equals(requestedresourceuid) && s.senderId.equals(senderUserId)) {
                           Toast.makeText(c, "You requested this already", Toast.LENGTH_LONG).show();
                            b=false;
                            break;
                        }
                    }
                    if(b){
                        sendrequest ssr = new sendrequest(requestedresourceuid,receiverUserId,senderUserId,"sent",uid);
                        bookrequsetref.child(uid).setValue(ssr).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(c,"Your request was sent successfully ",Toast.LENGTH_LONG).show();
                                Button br;
                                br = cc.findViewById(R.id.RequestB);
                                br.setText("REQUESTED");
                                br.setEnabled(false);
                                br.setBackgroundResource(R.drawable.cl);
                                br.setTextColor(Color.parseColor("#2196f3"));
                                TextView mTextView1;
                                TextView mTextView2;
                                TextView mTextView4;
                                TextView mTextView5;
                                TextView mTextView7;
                                mTextView1 = cc.findViewById(R.id.t11);
                                mTextView2 = cc.findViewById(R.id.t22);
                                mTextView4 = cc.findViewById(R.id.t44);
                                mTextView5 = cc.findViewById(R.id.t55);
                                mTextView7 = cc.findViewById(R.id.t77);
                                sender = mTextView4.getText().toString();
                                receiver = mTextView7.getText().toString();
                                bookname = mTextView1.getText().toString();
                                booktype = mTextView2.getText().toString();
                                ruid = mTextView5.getText().toString();
                                final DatabaseReference notreff = FirebaseDatabase.getInstance().getReference("not");
                                final String s = notreff.push().getKey();
                                not n = new not(s,receiver,sender,"000000",bookname,booktype,ruid);
                                notreff.child(s).setValue(n).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            FirebaseDatabase.getInstance().getReference("Tokens").child(sender).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    String usertoken = dataSnapshot.getValue(String.class);
                                                    sendNotifiaction("You got new notification",sender,c,usertoken);

                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
                                        }
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
    public Filter getFilter() {

        if(filter == null){
            filter = new CustomFilter(arrayListFiltered, this);
        }

        return filter;
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
                       Toast.makeText(c, "Failed!", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {
            }
        });
    }




}