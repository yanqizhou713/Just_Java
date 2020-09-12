package com.example.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DisplaySalesDetails extends AppCompatActivity {
    String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_sales_details);
        intent();
    }

    public void intent() {
        Intent intent =getIntent();
        message = intent.getStringExtra("db");
        TextView salesView = (TextView) findViewById(R.id.dispalyTextView);
        salesView.setText(message);
    }

    public void reorder(View view) {
        final Intent mainActivity = new Intent(this, MainActivity.class);
        mainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        if(mainActivity.resolveActivity(getPackageManager()) != null) {
            startActivity(mainActivity);
        }
    }

    public void deleteRecord(View view) {
        CoffeeDBHandler dbHandler = new CoffeeDBHandler(this, null, null, 1);
        dbHandler.deleteOrder();
        Toast.makeText(getApplicationContext(), "Data Deleted!", Toast.LENGTH_SHORT).show();
        TextView salesView = (TextView) findViewById(R.id.dispalyTextView);
        salesView.setText("");
        TextView totalSalesView = (TextView) findViewById(R.id.totalSalesTextView);
        totalSalesView.setText("");
    }

    public void displayTotalSales(View view) {
        CoffeeDBHandler dbHandler = new CoffeeDBHandler(this, null, null, 1);
        int amount = dbHandler.displayTotalSales();
        TextView salesView = (TextView) findViewById(R.id.totalSalesTextView);
        salesView.setText("Total Sales are $"+amount);
    }
} // end of app