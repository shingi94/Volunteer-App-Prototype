package com.lengwe.iVolunteer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportAdd extends Activity {

    SqlConnect sqlConnect;
    private Bundle bundle;
    private String titleReport;

    private final String REPORT_HEADER = "Report";
    private EditText reportHeading;
    private EditText reportContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_report);

        this.reportHeading = (EditText)findViewById(R.id.add_report_heading_id);
        this.reportContent = (EditText)findViewById(R.id.add_report_content_id);
        sqlConnect = new SqlConnect(this);

        bundle = getIntent().getExtras();
        titleReport = bundle.getString("titleReport2");
    }

    public void add_report(View view) {
        String reportHeading = this.reportHeading.getText().toString();
        String reportContent = this.reportContent.getText().toString();

        if (!reportContent.isEmpty()){
            if (reportHeading.isEmpty()){
                reportHeading = REPORT_HEADER;
            }
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String nowDate = simpleDateFormat.format(date);
            sqlConnect.add_report(reportHeading, reportContent, nowDate, this.titleReport);
            Report.reportRefreshAdapter = new ReportRefreshAdapter(ReportRefreshAdapter.context, ReportRefreshAdapter.view, titleReport);
        }
    }
}
