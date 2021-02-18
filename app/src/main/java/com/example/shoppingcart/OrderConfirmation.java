package com.example.shoppingcart;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class OrderConfirmation extends AppCompatActivity {

    private ListView listView;
    public static ArrayList<Item> arrayOfItemsToOrder;
    private ItemsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_confirmation);

        arrayOfItemsToOrder = new ArrayList<Item>();

        for(Item item : MainActivity.arrayOfItems)
            if(item.getUserAmount() != 0)
                arrayOfItemsToOrder.add(item);

        // Create the adapter to convert the array to views
        adapter = new ItemsAdapter(this, arrayOfItemsToOrder, false);
        listView = (ListView)findViewById(R.id.list_item);
        // Attach the adapter to a ListView
        listView.setAdapter(adapter);
        setTotalPrice();
    }

    public void editOrder(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void checkout(View view) {
        Intent intent = new Intent(this, CartCheckout.class);
        startActivity(intent);
    }

    public void deleteItem(View view) {
        View parentRow = (View)view.getParent();
        final int position = listView.getPositionForView(parentRow);
        Item item = arrayOfItemsToOrder.get(position);
        adapter.remove(item);
        adapter.notifyDataSetChanged();

        item.setUserAmount(0);
        setTotalPrice();
    }

    private void setTotalPrice() {
        double totalPrice = 0;
        for(Item item : arrayOfItemsToOrder)
            totalPrice += item.getTotalPrice();

        TextView TotalPrice = findViewById(R.id.total_price);
        TotalPrice.setText(String.format("%.1f", totalPrice));
    }
}