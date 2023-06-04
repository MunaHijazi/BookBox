package com.example.pp_ff;

import android.content.Intent;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signin extends AppCompatActivity {
    Button b1;
    FirebaseDatabase database;
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    boolean viewpass;
    private static final String USER = "user";
    private static final String TAG="signin";
    EditText namee, passworde1, emaile, passworde2,uide;
    User user;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        b1=findViewById(R.id.login2);
        namee = findViewById(R.id.namee);
        passworde1 = findViewById(R.id.passworde1);
        passworde2 = findViewById(R.id.passworde2);
        emaile = findViewById(R.id.emaile);
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference(USER);
        mAuth = FirebaseAuth.getInstance();
        passworde1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int right =2 ;
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(event.getRawX()>=passworde1.getRight()-passworde1.getCompoundDrawables()[right].getBounds().width()){
                        int selection =passworde1.getSelectionEnd();
                        if (viewpass)
                        {
                            passworde1.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_off_24,0);
                            passworde1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            viewpass=false ;
                        }
                        else {
                            passworde1.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.seen,0);
                            passworde1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            viewpass=true ;
                        }
                        passworde1.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
        passworde2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int right =2 ;
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(event.getRawX()>=passworde2.getRight()-passworde2.getCompoundDrawables()[right].getBounds().width()){
                        int selection =passworde2.getSelectionEnd();
                        if (viewpass)
                        {
                            passworde2.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_off_24,0);
                            passworde2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            viewpass=false ;
                        }
                        else {
                            passworde2.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.seen,0);
                            passworde2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            viewpass=true ;
                        }
                        passworde2.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emaile.getText().toString();
                String password1 = passworde1.getText().toString();
                String password2 = passworde2.getText().toString();
                String name = namee.getText().toString();

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password1) || TextUtils.isEmpty(name) || TextUtils.isEmpty(password2)){
                    Toast.makeText(signin.this, "Please fill all fields", Toast.LENGTH_LONG).show();
                    return;
                }
                if(!password1.equals(password2)){
                    Toast.makeText(signin.this, "Please match password", Toast.LENGTH_LONG).show();
                    return;
                }
                registerUser(email,password1);
                user = new User(name,password1,email);
                Intent ii=getIntent();
            }
        });
    }

    public void registerUser(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(signin.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(signin.this, "Authentication failed.", Toast.LENGTH_LONG).show();
                    Toast.makeText(signin.this,"Error!" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void updateUI(FirebaseUser currentUser) {
        uid = currentUser.getUid();
        user.setUid(uid);
        mDatabase= FirebaseDatabase.getInstance().getReference().child(USER);
        mDatabase.child(user.getUid()).setValue(user);
        Intent i=new Intent(signin.this,mainpage.class);
        startActivity(i);
    }

}
