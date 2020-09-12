package com.example.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayOrderDetails extends AppCompatActivity {
    CoffeeDBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_order_details);
        dbHandler = new CoffeeDBHandler(this, null, null, 1);
        intent();

    }

    public void intent() {
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = findViewById(R.id.display_text_view);
        textView.setText(message);
    }

    public void sendMessage(View view) {
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        String name = message.substring(6, message.indexOf("\n"));
        intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void submitEmailOrder(View view) {
        sendMessage(view);
    }

    // button to save data in SQLite database
    public void addButton(View view) {
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        String name = message.substring(6, message.indexOf("\n"));
        String price = message.substring((message.indexOf("$")+1), message.lastIndexOf("\n"));
        Order order = new Order(name, Integer.parseInt(price));
        dbHandler.addOrder(order);
        Toast.makeText(getApplicationContext(), "Data Saved!", Toast.LENGTH_SHORT).show();
    }

    // method to send the report details
    public void salesReport(View view) {
        // collect data from database
        String dbString = dbHandler.databaseToString();
        // new intent
        Intent saleInent = new Intent(this, DisplaySalesDetails.class);
        saleInent.putExtra("db", dbString);
        if(saleInent.resolveActivity(getPackageManager()) != null) {
            startActivity(saleInent);
        }
    }
} // end of app