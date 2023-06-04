package com.example.pp_ff;

import androidx.cardview.widget.CardView;

public interface CardAdapterabout {
    int MAX_ELEVATION_FACTOR = 8;

    float getBaseElevation();

    CardView getCardViewAt(int position);

    int getCount();
}
