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
public class changeUsername extends Fragment {

    private EditText email;
    private EditText newusername;
    Button saveuser;
    protected String inputcurrentemail;
    protected String inputnewusername;
    String user;
    public static final String Default = "N/A"; // hold null value;

    private final String serverUrl = "link to appropiate webservice script"; // changing user name


    public changeUsername() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        getActivity().setTitle(R.string.account);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("mydata", Context.MODE_PRIVATE);
        user = sharedPreferences.getString("username", Default);

        View view = inflater.inflate(R.layout.fragment_change_username, container, false);

        email = (EditText) view.findViewById(R.id.current_email);
        newusername = (EditText) view.findViewById(R.id.new_username);

        saveuser = (Button) view.findViewById(R.id.user_change_button);

        saveuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputcurrentemail = email.getText().toString();

                inputnewusername = newusername.getText().toString();

                if (inputcurrentemail.equals("") || inputnewusername.equals("")) {
                    Toast.makeText(getActivity(), "Email AND UserName REQUIRED", Toast.LENGTH_LONG).show();
                    return;
                }
                if (inputcurrentemail.length() <= 1 || inputnewusername.length() <= 1) {
                    Toast.makeText(getActivity(), "EMAIL OR USERNAME MUST BE MORE THEN ONE CHARACTER", Toast.LENGTH_LONG).show();
                    return;
                }
                // request authentication with remote server4
                AsyncDataClass asyncRequestObject = new AsyncDataClass();
                asyncRequestObject.execute(serverUrl, inputcurrentemail,inputnewusername ,user);

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
                credidentiallist.add(new BasicNameValuePair("currentEmail", params[1]));
                credidentiallist.add(new BasicNameValuePair("newusername", params[2]));
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
                Toast.makeText(getActivity(), "USERNAME DOESNT EXIST", Toast.LENGTH_LONG).show();
                return;
            }
            if (jsonResult == 1) {

                SharedPreferences sharedPreferences =getActivity().getSharedPreferences("mydata", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor= sharedPreferences.edit();
                editor.putBoolean("mydata",true);
                editor.putString("username",newusername.getText().toString());
                editor.commit();

                Intent intent = new Intent(getContext(), Login.class);
                intent.putExtra("MESSAGE", "USERNAME CHANGED SUCCESSFULLY ");
                Toast.makeText(getActivity(), " USERNAME CHANGED SUCCESSFULLY", Toast.LENGTH_LONG).show();
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




