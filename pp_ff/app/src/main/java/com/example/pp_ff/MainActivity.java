package com.example.pp_ff;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

    public class MainActivity extends Activity {

        /** Duration of wait **/
        private final int SPLASH_SCREEN = 5000;

        Animation topAnim , bottomAnim;
        ImageView image;
        TextView logo;

        private static final String TAG="MainActivity";
        /** Called when the activity is first created. */
        @Override
        protected void onCreate (Bundle savedInstancestate) {
            super.onCreate(savedInstancestate);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_main);

            topAnim= AnimationUtils.loadAnimation(this,R.anim.top_an);
            bottomAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_an);


            image=findViewById(R.id.splashscreen);
            logo=findViewById(R.id.textView);

            image.setAnimation(topAnim);
            image.setAnimation(bottomAnim);


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this,log_in.class);
                    startActivity(intent);
                    finish();



                }
            },SPLASH_SCREEN);
        }
    }