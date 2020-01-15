package com.lengwe.iVolunteer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class Report extends Activity {

    static ReportRefreshAdapter reportRefreshAdapter;
    static RecyclerView recyclerView;

    private SqlConnect sqlConnect;
    private Bundle bundle;
    private String titleReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        sqlConnect = new SqlConnect(this);
        bundle = getIntent().getExtras();
        titleReport = bundle.getString("titleReport");

        reportRefreshAdapter = new ReportRefreshAdapter(this, findViewById(R.id.recyclerViewParentContainer), titleReport);
    }

    public void add_report_fab(View view) {

        Intent intent = new Intent(getApplicationContext(), ReportAdd.class);
        intent.putExtra("titleReport2", titleReport);
        startActivity(intent);

    }

}
