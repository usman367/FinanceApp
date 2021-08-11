package com.example.financeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class AddTransactionActivity extends AppCompatActivity implements View.OnClickListener {

    private Calendar cal = Calendar.getInstance();
    //private DatePickerDialog dateSetListener = DatePickerDialog.OnDateSetListener;


    //For the date picker dialog
    private int mYear, mMonth, mDay, mHour, mMinute;

    //Variable which will store the amount of money the user has spent
    private int thisAmount = 0;

    MainActivity mainActivity = new MainActivity();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        //Gert the EditText and create an OnClickListener for it
        EditText et_date = findViewById(R.id.et_date);
        et_date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Process to get Current Date
                //Creating an instance of the calendar
                final Calendar c = Calendar.getInstance();
                //Get the day month and year using the instance
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                // Launch Date Picker Dialog
                DatePickerDialog dpd = new DatePickerDialog(AddTransactionActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // Display Selected date in edit text
                                //monthOfYear + 1 because the months start from 0 in android
                                et_date.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);

                            }
                            //Set the current date to the date we got from above using the calendars instance
                        }, mYear, mMonth, mDay);
                //Show the dialog
                dpd.show();
            }
        });

//        EditText et_amount = findViewById(R.id.et_amount);
//        et_amount.setOnClickListener(this);

        // We have extended the onClickListener above and the override method as onClick added and here we are setting a listener to date EditText.
        //Assigning a click event listener to the save button
        Button btn_save = findViewById(R.id.btn_save);
        btn_save.setOnClickListener(this);


    }


    //This function is implemented because we made the class implement View.OnClickListener
    @Override
    public void onClick(View v) {
        EditText et_amount = findViewById(R.id.et_amount);
        EditText et_description = findViewById(R.id.et_description);
        EditText et_date = findViewById(R.id.et_date);
        EditText et_method = findViewById(R.id.et_method);

        if(v.getId() == R.id.btn_save){

            if(et_amount.getText().length() == 0){
                Toast.makeText(this, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
            }
            else{
                //Creating an instance of the TransactionModel
                //We're passing in 0 for the ID, it will auto increment
                TransactionModel2 transactionModel = new TransactionModel2(
                        0,
                        et_amount.getText().toString(),
                        et_description.getText().toString(),
                        et_date.getText().toString(),
                        et_method.getText().toString()
                );

                DatabaseHandler dbHandler = new DatabaseHandler(this);

                Long addTransaction = dbHandler.addTransaction(transactionModel);

                //We get the value from the et_amount and add it to the totalAmount
                //We first need to convert it into a string and then convert that into an integer
                thisAmount += Integer.parseInt(et_amount.getText().toString());
                mainActivity.setTotalAmount(thisAmount);
                Log.i("TotalAmountActivity", String.valueOf(thisAmount));


                if(addTransaction > 0){
                    //Logging this to check if it works
                    Log.i("Database addition", "Database addition worked: ");

                    //We do this so we can update the home screen once we have added a transaction
                    setResult(Activity.RESULT_OK);

                    //Close this activity
                    finish();
                }else{
                    Toast.makeText(this, "Unsuccessful.", Toast.LENGTH_SHORT);

                    Log.i("Database addition", "Database addition unsuccessful: ");
                }


            }


        }



    }

//    public int getTotalAmount(){
//        return totalAmount;
//    }


}