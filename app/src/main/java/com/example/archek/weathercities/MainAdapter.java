package com.example.archek.weathercities;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<City> cityList = new ArrayList<>();
    private MainFragment mainFragment;
    private boolean change;

    MainAdapter(MainFragment mainFragment) {
        this.mainFragment = mainFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View itemView = inflater.inflate( R.layout.item_city, parent, false);

        final ViewHolder holder = new ViewHolder( itemView ); //set on click listener choose city from list
        itemView.setOnClickListener( new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                City city  = cityList.get(holder.getAdapterPosition());
                if(!change) {
                    holder.tvName.setBackgroundResource(R.drawable.autumn_back);
                    setChange(true);
                }
                else {
                    holder.tvName.setBackgroundResource(R.drawable.back_item);
                    setChange(false);
                }

                switch (MainFragment.chooseSeason){
                    case "winter":
                        mainFragment.chooseCity(city.getCitySize(), valueOf(city.getWinter()));
                        break;
                    case "spring":
                        mainFragment.chooseCity(city.getCitySize(), valueOf(city.getSpring()));
                        break;
                    case "summer":
                        mainFragment.chooseCity(city.getCitySize(), valueOf(city.getSummer()));
                        break;
                    case "autumn":
                        mainFragment.chooseCity(city.getCitySize(), valueOf(city.getAutumn()));
                        break;
                }
            }
        } );
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {//bind and load all views to holder
        City city = cityList.get(position);
        holder.tvName.setText(city.getCityName());
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    } //count all items

    public void addAll(List<City> reviewsToReplace) {//load all reviews in main list
        cityList.clear();
        cityList.addAll(reviewsToReplace);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;

        private ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvCityName);
        }
    }

    private void setChange(boolean change) {
        this.change = change;
    }
}
