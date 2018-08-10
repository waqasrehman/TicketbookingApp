package com.example.waqasur_rehman.myapplication;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class Home extends Fragment {

    private static final String url = "link to appropiate webservice script";
    private ProgressDialog dialog;
    private ArrayList<Item> array = new ArrayList<Item>();




    private ListView listView;
    private Adapter adapter;

    SearchView text;

    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        getActivity().setTitle(R.string.Home);


        //setContentView(R.layout.searchmovies);
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        listView = (ListView) view.findViewById(R.id.list_movies);
        adapter = new Adapter(this,array);
        listView.setAdapter(adapter);
        adapter.filteritems.clear();

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        text =(SearchView)view.findViewById(R.id.searchData);
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




        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        dialog.show();
        makerequest();


        // Inflate the layout for this fragment
        return view;
    }




    private void makerequest() {

        //Creat volley request obj
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                hideDialog();
                //parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        Item item = new Item();
                        item.setTitle(obj.getString("Title"));
                        item.setImage(obj.getString("Image"));
                        item.setID(obj.getInt("MovieID"));
                        item.setYear(obj.getInt("year"));
                        item.setRate(obj.getDouble("rating"));
                        item.setGenre(obj.getString("Genre"));
                        array.add(item);
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getmInstance().addToRequesQueue(jsonArrayRequest);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                view.setSelected(true);

                String ID = ((TextView) view.findViewById(R.id.ID)).getText().toString();

                Bundle bundle = new Bundle();
                bundle.putString("MovieID",ID);
                Detail frag =new Detail();
                frag.setArguments(bundle);
                Fragment searchMovie = new Fragment();
                FragmentTransaction Fragment = getFragmentManager().beginTransaction();
                Fragment.replace(R.id.container, frag);
                Fragment.add(R.id.container,searchMovie,"searchMovie");
                Fragment.addToBackStack("addsearcMovie");
                Fragment.commit();
                adapter.filteritems.clear();// filter the filteritems list array to avoid duplicate data on back stack

            }

            @SuppressWarnings("unused")
            public void onClick(View v) {
            }

            ;
        });

    }

    public void hideDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }


}


