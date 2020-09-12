package com.example.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    public int quantity = 1;
    public int coffeeSinglePrice = 5;
    public int whipped_cream_price = 1;
    public int chocolate_price = 2;
    public String customer_name;
    public static final String EXTRA_MESSAGE = "com.example.OrderDetails.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void submitOrder(View view) {
        sentMessage(view);
    }

    public void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(String.valueOf(number));
    }

    public void increment(View view) {
        if(quantity == 100) {
            return;
        }
        quantity ++;
        displayQuantity(quantity);
    }

    public void decrement(View view){
        if(quantity > 1) {
            quantity--;
            displayQuantity(quantity);
        } else {
            displayQuantity(1);
        }
    }

    public String orderSummary(int number) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        EditText editText = (EditText) findViewById(R.id.name_field);
        customer_name = editText.getText().toString();
        String message = "Name: " + customer_name;
        message += "\nAdd whipped cream? " + hasWhippedCream;
        message += "\nAdd chocolate? " + hasChocolate;
        message += "\nquantity: " + quantity;
        message += "\nTotal: $" + calculate()+ "\nThank you!";
        return message;
    }


    public int calculate() {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        if(hasWhippedCream) {
            if (hasChocolate) {
                return (coffeeSinglePrice + whipped_cream_price + chocolate_price) * quantity;
            } else {
                return (coffeeSinglePrice + whipped_cream_price) * quantity;
            }
        } else if(hasChocolate) {
            return (coffeeSinglePrice + chocolate_price) * quantity ;
        } else {
            return coffeeSinglePrice * quantity;
        }

    }

    public void sentMessage(View view) {
        Intent intent = new Intent(this, DisplayOrderDetails.class);
        String message = orderSummary(calculate());
        intent.putExtra(EXTRA_MESSAGE, message);
        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


} // end of app