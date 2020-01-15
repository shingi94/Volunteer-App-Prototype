package com.lengwe.iVolunteer;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class ReportRefreshAdapter {

    private SqlConnect sqlConnect;
    static Context context;
    static View view;
    private String reportHeading;
    private String reportContent;
    private String reportDate;
    private ArrayList<ReportModel> reportList;
    private ReportRecyclerViewAdapter reportRecyclerViewAdapter;
    private String activityTitle;

    public ReportRefreshAdapter(Context context, View view, String activityTitle){
        ReportRefreshAdapter.context = context;
        ReportRefreshAdapter.view = view;
        this.activityTitle = activityTitle;
        refresh();
    }

    private void refresh() {
        sqlConnect = new SqlConnect(ReportRefreshAdapter.context);
        reportList = new ArrayList<>();
        reportList.clear();
        Cursor report_data = sqlConnect.view_report(activityTitle);

        while(report_data.moveToNext()){
            this.reportHeading = report_data.getString(1);
            this.reportContent = report_data.getString(2);
            this.reportDate = report_data.getString(3);

            reportList.add(new ReportModel(this.reportHeading, this.reportContent, this.reportDate));
        }

        Report.recyclerView = (RecyclerView) ReportRefreshAdapter.view.findViewById(R.id.reprot_recyclerview_id);
        reportRecyclerViewAdapter = new ReportRecyclerViewAdapter(ReportRefreshAdapter.context, reportList, activityTitle);
        Report.recyclerView.setLayoutManager(new GridLayoutManager(ReportRefreshAdapter.context, 1));
        Report.recyclerView.setAdapter(reportRecyclerViewAdapter);

    }

}
