package com.example.waqasur_rehman.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
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
 * Created by WAQAS UR-REHMAN on 22/04/2016.
 */
public class forgotPassword extends BaseActivity{

    private EditText newpassword;
    private EditText et_email;
    protected String inputemail;
    protected String inputnewpassword;
    public static final String Default = "N/A"; // hold null value;


    private final String serverUrl = "link to appropiate webservice script";




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_forgot_password);

        this.setTitle(R.string.Retrieve);

        et_email = (EditText)findViewById(R.id.current_email);
        newpassword = (EditText) findViewById(R.id.newpassword);





    }

    public void getforgpassword(View view) {

        inputemail = et_email.getText().toString();

        inputnewpassword = newpassword.getText().toString();

        if (inputemail.equals("") || inputnewpassword.equals("")) {
            Toast.makeText(forgotPassword.this, "Password AND Email REQUIRED", Toast.LENGTH_LONG).show();
            return;
        }
        if (inputemail.length() <= 1 || inputnewpassword.length() <= 1) {
            Toast.makeText(forgotPassword.this, "Password AND Email MUST BE MORE THEN ONE CHARACTER", Toast.LENGTH_LONG).show();
            return;
        }
        // request authentication with remote server4
        AsyncDataClass asyncRequestObject = new AsyncDataClass();
        asyncRequestObject.execute(serverUrl, inputemail,inputnewpassword);
    }


    private class AsyncDataClass extends AsyncTask<String, Void, String> {


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
                credidentiallist.add(new BasicNameValuePair("email", params[1]));
                credidentiallist.add(new BasicNameValuePair("newpassword", params[2]));
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
            if (result.equals("") || result == null) {
                Toast.makeText(forgotPassword.this, "CONNECTION TO SERVER FAILED", Toast.LENGTH_LONG).show();
                return;
            }

            int jsonResult = ParsedJsonObject(result);
            if (jsonResult == 0) {
                Toast.makeText(forgotPassword.this, " COULD NOT FIND  EMAIL", Toast.LENGTH_LONG).show();
                return;
            }
            if (jsonResult == 1) {
                Intent intent = new Intent(forgotPassword.this, Login.class);
                intent.putExtra("MESSAGE", "PASSWORD CHANGED SUCCESSFULLY ");
                Toast.makeText(forgotPassword.this, "Password Retrieved Successfully", Toast.LENGTH_LONG).show();
                startActivity(intent);
                finish();

            }
        }
    }

    // create a string
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

    // PARSE JSON results
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
