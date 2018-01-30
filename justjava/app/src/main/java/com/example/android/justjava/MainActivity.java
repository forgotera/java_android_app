package com.example.android.justjava;

/**
 * IMPORTANT: Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 *
 */


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;
    int cena;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
       composeEmail( name ,  createOrderSummary());
    }

    public void composeEmail( String subject, String attachment) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, attachment);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private String createOrderSummary(){
        EditText getName = (EditText) findViewById(R.id.EditText_name_view);
        CheckBox cream = (CheckBox) findViewById(R.id.checkbox_cream_view);
        CheckBox chocolate = (CheckBox) findViewById(R.id.checkbox_chokolate_view);


        if( (cream.isChecked()) && (chocolate.isChecked()) ){
            cena = 8;
        }else if( !(cream.isChecked()) && (chocolate.isChecked()) ){
            cena = 6;
        }else if( (cream.isChecked()) && !(chocolate.isChecked()) ){
            cena = 7;
        }else {
            cena = 5;
        }


        int price = (quantity * cena);
        name =getString(R.string.order_for)+ String.valueOf(getName.getText());
        return "\n"+getString(R.string.add_Whipped_cream) +cream.isChecked() + "\n"+ getString(R.string.add_chocolate) +chocolate.isChecked() +"\n"+getString(R.string.Quantity) + quantity + "\n"+getString(R.string.Total) + price +"\n"+getString(R.string.Thank_you);

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display( int number ) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */


    public void increment(View view){
        if(quantity < 100) {
            quantity++;
            display(quantity);
        }
    }

    public void decrement(View view){
        if(quantity>1) {
            quantity--;
            display(quantity);
        }
    }


}
