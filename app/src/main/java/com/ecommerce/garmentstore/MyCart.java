package com.ecommerce.garmentstore;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MyCart extends AppCompatActivity {

    RecyclerView recycler_itemlist;
    public static TextView tv_total;
    CardListAdapter cardListAdapter;
    public static int total=0;
     public static List<ItemDetails> itemList = new ArrayList<>();
     public  String itemName;
    public String itemSize;
    public String itemColor;
    public static String itemRate;
    public String itemQuantity;
    SqliteHelper sqliteHelper;
    public static Button btn_placeorder;
   // Toolbar toolbar;
    Toolbar toolbar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);




        sqliteHelper = new SqliteHelper(MyCart.this);
        final ItemDetails itemDetails = new ItemDetails();
        tv_total =(TextView) findViewById(R.id.tv_total);
        btn_placeorder = (Button) findViewById(R.id.btn_placeorder);
        recycler_itemlist =(RecyclerView) findViewById(R.id.recycler_cart);
        cardListAdapter = new CardListAdapter(MyCart.this,itemList);
        recycler_itemlist.setHasFixedSize(true);
        recycler_itemlist.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        recycler_itemlist.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        recycler_itemlist.getRecycledViewPool().setMaxRecycledViews(0, 0);
        recycler_itemlist.setAdapter(cardListAdapter);
        prepareItemData(itemDetails);
        calculateTotal(itemQuantity);
        btn_placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!sqliteHelper.isItemNameExists("1")) {

                    sqliteHelper.addItem(itemDetails);
                    Snackbar.make(btn_placeorder, "Item added successfully! In Database ", Snackbar.LENGTH_LONG).show();
                    MyApplication.getInstance().trackEvent("Garment", "Saving Cart", itemDetails.getRate());



                } else {
                    Snackbar.make(btn_placeorder, "Item already exists In Database ", Snackbar.LENGTH_LONG).show();
                }

            }
        });

    }

    private void prepareItemData(ItemDetails itemDetails) {

        Intent intent = getIntent();

        itemName = intent.getStringExtra("name");
        itemSize = intent.getStringExtra("size");
        itemColor = intent.getStringExtra("color");
        itemRate = intent.getStringExtra("price");
        itemQuantity = intent.getStringExtra("itemQuantity");


      //  ItemDetails itemDetails = new ItemDetails();
        itemDetails.setItemName(itemName);
        itemDetails.setSize(itemSize);
        itemDetails.setColor(itemColor);
        itemDetails.setRate(itemRate);
        itemDetails.setQuantity(itemQuantity);
        itemList.add(itemDetails);
        cardListAdapter.notifyDataSetChanged();



    }

    @SuppressLint("SetTextI18n")
    public static void calculateTotal(String itemQuantity){
        int i=0;
        total=0;
        while(i<itemList.size()){
            total=total + ( Integer.parseInt(itemRate) * Integer.parseInt(itemQuantity));
            i++;
        }
        tv_total.setText(""+total);
    }

}