package com.example.pp_ff;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class contactus extends AppCompatActivity {

    ImageView img1,img2,img3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);

        img1=findViewById(R.id.location);
        img2=findViewById(R.id.mail);
        img3=findViewById(R.id.call);

        Intent i=getIntent();

        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* AlertDialog.Builder builder = new AlertDialog.Builder(
                        contactus.this);
                builder.setTitle("Call us");
                builder.setMessage("0595081757");

                builder.show();*/
                String phoneNumber = "0568994685";
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(dialIntent);
            }

        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String recipientEmail = "bookboxpro@gmail.com";
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + recipientEmail));
                intent.putExtra(Intent.EXTRA_SUBJECT, " ");
                startActivity(intent);


            }

        });
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2=new Intent(contactus.this, MapsActivity.class);
                startActivity(i2);
            }

        });

    }
}