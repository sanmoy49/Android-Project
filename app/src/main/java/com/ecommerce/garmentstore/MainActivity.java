package com.ecommerce.garmentstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    RadioGroup radioGroup, radioGroup1;
    MaterialRadioButton radioButton1, radioButton2, radioButton3, smallButton, mediumButton, largeButton;
    Button btn_addCard, btn_placeOrder;
    String colorValue, sizeValue;
    TextView title, price;


    @SuppressLint({"ResourceType", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
        btn_addCard = (Button) findViewById(R.id.btn_addCard);
        title = (TextView) findViewById(R.id.title);
        price = (TextView) findViewById(R.id.price);


        radioButton1 = (MaterialRadioButton) findViewById(R.id.radioButton1);
        radioButton2 = (MaterialRadioButton) findViewById(R.id.radioButton2);
        radioButton3 = (MaterialRadioButton) findViewById(R.id.radioButton3);
        smallButton = (MaterialRadioButton) findViewById(R.id.smallButton);
        mediumButton = (MaterialRadioButton) findViewById(R.id.mediumButton);
        largeButton = (MaterialRadioButton) findViewById(R.id.largeButton);


        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        ImageAdapter adapter = new ImageAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(adapter.getCount() - 1);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton radioButton = (RadioButton) findViewById(checkedId);
                colorValue = radioButton.getText().toString();
                Log.d(TAG, "onCheckedChanged: " + colorValue);
                Toast.makeText(getApplicationContext(), radioButton.getText(), Toast.LENGTH_LONG).show();
            }
        });

        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton radioButton = (RadioButton) findViewById(checkedId);
                sizeValue = radioButton.getText().toString();
                Log.d(TAG, "onCheckedChanged: " + sizeValue);
                Toast.makeText(getApplicationContext(), radioButton.getText(), Toast.LENGTH_LONG).show();
            }
        });

        btn_addCard.setTag(1);
        btn_addCard.setText("Add to Cart");
        btn_addCard.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                if (colorValue !=null && sizeValue !=null) {
                    final int status = (Integer) view.getTag();
                    if (status == 1) {
                        btn_addCard.setText("Go to Cart");
                        view.setTag(0);
                        Snackbar.make(btn_addCard, "Item added to cart", Snackbar.LENGTH_LONG).show();
                    } else {
                        Intent intent = new Intent(MainActivity.this, MyCart.class);
                        intent.putExtra("name", title.getText().toString());
                        intent.putExtra("price", price.getText().toString());
                        intent.putExtra("size", sizeValue);
                        intent.putExtra("color", colorValue);
                        intent.putExtra("itemQuantity", "1");
                        startActivity(intent);
                    }
                } else {
                    Snackbar.make(btn_addCard, "Please Select Color and Size ", Snackbar.LENGTH_LONG).show();
                }



            }

        });

    }
}