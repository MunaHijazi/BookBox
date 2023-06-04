package com.example.pp_ff;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class log_in extends AppCompatActivity {
    Button b1,b2,b3;
    EditText e1,e2;
    boolean viewpass;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);checkConnection();
        b1=findViewById(R.id.login1);
        b2=findViewById(R.id.signin);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(log_in.this,signin.class);
                startActivity(i);
            }
        });
        Intent i=getIntent();
        e1=findViewById(R.id.edd1);
        e2=findViewById(R.id.edd2);
        String x=e1.getText().toString();
        String y=e2.getText().toString();
        mAuth = FirebaseAuth.getInstance();
        e2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int right =2 ;
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(event.getRawX()>=e2.getRight()-e2.getCompoundDrawables()[right].getBounds().width()){
                        int selection =e2.getSelectionEnd();
                        if (viewpass)
                        {
                            e2.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_off_24,0);
                            e2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            viewpass=false ;
                        }
                        else {
                            e2.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.seen,0);
                            e2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            viewpass=true ;
                        }
                        e2.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = e1.getText().toString();
                String password = e2.getText().toString();
                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(log_in.this, "Fields are empty! \n Enter email and password",Toast.LENGTH_LONG).show();
                    return;
                }


                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(log_in.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser currentUser = mAuth.getCurrentUser();
                                    String checkemail = currentUser.getEmail();
                                    String adminemail = "muna@gmail.com";
                                    if(adminemail.equals(checkemail) ){
                                        Intent adminintent = new Intent(log_in.this, adminpage.class);
                                        startActivity(adminintent);
                                    }else{
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        updateUI(user);
                                    }
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(log_in.this, "Authentication failed \n The email or password is incorrect",Toast.LENGTH_LONG).show();
                                }

                                // ...
                            }
                        });
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            String checkemail = currentUser.getEmail();
            String adminemail = "muna@gmail.com";
            if(adminemail.equals(checkemail) ){
                Intent adminintent = new Intent(log_in.this, adminpage.class);
                startActivity(adminintent);
            }else{
                updateUI(currentUser);
            }
        }
    }

    private void updateUI(FirebaseUser currentUser) {
        Intent profileIntent = new Intent(log_in.this, mainpage.class);
        profileIntent.putExtra("email",currentUser.getEmail());
        startActivity(profileIntent);
    }

    public void checkConnection(){
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        if(null==activeNetwork){
            SweetAlertDialog dialog = new SweetAlertDialog(log_in.this,SweetAlertDialog.CUSTOM_IMAGE_TYPE);
            dialog.setTitleText("No Internet Connection").setContentText("Please check your internet connection").setCustomImage(R.drawable.wifi).show();
            Button btn = (Button) dialog.findViewById(R.id.confirm_button);
            btn.setBackgroundColor(ContextCompat.getColor(log_in.this,R.color.colorPrimary));

        }
    }


}

