package com.example.pp_ff;

public class myrequsetitem {
    private int mImageResource;
    private String mText1;
    private String mText2;
    private String mText3;
    private String mText4;
    private String mText5;

    public myrequsetitem() {
    }


    public myrequsetitem(int imageResource, String text1, String text2,String text3, String text4, String text5) {
        mImageResource = imageResource;
        mText1 = text1;
        mText2 = text2;
        mText3 = text3;
        mText4 = text4;
        mText5 = text5;
    }


    public int getImageResource() {
        return mImageResource;
    }
    public String getText1() {
        return mText1;
    }
    public String getText2() { return mText2;}
    public String getText3() {
        return mText3;
    }
    public String getText4() {
        return mText4;
    }
    public String getText5(){return mText5; }

}
