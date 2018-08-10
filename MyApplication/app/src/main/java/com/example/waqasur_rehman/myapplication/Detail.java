package com.example.waqasur_rehman.myapplication;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;




public class Detail extends Fragment {
    ImageLoader imageLoader = AppController.getmInstance().getmImageLoader();
    public static String MID = "";
    private static final String URL = "link to appropiate webservice script";// for movie description
    private static final String getemail_url = "link to appropiate webservice script";
    private static final String storepurchase_url = "link to appropiate webservice script";


    public static final String Default = "N/A"; // hold null value;


   public static String title, venue, user;
    Button button;
    public static  String email;
    String subject ="Purchase"+" confirmation";
    int price,available;

    private static String Message;


    View view;

    public Detail() {
        // Required empty public constructor


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle(R.string.Detail);
        // Inflate the layout for this fragment


        view = inflater.inflate(R.layout.fragment_detail2, container, false);
        Bundle bundle = this.getArguments();
        MID = bundle.getString("MovieID");
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("mydata", Context.MODE_PRIVATE);
        user = sharedPreferences.getString("username", Default);


        makerequest();
        getemail();

        button = (Button) view.findViewById(R.id.payment);

        {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                Log.e("message is ", Message.toString());
                    if (available >= 1) {
                        if (!email.isEmpty()) {
                            SendMail mail = new SendMail(getContext(), email, subject, Message);
                            mail.execute();
                            storpurchase();


                        }

                    }else{

                        Toast.makeText(getActivity(), "Sorry No more Ticket Available", Toast.LENGTH_SHORT).show();

                    }

                }
            });


            return view;

        }
    }


    private void getemail() {
        StringRequest postRequest = new StringRequest(Request.Method.POST, getemail_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        // response
                        Log.d("Response", response);

                        try {
                            JSONArray data = new JSONArray(response);

                            for (int i = 0; i < data.length(); i++) {

                                JSONObject obj = data.getJSONObject(i);


                                email = obj.getString("email");


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


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
    }


    private void makerequest() {


        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        // response
                        Log.d("Response", response);

                        try {
                            JSONArray data = new JSONArray(response);

                            for (int i = 0; i < data.length(); i++) {

                                JSONObject obj = data.getJSONObject(i);

                                title = obj.getString("Title");
                                TextView Title = (TextView) view.findViewById(R.id.selected_title);
                                Title.setText(title);

                                 available = obj.getInt("Available");
                                TextView Available = (TextView) view.findViewById(R.id.selected_available);
                                Available.setText(String.valueOf(available));

                                venue = obj.getString("Venue");
                                TextView Venue = (TextView) view.findViewById(R.id.selected_venue);
                                Venue.setText(venue);

                                String Genre = obj.getString("Genre");
                                TextView genre = (TextView) view.findViewById(R.id.selected_genre);
                                genre.setText(Genre);

                                String description = obj.getString("Description");
                                TextView Description = (TextView) view.findViewById(R.id.selected_description);
                                Description.setText(description);

                                 price = obj.getInt("Price");
                                TextView Price = (TextView) view.findViewById(R.id.selected_price);
                                Price.setText(String.valueOf(price));
                                double rating = obj.getDouble("rating");
                                RatingBar Rating = (RatingBar) view.findViewById(R.id.rating);
                                Rating.setEnabled(false);
                                Rating.setMax(10);
                                Rating.setStepSize(0.01f);
                                Rating.invalidate();
                                Rating.setRating((float) rating);

                                Drawable drawable = Rating.getProgressDrawable();
                                drawable.setColorFilter(Color.parseColor("#0064A8"), PorterDuff.Mode.SRC_ATOP);


                                String Image = obj.getString("Image");
                                if (imageLoader == null)
                                    imageLoader = AppController.getmInstance().getmImageLoader();

                                NetworkImageView imageView = (NetworkImageView) view.findViewById(R.id.selected_image);
                                imageView.setImageUrl(Image, imageLoader);

                                Message = " You have purchased " + title + ". The Screening is at "+ venue + " and Your  payment of £ " +String.valueOf(price) +" has been received. " + " Thank you for using Ticket Booking System";




                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


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
                params.put("ID", MID.toString());
                return params;
            }
        };
        AppController.getmInstance().addToRequesQueue(postRequest);
    }

    public void storpurchase(){ // store purchased item in database when called

        StringRequest stringRequest = new StringRequest(Request.Method.POST, storepurchase_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("title",title);
                params.put("venue",venue);
                params.put("username", user);
                params.put("price", String.valueOf(price));

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }




    }









