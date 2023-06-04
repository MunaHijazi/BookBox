package com.example.pp_ff;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class adminpage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpage);

        Intent i= getIntent();
        Button b=findViewById(R.id.log);
        Toolbar toolbar2 = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar2);


        final TabLayout tabs2 = findViewById(R.id.tabs2);
        TabLayout.Tab firstTab = tabs2.newTab();
        firstTab.setText("Add");
        tabs2.addTab(firstTab,0);

        TabLayout.Tab secandTab = tabs2.newTab();
        secandTab.setText("Modfiy");
        tabs2.addTab(secandTab,1);

        TabLayout.Tab thirdTab = tabs2.newTab();
        thirdTab.setText("Delete");
        tabs2.addTab(thirdTab,2);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2,new adminadd()).commit();
        tabs2.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==0){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2,new adminadd()).commit();
                }
                else if(tab.getPosition()==1)
                {  getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2,new adminmodfiy()).commit();}

                else{getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2,new admindelete()).commit();}
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent i3=new Intent(adminpage.this,MainActivity.class);
                startActivity(i3);

            }
        });




    }
}
