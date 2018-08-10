package com.example.waqasur_rehman.myapplication;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

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


public class CategoryResult extends Fragment {

    private static final String URL = "link to appropiate webservice script";//get category of movies
    private ProgressDialog dialog;
    private ArrayList<Item> array = new ArrayList<Item>();

    public String title, image, description, venue, Genre;
    public int price, ID, year;
    public int rate;
    String prams = "";


    private ListView listView;
    private AdapterCategory adapter;

    SearchView text;

    String MID;
    String movieID;

    public CategoryResult() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        MID = bundle.getString("Genre");

        getActivity().setTitle(MID+" "+"Movies");


        //setContentView(R.layout.searchmovies);
        View view = inflater.inflate(R.layout.fragment_category_result, container, false);
        listView = (ListView) view.findViewById(R.id.list_category_movies);
        adapter = new AdapterCategory(this, array);
        listView.setAdapter(adapter);

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        text = (SearchView) view.findViewById(R.id.searchData);
        text.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                adapter.getFilter().filter(newText.toString());
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ID = ((TextView) view.findViewById(R.id.ID)).getText().toString();

                Bundle bundle = new Bundle();
                bundle.putString("MovieID", ID);
                Detail frag = new Detail();
                frag.setArguments(bundle);
                Fragment categoryhMovie = new Fragment();
                FragmentTransaction Fragment = getFragmentManager().beginTransaction();
                Fragment.replace(R.id.container, frag);
                Fragment.add(R.id.container, categoryhMovie, "category movie");
                Fragment.addToBackStack("addcategoryMovie");
                Fragment.commit();
                adapter.filteritems.clear();
            }
        });




        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        dialog.show();
        makerequest();


        // Inflate the layout for this fragment
        return view;
    }




    private void makerequest(  ) {


        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override

                    public void onResponse(String response) {
                        hideDialog();

                        // response
                        Log.d("Response", response);

                        try {
                            JSONArray data = new JSONArray(response);

                            for (int i = 0; i < data.length(); i++) {
                                JSONObject obj = data.getJSONObject(i);
                                Item item = new Item();
                                item.setTitle(obj.getString("Title"));
                                item.setImage(obj.getString("Image"));
                                item.setID(obj.getInt("MovieID"));
                                item.setYear(obj.getInt("year"));
                                item.setGenre(obj.getString("Genre"));
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
                params.put("Category", MID.toString());

                Log.e("Prams Value", params.toString());

                return params;
            }
        };
        AppController.getmInstance().addToRequesQueue(postRequest);

    }


    public void hideDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }



}


