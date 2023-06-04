package com.example.pp_ff;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class aboutapp extends AppCompatActivity {
    private ViewPager mViewPager;

    private CardPagerAdapterabout mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    Integer color[]=null;
    ArgbEvaluator ar =new ArgbEvaluator();
    boolean b=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutapp);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        Intent i=getIntent();
        mCardAdapter = new CardPagerAdapterabout();
        mCardAdapter.addCardItem(new CardItemabout(R.string.title_1, R.string.text1,R.drawable.college));
        mCardAdapter.addCardItem(new CardItemabout(R.string.title_2, R.string.text2,R.drawable.aim));
        mCardAdapter.addCardItem(new CardItemabout(R.string.title_3, R.string.text3,R.drawable.soso));
        mCardAdapter.addCardItem(new CardItemabout(R.string.title_4, R.string.text4,R.drawable.programer));

        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mCardShadowTransformer.enableScaling(b);


        Integer[] colortemp={getResources().getColor(R.color.color1), getResources().getColor(R.color.color2),
                getResources().getColor(R.color.color3), getResources().getColor(R.color.color4)};

        color=colortemp;

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position<(mCardAdapter.getCount()-1 )&& position<(color.length-1)){
                    mViewPager.setBackgroundColor((Integer)ar.evaluate(positionOffset,color[position],color[position]+1));

                }
                else { mViewPager.setBackgroundColor(color[color.length-1]);}
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
