package com.example.shoppingcart;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender extends AsyncTask{
    private Context context;
    private Session session;
    private String email;
    private String subject;
    private String message;
    private ProgressDialog progressDialog;

    public EmailSender(Context context, String email, String subject, String message) {
        this.context = context;
        this.email = email;
        this.subject = subject;
        this.message = message;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");

        session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("webEmailSender1@gmail.com", "webEmailSender123");
            }
        });

        try {
            MimeMessage mm = new MimeMessage(session);
            mm.setFrom(new InternetAddress("webEmailSender1@gmail.com"));
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            mm.setSubject(subject);
            mm.setText(message);
            Transport.send(mm);
        }
        catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
       progressDialog = ProgressDialog.show(context,"שולח הזמנה","בבקשה המתן...",false,false);
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        progressDialog.dismiss();
        Toast.makeText(context,"ההזמנה נשלחה",Toast.LENGTH_LONG).show();
    }

}