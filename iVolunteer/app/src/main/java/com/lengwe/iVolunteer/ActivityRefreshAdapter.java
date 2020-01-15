package com.lengwe.iVolunteer;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class ActivityRefreshAdapter {

    SqlConnect sqlConnect;
    static Context context;
    static View view;
    String activity_image;
    String activity_name;
    String activity_location;
    String activity_date;
    String activity_start_time;
    String user_email;
    String Volunteam_firstName;
    String Volunteam_sirName;
    String Volunteam_names;
    String activity_description;
    ArrayList<ActivtyModel> activity_list;
    ActivityRecyclerViewAdapter myAdapter;

    public ActivityRefreshAdapter(Context context, View view){
        ActivityRefreshAdapter.context = context;
        ActivityRefreshAdapter.view = view;
        refresh();
    }

    public void refresh(){
        sqlConnect = new SqlConnect(ActivityRefreshAdapter.context);
        Cursor all_activitys = sqlConnect.view_activity();
        activity_list = new ArrayList<>();
        activity_list.clear();

        while (all_activitys.moveToNext()) {
            this.activity_image = all_activitys.getString(0);
            this.activity_name = all_activitys.getString(1);
            this.activity_location = all_activitys.getString(2);
            this.activity_date = all_activitys.getString(3);
            this.activity_start_time = all_activitys.getString(4);
            this.user_email = all_activitys.getString(5);
            this.Volunteam_firstName = all_activitys.getString(6);
            this.Volunteam_sirName = all_activitys.getString(7);
            this.Volunteam_names = "Volunteam: " + Volunteam_firstName.concat(" " + Volunteam_sirName);
            this.activity_description = all_activitys.getString(8);

            activity_list.add(new ActivtyModel(
                    this.activity_image, this.activity_name, this.activity_location,
                    this.activity_date, this.activity_start_time, this.user_email,
                    this.Volunteam_names, this.activity_description));
        }

        Home.myrv = (RecyclerView) ActivityRefreshAdapter.view.findViewById(R.id.recyclerview_id);
        myAdapter = new ActivityRecyclerViewAdapter(this.context, activity_list);
        Home.myrv.setLayoutManager(new GridLayoutManager(this.context, 1));
        Home.myrv.setAdapter(myAdapter);
    }

    public ArrayList<ActivtyModel> getActivityList(){
            return activity_list;
    }

    public ActivityRecyclerViewAdapter getMyAdapter(){
        return myAdapter;
    }


}
