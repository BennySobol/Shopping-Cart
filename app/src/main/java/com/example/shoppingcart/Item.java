package com.example.shoppingcart;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Item {
    private String name;
    private String category;
    private double pricePerUnite;
    private Bitmap imageByte;
    private String notes;
    private int amountInStorage;
    private int userAmount;

    public double getTotalPrice()
    {
        return (this.userAmount * this.pricePerUnite);
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPricePerUnite() {
        return pricePerUnite;
    }

    public Bitmap getImageByte() {
        return imageByte;
    }

    public String getNotes() {
        return notes;
    }

    public int getAmountInStorage() {
        return amountInStorage;
    }

    public void setAmountInStorage(int amountInStorage) {
        this.amountInStorage = amountInStorage;
    }

    public int getUserAmount() {
        return userAmount;
    }

    public void setUserAmount(int userAmount)
    {
        this.userAmount = userAmount;
    }

    public void addUserAmount()
    {
        this.userAmount++;
    }

    public void subUserAmount() {
        this.userAmount--;
    }

    public Item(String name, String category, String pricePerUnite, String image64, String notes, String amountInStorage) {
        this.name = name;
        this.category = category;
        this.pricePerUnite = Double.parseDouble(pricePerUnite);

        this.notes = notes;
        this.amountInStorage = Integer.parseInt(amountInStorage);
        this.userAmount = 0;

        byte[] decodedString = Base64.decode(image64, Base64.DEFAULT);
        this.imageByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }
}
