package com.example.shoppingcart;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewDialog {

    public void showDialog(Activity activity, Item item) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.notes_dialog);


        TextView productName = dialog.findViewById(R.id.product_name);
        TextView productNote = dialog.findViewById(R.id.product_notes);
        ImageView productImage = dialog.findViewById(R.id.product_image);

        productName.setText(item.getName());
        productNote.setText(item.getNotes());
        Bitmap image = item.getImageByte();
        if(image != null)
            productImage.setImageBitmap(image);
        else
            productImage.setImageDrawable(productImage.getResources().getDrawable(R.drawable.default_product_image));

        Button dialogButton = dialog.findViewById(R.id.btn_close_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }
}