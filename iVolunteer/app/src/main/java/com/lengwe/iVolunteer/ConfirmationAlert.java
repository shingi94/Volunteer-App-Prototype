package com.lengwe.iVolunteer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import java.util.ArrayList;

public class ConfirmationAlert {

    private SqlConnect sqlConnect;
    private Context context;
    public ConfirmationAlert(Context context){
        this.context = context;
        sqlConnect = new SqlConnect(this.context);
    }

    static boolean result = false;
    public boolean onAddActivity(Context c, String title, String message, final ArrayList<String> data){
        AlertDialog.Builder alert = new AlertDialog.Builder(c);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sqlConnect.add_activity(data.get(0), data.get(1), data.get(2), data.get(3), data.get(4), data.get(5), data.get(6), data.get(7), data.get(8));
                Home.activityRefreshAdapter = new ActivityRefreshAdapter(ActivityRefreshAdapter.context, ActivityRefreshAdapter.view);
                Intent intent = new Intent(context, Home.class);
                context.startActivity(intent);
                result= true;
                dialog.dismiss();
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                result= false;
                dialog.dismiss();
            }
        });
        alert.show();
        return false;
    }
}
