package com.example.waqasur_rehman.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by WAQAS UR-REHMAN on 25/04/2016.
 */
public class SendMail extends AsyncTask<Void,Void,Void> {


    private Context context;
    private Session session;

    //Information to send email
    private String email;
    private String subject;
    private String message;

    //Progressdialog to show while sending email
    private ProgressDialog progressDialog;


    public SendMail(Context context, String email, String subject, String message){

        this.context= context;
        this.email=email;
        this.message=message;
        this.subject=subject;


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog= ProgressDialog.show(context," Sending email","Please wait...",false,false);
    }

    @Override
    protected Void doInBackground(Void... params) {

        Properties  properties = new Properties();

        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");

            session = Session.getDefaultInstance(properties, new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){

                return  new PasswordAuthentication(EmailConfiguration.EMAIL, EmailConfiguration.PASSWORD);
            }


        });
        try{
            MimeMessage mm = new MimeMessage(session);

            //configuring sender address
            mm.setFrom(new InternetAddress(EmailConfiguration.EMAIL));
            //Adding  the reciever address
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            //adding subject of the mail
            mm.setSubject(subject);
            //adding the actual feedback message
            mm.setText(message);

            //Sending email
            Transport.send(mm);


        }catch (MessagingException e) {
                e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        progressDialog.dismiss();
        Toast.makeText(context, "You should Recieve a confirmation email... Thank you...", Toast.LENGTH_LONG).show();


    }

}
