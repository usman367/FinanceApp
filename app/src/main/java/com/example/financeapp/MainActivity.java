package com.example.financeapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    // Used this for help:
    // https://guides.codepath.com/android/using-the-recyclerview


    //Variable which we use for updating the Main screen once the user has added a transaction
    int ADD_TRANSACTION_ACTIVITY_REQUEST_CODE = 1;

    int totalAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Getting the "+" button
        View fabAddTransaction = findViewById(R.id.fabAddTransaction);

        //Creating an intent to take the user to the AddTransactionActivity when the "+" button is pressed
        //Have to create this outside the function below
        Intent intent = new Intent(this, AddTransactionActivity.class);

        //Creating the onClickListener for the button
        fabAddTransaction.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Changed form this:
                //startActivity(intent);
                //To this:
                startActivityForResult(intent, ADD_TRANSACTION_ACTIVITY_REQUEST_CODE);
                //So the home screen automatically updates when we add a transaction
                //Instead of closing the app and then opening it again to see it on the homepage
                //We pass in the ADD_TRANSACTION_ACTIVITY_REQUEST_CODE which will be checked in the onActivityResult() func below
                //Change made in the onClick function under R.id.btn_save in the AddTransactionActivity
                //onActivityResult Button function added below to update it
            }
        });

        getTransactionListFromLocalDB();

    }


    private void getTransactionListFromLocalDB(){
        //Creating an object of the Database Handler
        DatabaseHandler dbHandler = new DatabaseHandler(this);

        //Creating an ArrayList of the type TransactionModel
        //Stores all the data in it, which we get from the getTransactionsList() method in the Database Handler class
        ArrayList<TransactionModel2> getTransactionList = dbHandler.getTransactionsList();

        //If the list is not empty
        if(getTransactionList.size() > 0){
            androidx.recyclerview.widget.RecyclerView rv_transaction_list = findViewById(R.id.rv_transaction_list);
            TextView tv_no_records_available = findViewById(R.id.tv_no_records_available);

            //Then show the RecyclerView
            rv_transaction_list.setVisibility(View.VISIBLE);
            tv_no_records_available.setVisibility(View.GONE);

            //Call this func from below which will set the RecyclerView
            setupTransactionRecyclerView(getTransactionList);


        }else{
            androidx.recyclerview.widget.RecyclerView rv_transaction_list = findViewById(R.id.rv_transaction_list);
            TextView tv_no_records_available = findViewById(R.id.tv_no_records_available);

            //Otherwise just show the TV which says there is no data
            rv_transaction_list.setVisibility(View.GONE);
            tv_no_records_available.setVisibility(View.VISIBLE);
        }

    }


    private void setupTransactionRecyclerView(ArrayList<TransactionModel2> transactionList){
        //Get the Recycler View from activity_main
        androidx.recyclerview.widget.RecyclerView rv_transaction_list = findViewById(R.id.rv_transaction_list);

        //We want to use the linear layout model
        rv_transaction_list.setLayoutManager(new LinearLayoutManager(this));


        rv_transaction_list.setHasFixedSize(true);

        //We set the adapter we want to use
        TransactionAdapter transactionAdapter = new TransactionAdapter(this, transactionList);
        rv_transaction_list.setAdapter((RecyclerView.Adapter) transactionAdapter);



        //Create an instance of our SwipeToDeleteCallback, we pass in our adapter
        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new SwipeToDeleteCallback(transactionAdapter));
        //We attach our itemTouchHelper to our RecyclerView
        itemTouchHelper.attachToRecyclerView(rv_transaction_list);


        // These two are for our other SwipeToDeleteCallback code that didn't work
//        ItemTouchHelper.Callback deleteSwipeHandler = new SwipeToDeleteCallback(this) {
//            @Override
//            protected Object PorterDuffXfermode(PorterDuff.Mode clear) {
//                return null;
//            }
//
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                //RecyclerView.Adapter adapter = rv_transaction_list.getAdapter();
//                //adapter.rem
//
//                  TransactionAdapter transactionAdapter = new TransactionAdapter(context, transactionList);
//                  transactionAdapter.removeAt(viewHolder.getAdapterPosition());
//                  getTransactionListFromLocalDB();
//
//            }
//        };
//
//        ItemTouchHelper deleteItemTouchHelper = new ItemTouchHelper(deleteSwipeHandler);
//        deleteItemTouchHelper.attachToRecyclerView(rv_transaction_list);


//        SwipeToDeleteCallback deleteSwipeHandler = new SwipeToDeleteCallback() {
//            @Override
//            protected Object PorterDuffXfermode(PorterDuff.Mode clear) {
//                return null;
//            }
//
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//
//                  RecyclerView.Adapter adapter = rv_transaction_list.getAdapter();
//
//
//
//            }
//        }



        //For our TotalAmount TextView:
        //TextView tvTotalAmount = findViewById(R.id.tv_totalAmount);
        //tvTotalAmount.setVisibility(View.VISIBLE);
        //tvTotalAmount.setText(String.valueOf(totalAmount));

        //rv_transaction_list.addView(tvTotalAmount);

//        TextView newTextView = new TextView(this);
//        newTextView.setLayoutParams(new RelativeLayout.LayoutParams
//                ((int) LinearLayout.LayoutParams.WRAP_CONTENT,(int) LinearLayout.LayoutParams.WRAP_CONTENT));
//        newTextView.setText(totalAmount);
//        rv_transaction_list.addView(newTextView);



    }


    //It is called when the activity which launched with the request code and expecting a result from the launched activity.
    //Call Back method to get the Message form other Activity
    //We added this function so it updates the Main Screen when a transaction is added
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check if the request code is same as what is passed here it is 'ADD_TRANSACTION_REQUEST_CODE'
        //If the request code is equal to the code we sent from here
        if (requestCode == ADD_TRANSACTION_ACTIVITY_REQUEST_CODE) {
            //And the result code is ok
            //We set it to OK in the onClick function under R.id.btn_save in the AddTransactionActivity
            if (resultCode == Activity.RESULT_OK) {
                //Then call getHappyPlacesListFromLocalDB() to get the latest version of the data from the database
                getTransactionListFromLocalDB();;

            }else{
                Log.e("Activity", "Cancelled or Back Pressed");
            }
        }

    }


    public void setTotalAmount(int amount){
        this.totalAmount = this.totalAmount + amount;

        if(totalAmount >0){
            Log.i("TotalAmountMain", "TotalAmount is successfully retrieved");
            Log.i("TotalAmountMain", String.valueOf(totalAmount));
        }else{
            Log.i("TotalAmountMain", "Unsuccessful");
        }
    }



}
