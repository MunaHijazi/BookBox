package com.example.pp_ff;

public class CardItemabout {
    private int mTextResource;
    private int mTitleResource;
    private int img;

    public CardItemabout(int title, int text,int imge) {
        mTitleResource = title;
        mTextResource = text;
        img=imge;
    }

    public int getText() {
        return mTextResource;
    }

    public int getTitle() {
        return mTitleResource;
    }

    public int getImg() { return img; }
}
