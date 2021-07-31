package com.example.financeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Used this for help:
    // https://guides.codepath.com/android/using-the-recyclerview

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
                startActivity(intent);
            }
        });

        getTransactionListFromLocalDB();

    }

    private void getTransactionListFromLocalDB(){
        DatabaseHandler dbHandler = new DatabaseHandler(this);

        //Causing a problem, its in the getTransactionsList() method
        ArrayList<TransactionModel2> getTransactionList = dbHandler.getTransactionsList();

        //Test:
        //ArrayList<TransactionModel2> getTransactionList = new ArrayList<>();


        if(getTransactionList.size() > 0){
            androidx.recyclerview.widget.RecyclerView rv_transaction_list = findViewById(R.id.rv_transaction_list);
            TextView tv_no_records_available = findViewById(R.id.tv_no_records_available);

            rv_transaction_list.setVisibility(View.VISIBLE);
            tv_no_records_available.setVisibility(View.GONE);

            setupTransactionRecyclerView(getTransactionList);
        }else{
            androidx.recyclerview.widget.RecyclerView rv_transaction_list = findViewById(R.id.rv_transaction_list);
            TextView tv_no_records_available = findViewById(R.id.tv_no_records_available);

            rv_transaction_list.setVisibility(View.GONE);
            tv_no_records_available.setVisibility(View.VISIBLE);
        }

    }


    private void setupTransactionRecyclerView(ArrayList<TransactionModel2> transactionList){
        androidx.recyclerview.widget.RecyclerView rv_transaction_list = findViewById(R.id.rv_transaction_list);

        rv_transaction_list.setLayoutManager(new LinearLayoutManager(this));
        //rv_transaction_list.setLayoutManager(LinearLayout);

        rv_transaction_list.setHasFixedSize(true);

         TransactionAdapter transactionAdapter = new TransactionAdapter(this, transactionList);
         rv_transaction_list.setAdapter((RecyclerView.Adapter) transactionAdapter);

    }



}