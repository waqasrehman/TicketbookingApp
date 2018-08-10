package com.example.waqasur_rehman.myapplication;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class purchasehistory extends Fragment {

    View view;
    public static final String Default = "N/A"; // hold null value;
    private static final String getpurchase_url = "link to appropiate webservice script";

    private ArrayList<itempurchased> array = new ArrayList<itempurchased>();


    String user;
    purchaseAdapter adapter;


    public purchasehistory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        view = inflater.inflate(R.layout.fragment_purchasehistory, container, false);


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("mydata", Context.MODE_PRIVATE);
        user = sharedPreferences.getString("username", Default);


            ListView listView = (ListView)view.findViewById(R.id.displayed_purchased);
       adapter = new purchaseAdapter(this,array);
        listView.setAdapter(adapter);






        StringRequest postRequest = new StringRequest(Request.Method.POST, getpurchase_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        // response
                        Log.d("Response", response);

                        try {
                            JSONArray data = new JSONArray(response);

                            for (int i = 0; i < data.length(); i++) {

                                JSONObject obj = data.getJSONObject(i);

                                itempurchased item = new itempurchased();

                                item.setTitle(obj.getString("title"));
                                item.setVenue(obj.getString("venue"));
                                item.setPrice(obj.getInt("price"));

                                    array.add(item);






                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        adapter.notifyDataSetChanged();



                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", "");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", user.toString());
                return params;
            }
        };
        AppController.getmInstance().addToRequesQueue(postRequest);
        return view;

    }

}




