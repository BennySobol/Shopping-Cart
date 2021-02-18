package com.example.shoppingcart;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ProgressDialog loading;
    public static ArrayList<Item> arrayOfItems;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.list_item);
        getItems();
    }

    private void getItems() {
        if(arrayOfItems != null)
        {
            // Create the adapter to convert the array to views
            ItemsAdapter adapter = new ItemsAdapter(this, arrayOfItems, true);
            // Attach the adapter to a ListView
            listView.setAdapter(adapter);
            return;
        }
        loading =  ProgressDialog.show(this,"טוען","חכה בבקשה",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec?id=1insBTcnecfcJJn-bGFjBYpQ1QQhus98-4e-NhrjdcGY&sheet=shopping%20cart",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseItems(response);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error", error.getMessage());
                    }
                }
        );
        final int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }


    private void parseItems(String jsonResposnce) {

        // Construct the data source
        arrayOfItems = new ArrayList<Item>();

        try {
            JSONObject jobj = new JSONObject(jsonResposnce);
            JSONArray jarray = jobj.getJSONArray("shopping cart");

            for (int i = 0; i < jarray.length(); i++)
            {
                JSONObject jo = jarray.getJSONObject(i);
                Item item = new Item(jo.getString("NAME"),  jo.getString("CATEGORY"),  jo.getString("PRICE"),  jo.getString("IMAGE_BASE64"), jo.getString("NOTES"),  jo.getString("AMOUNT"));
                arrayOfItems.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("JSONException",e.getMessage());
        }

        // Create the adapter to convert the array to views
        ItemsAdapter adapter = new ItemsAdapter(this, arrayOfItems, true);

        // Attach the adapter to a ListView
        listView.setAdapter(adapter);
        loading.dismiss();
    }

    public void showNotes(View view) {
        ViewDialog alert = new ViewDialog();
        View parentRow = (View)view.getParent();

        final int position = listView.getPositionForView(parentRow);

        alert.showDialog(this, arrayOfItems.get(position));
    }

    public void changeValue(View view) {
        View parentRow = (View)view.getParent().getParent();
        final int position = listView.getPositionForView(parentRow);
        Item item = arrayOfItems.get(position);
        TextView totalPrice = parentRow.findViewById(R.id.product_total_price);
        TextView productUserAmount = parentRow.findViewById(R.id.product_user_amount);
        TextView TotalPrice = findViewById(R.id.total_price);

        if(view.getId() == findViewById(R.id.btn_minus).getId()) {
            if(item.getUserAmount() == 0)
                return;
            item.subUserAmount();
        }
        else
            item.addUserAmount();

        TotalPrice.setText(String.format("%.1f", getTotalPrice()));
        productUserAmount.setText(String.valueOf(item.getUserAmount()));
        totalPrice.setText(String.format("%.1f", item.getTotalPrice()));
    }

    private double getTotalPrice() {
        double totalPrice = 0;
        for(Item item : arrayOfItems)
            totalPrice += item.getTotalPrice();
        return totalPrice;
    }

    public void orderConfirmation(View view) {
        Intent intent = new Intent(this, OrderConfirmation.class);
        startActivity(intent);
    }
}