package com.ecommerce.garmentstore;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.ViewHolder> {

    List<ItemDetails> itemDetails;
    Context context;

    public CardListAdapter(Context context, List<ItemDetails> itemDetails) {
        this.itemDetails = itemDetails;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        ItemDetails itemDet = itemDetails.get(position);

        String itemId = itemDetails.get(position).ItemId;
        Log.d("itemId", "onBindViewHolder: " + itemId);
        holder.from_name.setText(itemDet.getItemName());
        holder.plist_price_text.setText(itemDet.getRate() + " Rs");
        holder.plist_size_text.setText(itemDet.getSize());
        holder.cart_product_quantity_tv.setText(itemDet.getQuantity());
        itemDet.setQuantity(holder.cart_product_quantity_tv.getText().toString());


        holder.cart_plus_img.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(String.valueOf(holder.cart_product_quantity_tv.getText()));
                count++;
                holder.cart_product_quantity_tv.setText("" + count);
                MyCart.calculateTotal(holder.cart_product_quantity_tv.getText().toString());


            }
        });

        holder.cart_minus_img.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(String.valueOf(holder.cart_product_quantity_tv.getText()));
                if (count == 1) {
                    holder.cart_product_quantity_tv.setText("1");
                    MyCart.calculateTotal(holder.cart_product_quantity_tv.getText().toString());
                } else {
                    count -= 1;
                    holder.cart_product_quantity_tv.setText("" + count);
                    MyCart.calculateTotal(holder.cart_product_quantity_tv.getText().toString());
                }
            }
        });

        holder.img_deleteitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SqliteHelper sqliteHelper = new SqliteHelper(context);
                        sqliteHelper.deleteItem(position);
                        itemDetails.remove(position);
                        notifyItemRemoved(position);
                        if (itemDetails == null || itemDetails.isEmpty()) {
                            MyCart.btn_placeorder.setEnabled(false);
                            MyCart.tv_total.setText("0");
                        }



                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


    }


    @Override
    public int getItemCount() {
        //Log.d("Size List:",String.valueOf(callListResponses.size()));
        if (itemDetails != null) {
            return itemDetails.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView from_name, plist_price_text, plist_size_text, cart_product_quantity_tv;
        ImageView cart_minus_img, cart_plus_img, img_deleteitem;


        public ViewHolder(View itemView) {
            super(itemView);
            cart_minus_img = (ImageView) itemView.findViewById(R.id.cart_minus_img);
            cart_plus_img = (ImageView) itemView.findViewById(R.id.cart_plus_img);
            img_deleteitem = (ImageView) itemView.findViewById(R.id.img_close);

            from_name = (TextView) itemView.findViewById(R.id.from_name);
            plist_price_text = (TextView) itemView.findViewById(R.id.plist_price_text);
            plist_size_text = (TextView) itemView.findViewById(R.id.plist_size_text);
            cart_product_quantity_tv = (TextView) itemView.findViewById(R.id.cart_product_quantity_tv);

        }
    }


}
