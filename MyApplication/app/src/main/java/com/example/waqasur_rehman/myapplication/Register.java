package com.example.waqasur_rehman.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WAQAS UR-REHMAN on 25/12/2015.
 */
public class Register extends BaseActivity {

    protected EditText username;
    private EditText password;
    private EditText email;
    protected String inputemail;
    protected String inputpassword;
    String subject = "Registration Confirmation";
    String Message;

    protected String inputusername;
    private final String serverUrl = "link to appropiate webservice script ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);


        username = (EditText)findViewById(R.id.regName);
        email = (EditText)findViewById(R.id.regEmail);
        password = (EditText)findViewById(R.id.regpassword);

        Button regsubmit = (Button)findViewById(R.id.buttonregister);

        regsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputusername = username.getText().toString();
                inputemail = email.getText().toString();

                inputpassword = password.getText().toString();

                if(inputusername.equals("") || inputpassword.equals("") || inputemail.equals("")){
                    Toast.makeText(Register.this, "USERNAME OR PASSWORD REQUIRED", Toast.LENGTH_LONG).show();
                    return;
                }
                if(inputusername.length() <= 1 || inputpassword.length() <= 1){
                    Toast.makeText(Register.this, "USERNAME OR PASSWORD MUST BE MORE THEN ONE CHARACTER", Toast.LENGTH_LONG).show();
                    return;
                }




                // request authentication with remote server4
                AsyncDataClass asyncRequestObject = new AsyncDataClass();
                asyncRequestObject.execute(serverUrl, inputusername, inputpassword, inputemail);



            }
        });




    }


    private class AsyncDataClass extends AsyncTask<String , Void , String> {


        @Override
        protected String doInBackground(String... params) {

            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);
            HttpConnectionParams.setSoTimeout(httpParameters, 5000);

            HttpClient httpClient = new DefaultHttpClient(httpParameters);
            HttpPost httpPost = new HttpPost(params[0]);

            String jresult = "";
            try {
                List<NameValuePair> credidentiallist = new ArrayList<NameValuePair>(2);
                credidentiallist.add(new BasicNameValuePair("username", params[1]));
                credidentiallist.add(new BasicNameValuePair("password", params[2]));
                credidentiallist.add(new BasicNameValuePair("email", params[3]));
                httpPost.setEntity(new UrlEncodedFormEntity(credidentiallist));



                HttpResponse response = httpClient.execute(httpPost);
                jresult = inputStreamToString(response.getEntity().getContent()).toString();
                System.out.println("Returned Json object " + jresult.toString());


            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }



            return jresult;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);





            System.out.println("result Value: " + result);
            if(result.equals("") || result == null){
                Toast.makeText(Register.this, "CONNECTION TO SERVER FAILED", Toast.LENGTH_LONG).show();
                return;
            }

            int jsonResult = ParsedJsonObject(result);
            if(jsonResult == 0){

                Toast.makeText(Register.this, " INVALID EMAIL OR PASSWORD", Toast.LENGTH_LONG).show();
                return;
            }
            if(jsonResult == 1){
                Message = "Thank you for Registering With Ticket Booking System." + " Your Username is " + inputusername + " And your Password is " + inputpassword + ".";
                SendRegEmail mail = new SendRegEmail(Register.this, inputemail, subject, Message);
                mail.execute();
                Intent intent = new Intent(Register.this, Login.class);
                intent.putExtra("MESSAGE", "YOUR REGISTRATION HAS BEEN SUCCESSFULL");
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);





            }


        }
    }



    private StringBuilder inputStreamToString(InputStream content) {
        String rLine = "";
        StringBuilder answer = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(content));
        try {
            while ((rLine = br.readLine()) != null) {
                answer.append(rLine);
            }
            br.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return answer;

    }


    private int ParsedJsonObject(String result) {

        JSONObject resultObject = null;

        int returnedResult = 0;
        try {
            resultObject = new JSONObject(result);
            returnedResult = resultObject.getInt("success");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnedResult;
    }
}
