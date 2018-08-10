package com.example.waqasur_rehman.myapplication;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * A simple {@link Fragment} subclass.
 */
public class AccountInfo extends Fragment {

    private EditText password;
    private EditText currentpass;
    Button savepass;
    protected String inputcurrentpass;
    protected String inputnewpassword;
    String user;
    public static final String Default = "N/A"; // hold null value;

    private final String serverUrl = "link to appropiate webservice script"; // changing password php script


    public AccountInfo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

            getActivity().setTitle(R.string.account);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("mydata", Context.MODE_PRIVATE);
        user = sharedPreferences.getString("username", Default);

        View view = inflater.inflate(R.layout.fragment_account_info, container, false);

        password = (EditText) view.findViewById(R.id.new_username);
        currentpass = (EditText) view.findViewById(R.id.current_email);

        savepass = (Button) view.findViewById(R.id.pass_change_button);

        savepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputcurrentpass = currentpass.getText().toString();

                inputnewpassword = password.getText().toString();

                if (inputcurrentpass.equals("") || inputnewpassword.equals("")) {
                    Toast.makeText(getActivity(), "USERNAME OR PASSWORD REQUIRED", Toast.LENGTH_LONG).show();
                    return;
                }
                if (inputcurrentpass.length() <= 1 || inputnewpassword.length() <= 1) {
                    Toast.makeText(getActivity(), "USERNAME OR PASSWORD MUST BE MORE THEN ONE CHARACTER", Toast.LENGTH_LONG).show();
                    return;
                }
                // request authentication with remote server4
                AsyncDataClass asyncRequestObject = new AsyncDataClass();
                asyncRequestObject.execute(serverUrl, inputcurrentpass,inputnewpassword ,user);

            }
        });

        return view;


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
                credidentiallist.add(new BasicNameValuePair("currentpass", params[1]));
                credidentiallist.add(new BasicNameValuePair("newpassword", params[2]));
                credidentiallist.add(new BasicNameValuePair("username", params[3]));
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
                Toast.makeText(getActivity(), "CONNECTION TO SERVER FAILED", Toast.LENGTH_LONG).show();
                return;
            }

            int jsonResult = ParsedJsonObject(result);
            if (jsonResult == 0) {
                Toast.makeText(getActivity(), "  EMAIL DOESNT EXIST", Toast.LENGTH_LONG).show();
                return;
            }
            if (jsonResult == 1) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("MESSAGE", "PASSWORD CHANGED SUCCESSFULLY ");
                Toast.makeText(getActivity(), " PASSWORD CHANGED SUCCESSFULLY", Toast.LENGTH_LONG).show();
                startActivity(intent);

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




