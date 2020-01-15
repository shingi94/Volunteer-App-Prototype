package com.lengwe.iVolunteer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ReportRecyclerViewAdapter extends RecyclerView.Adapter<ReportRecyclerViewAdapter.MyViewHolder>{

    private SqlConnect sqlConnect;
    private Context context;
    private List<ReportModel> data;
    private String activityTitle;

    public ReportRecyclerViewAdapter(Context context, List<ReportModel> data, String activityTitle){
        this.context = context;
        this.sqlConnect = new SqlConnect(context);
        this.data = data;
        this.activityTitle = activityTitle;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.cardview_items_report, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.reportHeader.setText(data.get(position).getReportHeader());
        holder.reportContent.setText(data.get(position).getReportContent());
        holder.reportDate.setText(data.get(position).getReportDate());

        holder.reportMoreIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, holder.reportMoreIcon);
                popupMenu.getMenuInflater().inflate(R.menu.report_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                       
                        switch (item.getItemId()){
                            case R.id.end_activity:
                                Toast.makeText(context, "ActivtyModel ended", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.delete_report:
                                Toast.makeText(context, "Report deleted", Toast.LENGTH_SHORT).show();
                                sqlConnect.delete_report(data.get(position).getReportHeader());
                                Report.reportRefreshAdapter = new ReportRefreshAdapter(ReportRefreshAdapter.context, ReportRefreshAdapter.view, activityTitle);
                                break;
                        }
                       
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView reportHeader;
        TextView reportContent;
        TextView reportDate;
        ImageView reportMoreIcon;

        public MyViewHolder(View itemView) {
            super(itemView);

            this.reportHeader = (TextView)itemView.findViewById(R.id.report_header_id);
            this.reportContent = (TextView)itemView.findViewById(R.id.report_details_id);
            this.reportDate = (TextView)itemView.findViewById(R.id.report_date_id);
            this.reportMoreIcon = (ImageView)itemView.findViewById(R.id.report_more_id);
        }
    }
}
