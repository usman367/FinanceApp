package com.example.financeapp;

import android.content.Context;
import android.util.Log;
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

    // Used these for help:
    // https://www.py4u.net/discuss/695914
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



    //This will remove it from the adapter
    // Create a function to delete the transaction which is inserted earlier from the local storage.
    /**
     * A function to delete the added transaction from the local storage.
     */
    public void deleteItem(int position) {
        //Create an instance of our Database Handler
        DatabaseHandler dbHandler = new DatabaseHandler(context);

        //Call the deleteTransaction() func from the dbHandler to delete this item
        int isDeleted = dbHandler.deleteTransaction(list.get(position));

        //If we were successful in deleting it from the database (Gives a positive number if we were)
        if(isDeleted > 0){
            //Then remove the item from the adapter
            list.remove(position);
            //Make sure it updates
            notifyItemRemoved(position);
        }

    }

    //Used in SwipeToDeleteCallback
    public Context getContext() {
        return context;
    }


    //Function for deleting the item for our other SwipeToDelete code
//    public void removeAt(int position){
//        DatabaseHandler dbHandler = new DatabaseHandler(context);
//
//        int isDeleted = dbHandler.deleteTransaction(list.get(position));
//
//        if(isDeleted > 0){
//            list.remove(position);
//            notifyItemRemoved(position);
//            Log.i("RemoveAt", "Successful");
//        }else{
//            Log.i("RemoveAt", "Unsuccessful");
//        }
//
//    }

}
