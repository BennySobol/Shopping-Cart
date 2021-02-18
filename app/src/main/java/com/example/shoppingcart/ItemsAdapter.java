package com.example.shoppingcart;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;


public class ItemsAdapter extends ArrayAdapter<Item> {

    private boolean type; // true - in cart ; false - to order

    public ItemsAdapter(Context context, ArrayList<Item> items, boolean type) {
        super(context, 0, items);
        this.type = type;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Item item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            if(type)
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_to_order, parent, false);
            else
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_in_cart, parent, false);

        }
        // Lookup view for data population
        TextView productName = convertView.findViewById(R.id.product_name);
        TextView productCategory = convertView.findViewById(R.id.product_category);
        TextView productPrice = convertView.findViewById(R.id.product_price);
        TextView totalPrice = convertView.findViewById(R.id.product_total_price);
        ImageView productImage = convertView.findViewById(R.id.product_image);
        TextView productUserAmount = convertView.findViewById(R.id.product_user_amount);

        // Populate the data into the template view using the data object
        productName.setText(item.getName());
        productCategory.setText(item.getCategory());
        productPrice.setText(String.format("%.1f", item.getPricePerUnite()));
        productUserAmount.setText(String.valueOf(item.getUserAmount()));
        totalPrice.setText(String.format("%.1f",item.getTotalPrice()));

        Bitmap image = item.getImageByte();
        if(image != null)
            productImage.setImageBitmap(image);
        else
            productImage.setImageDrawable(getContext().getResources().getDrawable(R.drawable.default_product_image));

        // Return the completed view to render on screen
        return convertView;
    }
}