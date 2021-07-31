package com.example.financeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class DatabaseHandler extends SQLiteOpenHelper {

    // Used this for help:
    // https://guides.codepath.com/android/local-databases-with-sqliteopenhelper

    //static final added so it doesn't give an error in the constructor
    private static final int DATABASE_VERSION = 1; // Database version
    private static final String DATABASE_NAME = "FinanceAppDatabase"; // Database name

    //Table name
    private String TABLE_FINANCE_APP = "FinanceAppTable"; // Table Name

    //All the Columns names
    private String KEY_ID = "_id";
    private String KEY_AMOUNT = "amount";
    private String KEY_DESCRIPTION = "description";
    private String KEY_DATE = "date";
    private String KEY_METHOD = "method";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Creating our table
    @Override
    public void onCreate(SQLiteDatabase db) {
        //creating table with fields
        String CREATE_FINANCE_APP_TABLE = ("CREATE TABLE " + TABLE_FINANCE_APP + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_AMOUNT + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_DATE + " TEXT,"
                + KEY_METHOD + " TEXT)"
        );

        //Executing the statement
        db.execSQL(CREATE_FINANCE_APP_TABLE);

//        String CREATE_FINANCE_APP_TABLE = "CREATE TABLE " + TABLE_FINANCE_APP +
//                "(" +
//                KEY_ID + " INTEGER PRIMARY KEY," +
//                KEY_AMOUNT + " TEXT," +
//                KEY_DESCRIPTION + " TEXT," +
//                KEY_DATE + " TEXT," +
//                KEY_METHOD + " TEXT" +
//                ")";
//
//        db.execSQL(CREATE_FINANCE_APP_TABLE);

    }

    //When the table is changed, this is used to update the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //We delete the table
        db.execSQL("DROP TABLE IF EXISTS TABLE_FINANCE_APP");
        //And then we call the onCreate() method which re-draws the updated version
        onCreate(db);

//        if (oldVersion != newVersion) {
//            //We delete the table
//            db.execSQL("DROP TABLE IF EXISTS TABLE_FINANCE_APP");
//            //And then we call the onCreate() method which re-draws the updated version
//            onCreate(db);
//        }
    }


    //
    /**
     * Function to insert the Transaction to SQLite Database.
     */
    public Long addTransaction(TransactionModel2 transactionModel) {
        //Creates a database we can write in
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        //We use the TransactionModel parameter that's passed in to get its values and insert them into contentValues
        contentValues.put(KEY_AMOUNT, transactionModel.getAmount());
        contentValues.put(KEY_DESCRIPTION, transactionModel.getDescription());
        contentValues.put(KEY_DATE, transactionModel.getDate());
        contentValues.put(KEY_METHOD, transactionModel.getMethod());

        // Inserting Row
        //We give the table name, null and the values we want to insert into the database
        Long result = db.insert(TABLE_FINANCE_APP, null, contentValues);

        db.close();
        return result;


//        SQLiteDatabase db = getWritableDatabase();
//
//        try{
//            ContentValues contentValues = new ContentValues();
//
//            //We use the TransactionModel parameter that's passed in to get its values and insert them into contentValues
//            contentValues.put(KEY_AMOUNT, transactionModel.getAmount());
//            contentValues.put(KEY_DESCRIPTION, transactionModel.getDescription());
//            contentValues.put(KEY_DATE, transactionModel.getDate());
//            contentValues.put(KEY_METHOD, transactionModel.getMethod());
//
//            // Inserting Row
//            //We give the table name, null and the values we want to insert into the database
//            Long result = db.insert(TABLE_FINANCE_APP, null, contentValues);
//
//            return result;
//
//        }catch (Exception e) {
//            Log.d("Adding transaction", "Error while trying to add post to database");
//        }finally {
//            db.close();
//        }
//
//        Long x = 1L;
//        return x;

    }


    //A function to read all the list of Happy Places data which are inserted.
    public ArrayList<TransactionModel2> getTransactionsList() {
        // A list is initialize using the data model class in which we will add the values from cursor.
        ArrayList<TransactionModel2>  transactionList = new ArrayList<>();

        //Selects all the data from our database
        //Changed from TABLE_FINANCE_APP (the variable) to FinanceAppTable (the variables value) to make it work
        //Because the variable name inside the string is not read as a variable
        String selectQuery = "SELECT  * FROM FinanceAppTable" ; // Database select query

        SQLiteDatabase db = this.getReadableDatabase();

        //This section below is causing a problem
        try{
            //Creating our cursor which we will use to read the data
            //We pass in the query we created
            Cursor cursor = db.rawQuery(selectQuery, null);

            //Move to the first line
            if(cursor.moveToFirst()){
                do{
                    //For every single entry we create a new transaction
                    TransactionModel2 transaction = new TransactionModel2(
                            cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                            //Could be a String
                            cursor.getString(cursor.getColumnIndex(KEY_AMOUNT)),
                            cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)),
                            cursor.getString(cursor.getColumnIndex(KEY_DATE)),
                            cursor.getString(cursor.getColumnIndex(KEY_METHOD))
                    );

                    //Add the transaction to our ArrayList
                    transactionList.add(transaction);

                    //Read it while there is another list after
                }while (cursor.moveToNext());
            }
            cursor.close();


        }catch (SQLiteException e) {
            db.execSQL(selectQuery);
            Log.i("getTransactionList", "getTransactionsList is crashing: ");
            return transactionList;
        }

        return transactionList;


//        // A list is initialize using the data model class in which we will add the values from cursor.
//        ArrayList<TransactionModel2>  transactionList = new ArrayList<>();
//
//        //Selects all the data from our database
//        //Changed from TABLE_FINANCE_APP (the variable) to FinanceAppTable (the variables value) to make it work
//        //Because the variable name inside the string is not read as a variable
//        String selectQuery =
//                String.format("SELECT * FROM FinanceAppTable");
//
//        SQLiteDatabase db = getReadableDatabase();
//
//        //This section below is causing a problem
//        try{
//            //Creating our cursor which we will use to read the data
//            //We pass in the query we created
//            Cursor cursor = db.rawQuery(selectQuery, null);
//
//            //Move to the first line
//            if(cursor.moveToFirst()){
//                do{
//                    //For every single entry we create a new transaction
//                    TransactionModel2 transaction = new TransactionModel2(
//                            cursor.getInt(cursor.getColumnIndex(KEY_ID)),
//                            //Could be a String
//                            cursor.getString(cursor.getColumnIndex(KEY_AMOUNT)),
//                            cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)),
//                            cursor.getString(cursor.getColumnIndex(KEY_DATE)),
//                            cursor.getString(cursor.getColumnIndex(KEY_METHOD))
//                    );
//
//                    //Add the transaction to our ArrayList
//                    transactionList.add(transaction);
//
//                    //Read it while there is another list after
//                }while (cursor.moveToNext());
//            }
//            cursor.close();
//
//            Log.i("getTransactionList", "getTransactionsList worked ");
//
//
//        }catch (SQLiteException e) {
//            db.execSQL(selectQuery);
//            Log.i("getTransactionList", "getTransactionsList is crashing: ");
//            return transactionList;
//        }
//
//        return transactionList;


    }


}
