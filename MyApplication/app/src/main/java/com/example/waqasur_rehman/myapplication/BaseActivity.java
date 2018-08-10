package com.example.waqasur_rehman.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

/**
 * Created by WAQAS UR-REHMAN on 26/11/2015.
 */
public abstract class BaseActivity  extends AppCompatActivity {





    private final String  tag = "BaseActivity";
    private DrawerLayout drawerLayout;
    SharedPreferences savedpref;
    Boolean isUserLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);




    }






        public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        //getMenuInflater().inflate(R.menu.main_menu,menu);

        return false;





    }


}

