package com.example.shoppingcart;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

public class CartCheckout extends AppCompatActivity {

    private EditText personNameEditText;
    private EditText emailAddressEditText;
    private EditText phoneEditText;
    private EditText postalAddressEditText;

    private String emailAddressText;
    private String personNameText;
    private String phoneText;
    private String postalAddressText;

    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_checkout);

        personNameEditText = findViewById(R.id.PersonName);
        emailAddressEditText = findViewById(R.id.EmailAddress);
        phoneEditText = findViewById(R.id.PhoneNumber);
        postalAddressEditText = findViewById(R.id.PostalAddress);

        setEditTextsErrorCheck();

        // load previous values from shared preferences
        sharedPref = getPreferences(Context.MODE_PRIVATE);
        emailAddressText = sharedPref.getString("emailAddress", "");
        personNameText = sharedPref.getString("personName", "");
        phoneText = sharedPref.getString("phone", "");
        postalAddressText = sharedPref.getString("postalAddress", "");

        personNameEditText.setText(emailAddressText);
        emailAddressEditText.setText(personNameText);
        phoneEditText.setText(phoneText);
        postalAddressEditText.setText(postalAddressText);
    }


    private void setEditTextsErrorCheck() {

        // set errors
        personNameEditText.addTextChangedListener(new TextWatcher()  {
            @Override
            public void afterTextChanged(Editable s)  {
                if (personNameEditText.getText().toString().length() <= 0)
                    personNameEditText.setError("הכנס את שמך המלא");
                else
                    personNameEditText.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        });

        emailAddressEditText.addTextChangedListener(new TextWatcher()  {
            @Override
            public void afterTextChanged(Editable s)  {
                if (emailAddressEditText.getText().toString().length() <= 0)
                    emailAddressEditText.setError("הכנס כתובת מייל תקינה"); // TO DO
                else
                    emailAddressEditText.setError(null);
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        });

        phoneEditText.addTextChangedListener(new TextWatcher()  {
            @Override
            public void afterTextChanged(Editable s)  {
                if (phoneEditText.getText().toString().length() < 9)
                    phoneEditText.setError("הכנס מספר טלפון תקין");
                else
                    phoneEditText.setError(null);
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        });

        postalAddressEditText.addTextChangedListener(new TextWatcher()  {
            @Override
            public void afterTextChanged(Editable s)  {
                if (postalAddressEditText.getText().toString().length() <= 0)
                    postalAddressEditText.setError("הכנס מספר טלפון תקין"); // TO DO
                else
                    postalAddressEditText.setError(null);
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        });
    }

    private void sendEmail()
    {
        String email = "banisobol@gmail.com";
        String subject = "הזמנה חדשה";
        String message = getMessage();
        EmailSender emailSender = new EmailSender(CartCheckout.this, email, subject, message);
        emailSender.execute();
    }

    private String getMessage()
    {
        String message = "פרטים אודות הלקוח:" + "\n";
        message += String.format("כתובת מייל: %s", emailAddressText) + "\n";
        message += String.format("שם לקוח: %s", personNameText) + "\n";
        message += String.format("טלפון: %s", phoneText) + "\n";
        message += String.format("כתובת: %s", postalAddressText) + "\n\n\n";
        message += "פרטי ההזמנה:" + "\n";

        for(Item item : OrderConfirmation.arrayOfItemsToOrder)
            message += String.format("מוצר: %s, כמות: %d", item.getName(), item.getUserAmount()) + "\n";
        message += "\n" + String.format("מחיר כולל: %d", 12345) + "\n"; //TO DO

        return message;
    }

    public void order(View view) {
        if(phoneEditText.getError() != null || emailAddressEditText.getError() != null || personNameEditText.getError() != null || postalAddressEditText.getError() != null )
            return;

        sharedPref = getPreferences(Context.MODE_PRIVATE);
        // update Text Strings values
        emailAddressText = String.valueOf(emailAddressEditText.getText());
        personNameText = String.valueOf(personNameEditText.getText());
        phoneText = String.valueOf(phoneEditText.getText());
        postalAddressText = String.valueOf(postalAddressEditText.getText());

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("emailAddress", emailAddressText);
        editor.putString("personName", personNameText);
        editor.putString("phone", phoneText);
        editor.putString("postalAddress", postalAddressText);
        editor.apply();

        sendEmail();
    }
}