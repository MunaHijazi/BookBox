package com.example.pp_ff;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class list extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] maintitle;
    private final String[] subtitle1;
    private final String[] subtitle2;



    public list(Activity context, String[] maintitle, String[] subtitle1, String[] subtitle2) {
        super(context, R.layout.customlist, maintitle);



        this.context=context;
        this.maintitle=maintitle;
        this.subtitle1=subtitle1;
        this.subtitle2=subtitle2;




    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.customlist, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.titi);
        TextView subtitleText1 = (TextView) rowView.findViewById(R.id.subtitle1);
        TextView subtitleText2 = (TextView) rowView.findViewById(R.id.subtitle2);

        titleText.setText(maintitle[position]);
        subtitleText1.setText(subtitle1[position]);
        subtitleText2.setText(subtitle2[position]);

        return rowView;

    };


}
