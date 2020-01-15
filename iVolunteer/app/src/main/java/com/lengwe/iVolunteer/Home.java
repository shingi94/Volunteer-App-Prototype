package com.lengwe.iVolunteer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class Home extends AppCompatActivity implements SearchView.OnQueryTextListener {

    SqlConnect sqlConnect;
    byte[] activity_image;
    String activity_name;
    String activity_location;
    String activity_date;
    String activity_start_time;
    String user_email;
    String organiser_firstName;
    String organiser_sirName;
    String organiser_names;
    static RecyclerView myrv;
    static ActivityRefreshAdapter activityRefreshAdapter;

    public static String latest_activity;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    Toolbar toolbar;

    TextView name;
    TextView email;

//    static Bundle extras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView viewById = (NavigationView) findViewById(R.id.nav_view);
        View headerView = viewById.getHeaderView(0);

        name = (TextView) headerView.findViewById(R.id.user_name);
        email = (TextView) headerView.findViewById(R.id.email_address);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        sqlConnect = new SqlConnect(this);

        SharedPreferences sharedPreferences = getSharedPreferences("userData", Context.MODE_PRIVATE);
        String _name = sharedPreferences.getString("name", "");
        String _email = sharedPreferences.getString("email", "");

        email.setText(_email);
        name.setText(_name);


        activityRefreshAdapter = new ActivityRefreshAdapter(this, findViewById(R.id.drawer));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    static int i=1;
    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d("key press = ", ""+i);
        i++;
        newText = newText.toLowerCase();
        ArrayList<ActivtyModel> newList = new ArrayList<>();

        for (ActivtyModel activity : activityRefreshAdapter.getActivityList()) {
            // search based on what you want
            String title = activity.getActivity_name().toLowerCase();
            String reporterName = activity.getVolunteam_names().toLowerCase();
            if (title.contains(newText) || reporterName.contains(newText)) {
                newList.add(activity);
            }
        }
        activityRefreshAdapter.getMyAdapter().setFilter(newList);
        return true;
    }

    public void add_activity_fab(View view) {
        Intent intent = new Intent(getApplicationContext(), ActivityAdd.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        AlertDialog diaBox = AskOption();
        diaBox.show();
    }

    private AlertDialog AskOption()
    {
        AlertDialog alertDialog =new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Home.this.finish();
                        System.exit(0);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        return alertDialog;

    }
}
