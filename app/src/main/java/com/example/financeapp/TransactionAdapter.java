package com.example.financeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedOutputStream;
import java.util.ArrayList;

public class TransactionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Used this for help:
    // https://guides.codepath.com/android/using-the-recyclerview

    private Context context;
    private  ArrayList<TransactionModel2> list;

    TransactionAdapter(Context context, ArrayList<TransactionModel2> list){
        this.context = context;
        this.list = list;
    }

    /**
     * Inflates the item views which is designed in xml layout file
     *
     * create a new
     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){

        return new MyViewHolder(
                //We want to use the item_transaction layout we created
                LayoutInflater.from(context).inflate(
                        R.layout.item_transaction,
                        parent,
                        false
                )
        );

        // return null;
    }


    /**
     * Binds each item in the ArrayList to a view
     *
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //List is the list we pass in to the TransactionAdapter above as a parameter
        TransactionModel2 model = list.get(position);

        //holder.itemView.findViewById(R.id.tvAmount).text

        if(holder instanceof  MyViewHolder){

            ((TextView)holder.itemView.findViewById(R.id.tvAmount)).setText(model.getAmount());
            ((TextView)holder.itemView.findViewById(R.id.tvDescription)).setText(model.getDescription());
            ((TextView)holder.itemView.findViewById(R.id.tvDate)).setText(model.getDate());
            ((TextView)holder.itemView.findViewById(R.id.tvMethod)).setText(model.getMethod()) ;

//            ((TextView)holder.itemView.findViewById(R.id.tvDescription)).setText() = model.getDescription();
//            ((TextView)holder.itemView.findViewById(R.id.tvDate)).setText() = model.getDate();
//            ((TextView)holder.itemView.findViewById(R.id.tvMethod)).setText() = model.getMethod();

        }
    }


    /**
     * Gets the number of items in the list
     */
    @Override
    public int getItemCount() {
        return list.size();
    }


    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    private class MyViewHolder extends RecyclerView.ViewHolder{
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
