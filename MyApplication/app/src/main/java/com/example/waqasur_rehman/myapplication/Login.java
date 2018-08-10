package com.example.waqasur_rehman.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
 * Created by WAQAS UR-REHMAN on 26/11/2015.
 */
public class Login extends BaseActivity {

    protected EditText ET_username;
    private EditText ET_password;
    protected String username;
    protected String inputpassword;
    private final String serverUrl = "link to appropiate webservice script";


    public static final String Default = "N/A";



    public Login() {
        super();


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("mydata", Context.MODE_PRIVATE);
        String  user = sharedPreferences.getString("username", Default);


        if (user != Default) { // if sharedpreference is not empty redirect to main activity and display message

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(this, "Welcome Back " + user.toString(), Toast.LENGTH_LONG).show();

        }

        setContentView(R.layout.login); // set content to view of login.xml




        ET_username = (EditText) findViewById(R.id.username);
        ET_password = (EditText) findViewById(R.id.password);
        Button loginButton = (Button) findViewById(R.id.login);
        Button registerButton = (Button) findViewById(R.id.register);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // runs basic valudation on the login form, to check whether user have entered all the necessary info e.g. username and password
                username = ET_username.getText().toString();
                inputpassword = ET_password.getText().toString();


                if (username.equals("") || inputpassword.equals("")) {
                    Toast.makeText(Login.this, " USERNAME OR PASSWORD REQUIRED", Toast.LENGTH_LONG).show();
                    return;

                }
                if (username.length() <= 1 || inputpassword.length() <= 1) {

                    Toast.makeText(Login.this, "USERNAME OR PASSWORD MUST BE MORE THEN ONE CHARACTER", Toast.LENGTH_LONG).show();
                    return;
                }


                // carries out authentication with the server
                AsyncDataClass asyncRequestObject = new AsyncDataClass();
                asyncRequestObject.execute(serverUrl, username, inputpassword);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getregister = new Intent(Login.this, Register.class);
                startActivity(getregister);
            }
        });



    }

    public void getforgpassword(View view) {

        Intent intent = new Intent(Login.this,forgotPassword.class );
        startActivity(intent);
        finish();



    }


    private class AsyncDataClass extends AsyncTask<String, Void, String> { // when AsynTask method is called it create a HTTP connection and stores the user input in a list  which is then sent to the server for validation


        @Override
        protected String doInBackground(String... params) {

            // set connection timeout
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);
            HttpConnectionParams.setSoTimeout(httpParameters, 5000);

            // setup http client to connect to the webserver

            HttpClient httpClient = new DefaultHttpClient(httpParameters);
            HttpPost httpPost = new HttpPost(params[0]);
            String jresult = "";
            //create a list which will consist of the credidential that need to be authenticated when sent the webserver
            try {
                List<NameValuePair> credidentiallist = new ArrayList<NameValuePair>(2);
                credidentiallist.add(new BasicNameValuePair("username", params[1]));
                credidentiallist.add(new BasicNameValuePair("password", params[2]));
                httpPost.setEntity(new UrlEncodedFormEntity(credidentiallist));

                HttpResponse response = httpClient.execute(httpPost);
                jresult = inputStreamToString(response.getEntity().getContent()).toString();

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

            // check what is returned
            super.onPostExecute(result);
            System.out.println("Resulted Value: " + result);

            if (result.equals("") || result == null) { // check whether the connection have bee successfull to the webserver
                Toast.makeText(Login.this, "CONNECTION TO SERVER FAILED", Toast.LENGTH_LONG).show();
                return;

            }

            int jsonResult = ParsedJsonObject(result);
            if (jsonResult == 0) {  // if return result is empty meaning it doesnt match value on the dastabse, then display error messagef
                Toast.makeText(Login.this, "INVALID USERNAME OR PASSWORD", Toast.LENGTH_LONG).show();
                return;
            }
            if (jsonResult == 1) { // if the return value match the database, store the value and start new activity mainactivity

                SharedPreferences sharedPreferences = getSharedPreferences("mydata", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor= sharedPreferences.edit();
                editor.putBoolean("mydata",true);
                editor.putString("username",ET_username.getText().toString());
                editor.commit();

                Intent intent = new Intent(Login.this, MainActivity.class);
                    Toast.makeText(Login.this, " Login  Successfull", Toast.LENGTH_LONG).show();
                startActivity(intent);
                finish();// finish activity so user can go back to it after login
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
            e.printStackTrace();
        }
        return answer;
    }

    private int ParsedJsonObject(String result) { // handles json operation
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

