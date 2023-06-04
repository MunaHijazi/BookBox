package com.example.pp_ff;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class noteficationadapter extends RecyclerView.Adapter<noteficationadapter.ExampleViewHolder> {
    private ArrayList<noteficationitem> mExampleList;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView2;

        public ExampleViewHolder(View itemView) {
            super(itemView);

            mTextView2 = itemView.findViewById(R.id.text);
        }
    }

    public noteficationadapter(ArrayList<noteficationitem> exampleList) {
        mExampleList = exampleList;

    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.noteficationtemp, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        noteficationitem currentItem = mExampleList.get(position);
        holder.mTextView2.setText(currentItem.getText2());

    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }


}
