package com.example.pp_ff;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.pp_ff.SendNotification.APIService;
import com.example.pp_ff.SendNotification.Client;
import com.example.pp_ff.SendNotification.Data;
import com.example.pp_ff.SendNotification.MyResponse;
import com.example.pp_ff.SendNotification.NotificationSender;
import com.example.pp_ff.SendNotification.Token;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class mainpage extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Button  badd;
    Spinner sbname, sinsname, stype, syear;
    ChildEventListener listener;
    ArrayAdapter <String> bookadapter;
    ArrayList<String> bookspinnerDataList;
    ArrayAdapter <String> insadapter;
    ArrayList<String> insspinnerDataList;
    DatabaseReference reff, dbreff;
    FirebaseUser currentUser;
    Resources r;
    FirebaseAuth mAuth;
    APIService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        checkConnection();
        Intent i=getIntent();
        dbreff = FirebaseDatabase.getInstance().getReference().child("Resources");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TabLayout tabs = findViewById(R.id.tabs);
        TabLayout.Tab firstTab = tabs.newTab();
        firstTab.setText("Main");

        tabs.addTab(firstTab,0);
        TabLayout.Tab secandTab = tabs.newTab();
        secandTab.setText("Request");
        tabs.addTab(secandTab,1);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Main()).commit();
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==1){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new RequsetTab()).commit();
                }
                else {  getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Main()).commit();}
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView nav_view = findViewById(R.id.nav_view);
        View headerView = nav_view.getHeaderView(0);
        final TextView navUsername = headerView.findViewById(R.id.username);
        final String userid =  FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference refff = FirebaseDatabase.getInstance().getReference("user");
        refff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (Objects.equals(ds.child("uid").getValue(), userid)) {
                        navUsername.setText(ds.child("name").getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch(menuItem.getItemId())
                {
                    case R.id.about:
                        Intent i6=new Intent(mainpage.this,aboutapp.class);
                        startActivity(i6);
                        break;
                    case R.id.contact:
                        Intent i3=new Intent(mainpage.this,contactus.class);
                        startActivity(i3);
                        break;
                    case R.id.collction:
                        Intent i=new Intent(mainpage.this,collction.class);
                        startActivity(i);
                        break;
                    case R.id.profile:
                        Intent i2=new Intent(mainpage.this,profile.class);
                        startActivity(i2);
                        break;
                    case R.id.notefication:
                        Intent i5=new Intent(mainpage.this,notefication.class);
                        startActivity(i5);
                        break;
                    case R.id.myrequset:
                        Intent i10=new Intent(mainpage.this,MyRequset.class);
                        startActivity(i10);
                        break;
                    case R.id.wishlist:
                        Intent i11=new Intent(mainpage.this,wishlist.class);
                        startActivity(i11);
                        break;
                    case R.id.logout:
                        FirebaseAuth.getInstance().signOut();
                        finish();
                        Intent i4=new Intent(mainpage.this,MainActivity.class);
                        startActivity(i4);

                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        toolbar .setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.add:
                        AlertDialog.Builder builder = new AlertDialog.Builder(mainpage.this);
                        final View customLayout = getLayoutInflater().inflate(R.layout.diloge, null);
                        builder.setView(customLayout);
                        sbname = customLayout.findViewById(R.id.sbname);
                        sinsname = customLayout.findViewById(R.id.sinsname);
                        stype = customLayout.findViewById(R.id.stype);
                        syear = customLayout.findViewById(R.id.syear);
                        stype.setSelection(0);
                        syear.setSelection(0);
                        bookspinnerDataList = new ArrayList<>();
                        bookadapter = new ArrayAdapter<String>(mainpage.this, R.layout.support_simple_spinner_dropdown_item,bookspinnerDataList);
                        sbname.setAdapter(bookadapter);
                        bookdata();
                        insspinnerDataList = new ArrayList<>();
                        insadapter = new ArrayAdapter<String>(mainpage.this, R.layout.support_simple_spinner_dropdown_item,insspinnerDataList);
                        sinsname.setAdapter(insadapter);
                        insdata();
                        final AlertDialog dialog = builder.create();
                        dialog.show();
                        badd = customLayout.findViewById(R.id.badd);

                        badd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mAuth = FirebaseAuth.getInstance();
                                currentUser = mAuth.getInstance().getCurrentUser();
                                final String n = sbname.getSelectedItem().toString().trim();
                                String i = sinsname.getSelectedItem().toString().trim();
                                final String t = stype.getSelectedItem().toString().trim();
                                String y = syear.getSelectedItem().toString().trim();
                                String uid = currentUser.getUid();
                                String p = dbreff.push().getKey();
                                if(n.equals("Select Name")){
                                  Toast.makeText(mainpage.this,"Please select subject name ",Toast.LENGTH_LONG).show();
                                }else{
                                    if(t.equals("Select Type")){
                                       Toast.makeText(mainpage.this,"Please select type ",Toast.LENGTH_LONG).show();
                                    }else{
                                        if(y.equals("Select Year")){
                                           Toast.makeText(mainpage.this,"Please select year ",Toast.LENGTH_LONG).show();
                                        }else{
                                            if(i.equals("Select Name")){
                                                Toast.makeText(mainpage.this,"Please select instructor name ",Toast.LENGTH_LONG).show();
                                            }else{
                                                r = new Resources(n,uid,t,y,i,p);
                                                dbreff.child(p).setValue(r);
                                                dialog.cancel();
                                                DatabaseReference wreff;
                                                wreff = FirebaseDatabase.getInstance().getReference().child("WishList");
                                                Query wq;
                                                wq = wreff.orderByChild("name").equalTo(n);
                                                wq.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        for(final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                                            final wishitem w = postSnapshot.getValue(wishitem.class);
                                                            assert w != null;
                                                            String cuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                                            DatabaseReference notreff = FirebaseDatabase.getInstance().getReference().child("not");
                                                            final String s = notreff.push().getKey();
                                                            not n = new not(s,cuid,w.getUID(),"0",w.getName(),w.getType(),"0");
                                                            notreff.child(s).setValue(n).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if(task.isSuccessful()){
                                                                        FirebaseDatabase.getInstance().getReference("Tokens").child(w.getUID()).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                            @Override
                                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                String usertoken = dataSnapshot.getValue(String.class);
                                                                                if(w.getType().equals(t)){
                                                                                    sendNotifiaction("You got new notification", w.getUID(), usertoken);
                                                                                }
                                                                            }

                                                                            @Override
                                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                            }
                                                                        });
                                                                    }
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

                                    }
                                }

                            }
                        });

                }

                UpdateToken();
                return false;
            }
        });


    }



    public void bookdata(){
        bookspinnerDataList.add("Select Name");
        reff= FirebaseDatabase.getInstance().getReference().child("Book");
        listener = reff.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                for(DataSnapshot item:dataSnapshot.getChildren()){
                    bookspinnerDataList.add(item.getValue().toString());
                }
                bookadapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                for(DataSnapshot item:dataSnapshot.getChildren()) {
                    bookspinnerDataList.add(item.getValue().toString());
                }
                bookadapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void insdata(){
        insspinnerDataList.add("Select Name");
        reff= FirebaseDatabase.getInstance().getReference().child("Instructor");
        listener = reff.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                for(DataSnapshot item:dataSnapshot.getChildren()){
                    insspinnerDataList.add(item.getValue().toString());
                }
                insadapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                for(DataSnapshot item:dataSnapshot.getChildren()){
                    insspinnerDataList.add(item.getValue().toString());
                }
                insadapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void UpdateToken(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        Token token = new Token(refreshToken);
        FirebaseDatabase.getInstance().getReference("Tokens").child(firebaseUser.getUid()).setValue(token);
    }

    public void checkConnection(){
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        if(null==activeNetwork){
            SweetAlertDialog dialog = new SweetAlertDialog(mainpage.this,SweetAlertDialog.CUSTOM_IMAGE_TYPE);
            dialog.setCustomImage(R.drawable.wifi).setTitleText("No Internet Connection").setContentText("Please check your internet connection").show();
            Button btn = (Button) dialog.findViewById(R.id.confirm_button);
            btn.setBackgroundColor(ContextCompat.getColor(mainpage.this,R.color.colorPrimary));

        }
    }

    public void sendNotifiaction(final String message, final String userid, final String tt){
        final FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        Data data = new Data(fuser.getUid(), R.drawable.bbb, message, "New Notification", userid);
        NotificationSender sender = new NotificationSender(data, tt);
        apiService.sendNotification(sender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (response.code() == 200){
                    if (response.body().success != 1){

                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {
            }
        });
    }


}