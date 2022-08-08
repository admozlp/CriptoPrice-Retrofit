package com.ademozalp.retrofitjava.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ademozalp.retrofitjava.databinding.RecyclerRowBinding;
import com.ademozalp.retrofitjava.model.CryptoModel;

import java.util.ArrayList;
import java.util.Random;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    ArrayList<CryptoModel> cryptoModels;
    Random rnd = new Random();

    public Adapter(ArrayList<CryptoModel> cryptoModels) {
        this.cryptoModels = cryptoModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(recyclerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return cryptoModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerRowBinding recyclerRowBinding;
        public ViewHolder(RecyclerRowBinding recyclerRowBinding) {
            super(recyclerRowBinding.getRoot());
            this.recyclerRowBinding = recyclerRowBinding;
        }

        public void bind(int position){
            int currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            itemView.setBackgroundColor(currentColor);
            recyclerRowBinding.textName.setText(cryptoModels.get(position).currency);
            recyclerRowBinding.textPrice.setText(cryptoModels.get(position).price + "$");
        }
    }
}
