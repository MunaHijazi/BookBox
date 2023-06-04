package com.example.pp_ff;

import android.widget.Filter;

import java.util.ArrayList;

public class CustomFilter extends Filter {

    ArrayList<cardviewitem> filterList;
    cardadapter adapter;

    public CustomFilter(ArrayList<cardviewitem> filterList, cardadapter adapter) {
        this.filterList = filterList;
        this.adapter = adapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {

        FilterResults results = new FilterResults();
        if(charSequence != null && charSequence.length()>0){
            charSequence = charSequence.toString().toUpperCase();
            ArrayList<cardviewitem> filtersCard = new ArrayList<>();

            for(int i = 0; i<filterList.size(); i++){
                if(filterList.get(i).getText1().toUpperCase().contains(charSequence)){
                    filtersCard.add(filterList.get(i));
                }
            }

            results.count = filtersCard.size();
            results.values = filtersCard;
        }

        else {
            results.count = filterList.size();
            results.values = filterList;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

        adapter.mExampleList = (ArrayList<cardviewitem>) filterResults.values;
        adapter.notifyDataSetChanged();

    }




}
