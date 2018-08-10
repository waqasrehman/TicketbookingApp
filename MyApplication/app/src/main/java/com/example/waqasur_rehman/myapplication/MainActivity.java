package com.example.waqasur_rehman.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends BaseActivity {

     DrawerLayout drawerLayout; // instance of drawerlayouts
    NavigationView  navigationView, navigationcategory; // instance of Navigation view

    ActionBarDrawerToggle actionbarDrawertoggle; // instance of actionbar drawer toggle

    FragmentTransaction fragmentTransaction; // instance of fragment transaction
    String user; // declare global string user

    public static final String Default = "N/A"; // hold null value;

    TextView displayusername;
    Adapter adapter;
    AdapterCategory adapterCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //holds user info e.g. username , password and email
        SharedPreferences sharedPreferences = getSharedPreferences("mydata", Context.MODE_PRIVATE);
        user = sharedPreferences.getString("username", Default);

        this.setTitle(R.string.Home);

        if (user == null) { // if sharedpereference in empty redirect user to login page

            Intent intent = new Intent(this, Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

            Toast.makeText(this, "No account logged in", Toast.LENGTH_LONG).show();

        }


        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionbarDrawertoggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);


        drawerLayout.setDrawerListener(actionbarDrawertoggle);


        final ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        navigationView = (NavigationView) findViewById(R.id.NavigationView);
        navigationcategory =(NavigationView)findViewById(R.id.NavigationCategory);

        View view = navigationView.getHeaderView(0);
        displayusername = (TextView) view.findViewById(R.id.display_user);
        displayusername.setText(user);



        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container, new Home());
        fragmentTransaction.commit();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.Home_main:
                        Fragment home = new Fragment();
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container, new Home());
                        fragmentTransaction.add(R.id.container,home,"Home");
                        fragmentTransaction.addToBackStack("addHome");
                        fragmentTransaction.commit();
                        item.setChecked(true);
                        drawerLayout.closeDrawers();

                        break;


                    case R.id.account:
                        Fragment Account = new Fragment();

                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container, new AccountInfo());
                        fragmentTransaction.add(R.id.container,Account,"Account");
                        fragmentTransaction.addToBackStack("addAccount");
                        fragmentTransaction.commit();
                        item.setChecked(true);
                        drawerLayout.closeDrawers();

                        break;

                    case R.id.changeusername:
                        Fragment changeusername = new Fragment();

                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container, new changeUsername());
                        fragmentTransaction.add(R.id.container,changeusername,"ChangeUsername");
                        fragmentTransaction.addToBackStack("addChangeusername");
                        fragmentTransaction.commit();
                        item.setChecked(true);
                        drawerLayout.closeDrawers();

                        break;

                    case R.id.feedback:
                        Fragment feedback = new Fragment();

                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container, new Feedback());
                        fragmentTransaction.add(R.id.container,feedback,"feedback");
                        fragmentTransaction.addToBackStack("addFeedback");
                        fragmentTransaction.commit();

                        item.setChecked(true);
                        drawerLayout.closeDrawers();

                        break;


                    case R.id.history:
                    Fragment history = new Fragment();

                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.container, new purchasehistory());
                    fragmentTransaction.add(R.id.container,history,"history");
                    fragmentTransaction.addToBackStack("addhistory");
                    fragmentTransaction.commit();

                    item.setChecked(true);
                    drawerLayout.closeDrawers();

                        break;

                    case R.id.logout:

                        SharedPreferences sharedPreferences = getSharedPreferences("mydata", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        //editor.putBoolean("mydata", false);
                        editor.clear();
                        editor.commit();

                        Intent intent = new Intent(getBaseContext(), Login.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        //fragmentTransaction.addToBackStack(null);

                        startActivity(intent);
                        finish();
                        drawerLayout.closeDrawers();
                        break;







                }

                return false;
            }
        });

        navigationcategory.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.movie_action:
                        String Action = "Action";
                        Fragment Action_movie = new Fragment();
                        Bundle  bundle = new Bundle();
                        bundle.putString("Genre", Action);

                        CategoryResult frag =new CategoryResult();
                        frag.setArguments(bundle);
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container, frag);

                        fragmentTransaction.add(R.id.container,Action_movie,"ActionMovie");
                        fragmentTransaction.addToBackStack("addActionMovie");
                        item.setChecked(true);
                        fragmentTransaction.commit();
                        drawerLayout.closeDrawers();




                        break;

                    case R.id.movie_scifi:
                        String scifi = "Sci-fi";
                        Fragment Scifi_movie = new Fragment();
                        Bundle  bundle2 = new Bundle();
                        bundle2.putString("Genre", scifi);

                        CategoryResult frag2 =new CategoryResult();
                        frag2.setArguments(bundle2);
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container, frag2);

                        fragmentTransaction.add(R.id.container,Scifi_movie,"scifiMovie");
                        fragmentTransaction.addToBackStack("addscifiMovie");
                        item.setChecked(true);
                        fragmentTransaction.commit();
                        drawerLayout.closeDrawers();


                        break;
                    case R.id.movie_comedy:

                        String comedy = "Comedy";
                        Fragment comedy_movie = new Fragment();
                        Bundle  bundle1 = new Bundle();
                        bundle1.putString("Genre", comedy);

                        CategoryResult frag1 =new CategoryResult();
                        frag1.setArguments(bundle1);
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container, frag1);

                        fragmentTransaction.add(R.id.container,comedy_movie,"ComedyMovie");
                        fragmentTransaction.addToBackStack("addComedyMovie");
                        item.setChecked(true);
                        fragmentTransaction.commit();
                        drawerLayout.closeDrawers();

                        break;
                    case R.id.movie_Thriller:

                        String Thriller = "Thriller";
                        Fragment Thriller_movie = new Fragment();
                        Bundle  bundle3 = new Bundle();
                        bundle3.putString("Genre", Thriller);

                        CategoryResult frag3 =new CategoryResult();
                        frag3.setArguments(bundle3);
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container, frag3);

                        fragmentTransaction.add(R.id.container,Thriller_movie,"ThrillerMovie");
                        fragmentTransaction.addToBackStack("addThrillerMovie");
                        item.setChecked(true);
                        fragmentTransaction.commit();
                        drawerLayout.closeDrawers();



                        break;
                    case R.id.movie_Fantasy:
                        String Fantasy = "Fantasy";
                        Fragment Fanstasy_movie = new Fragment();
                        Bundle  bundle4 = new Bundle();
                        bundle4.putString("Genre", Fantasy);

                        CategoryResult frag4 =new CategoryResult();
                        frag4.setArguments(bundle4);
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container, frag4);

                        fragmentTransaction.add(R.id.container,Fanstasy_movie,"FantasyMovie");
                        fragmentTransaction.addToBackStack("addFanstasyMovie");
                        item.setChecked(true);
                        fragmentTransaction.commit();
                        drawerLayout.closeDrawers();


                        break;
                    case R.id.movie_war:


                        String War = "War";
                        Fragment War_movie = new Fragment();
                        Bundle  bundle5 = new Bundle();
                        bundle5.putString("Genre", War);

                        CategoryResult frag5 =new CategoryResult();
                        frag5.setArguments(bundle5);
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container, frag5);

                        fragmentTransaction.add(R.id.container,War_movie,"WarMovie");
                        fragmentTransaction.addToBackStack("addWarMovie");
                        item.setChecked(true);
                        fragmentTransaction.commit();
                        drawerLayout.closeDrawers();

                        break;



                }

                return false;
            }
        });

    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionbarDrawertoggle.syncState();
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id  = item.getItemId();
        if(id == android.R.id.home) {

            if (drawerLayout.isDrawerOpen(navigationView) || drawerLayout.isDrawerOpen(navigationcategory)) {

                drawerLayout.closeDrawer(navigationView);
                drawerLayout.closeDrawer(navigationcategory);


            } else {

                drawerLayout.openDrawer(navigationView);
                drawerLayout.closeDrawer(navigationcategory);

            }
        }

        return super.onOptionsItemSelected(item);
    }



}
