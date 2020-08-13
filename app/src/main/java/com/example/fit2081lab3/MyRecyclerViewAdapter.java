package com.example.fit2081lab3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fit2081lab3.provider.Item;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    List<Item> data = new ArrayList<>();

    public MyRecyclerViewAdapter(){
    }

    public void setData(List<Item> data) {
        this.data = data;
    }


    @NonNull
    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false); //CardView inflated as RecyclerView list item
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.id.setText(data.get(position).getItemID() + "");
        holder.item.setText(data.get(position).getItemName());
        holder.quantity.setText(data.get(position).getItemQuantity());
        holder.cost.setText(data.get(position).getItemCost());
        holder.description.setText(data.get(position).getItemDescription());
        holder.frozen.setText(data.get(position).getItemFrozen());


        final int fPosition = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() { //set back to itemView for students
            @Override public void onClick(View v) {
                Snackbar.make(v, "Item at position " + fPosition + " was clicked!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    //viewholder class
    public class ViewHolder extends RecyclerView.ViewHolder{
        //public View itemView;
        public TextView id;
        public TextView item;
        public TextView quantity;
        public TextView cost;
        public TextView description;
        public TextView frozen;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //this.itemView = itemView;
            id = itemView.findViewById(R.id.idText);
            item = itemView.findViewById(R.id.itemText);
            quantity = itemView.findViewById(R.id.quantityText);
            cost = itemView.findViewById(R.id.costText);
            description = itemView.findViewById(R.id.descriptionText);
            frozen = itemView.findViewById(R.id.frozenText);
        }
    }
}
