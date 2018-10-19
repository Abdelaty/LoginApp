package com.example.abdel.loginapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CountriesNamesAdapter extends RecyclerView.Adapter<CountriesNamesAdapter.ViewHolder> {
    private List<ModelClass> list;
    Context context;
    CountriesNamesAdapter(List<ModelClass> list,Context context) {
        this.list = list;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ModelClass namesList = list.get(position);
        holder.name.setText(namesList.getCountryName());
    }

    @Override
    public int getItemCount() {

        int arr = 0;

        try{
            if(list.size()==0){

                arr = 0;

            }
            else{

                arr=list.size();
            }



        }catch (Exception e){



        }

        return arr;

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tvCountryName);
        }
    }
}