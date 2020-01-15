package com.lengwe.iVolunteer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class ActivityRecyclerViewAdapter extends RecyclerView.Adapter<ActivityRecyclerViewAdapter.MyViewHolder> {

    SqlConnect sqlConnect;

    private Context mContext;
    private List<ActivtyModel> mdata;

    public ActivityRecyclerViewAdapter(Context mContext, List<ActivtyModel> mdata) {
        this.mContext = mContext;
        this.sqlConnect = new SqlConnect(mContext);
        this.mdata = mdata;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.cardview_items_activity, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        //Set xml variables based on object index, as it increments as this Method is called base on size of arraylist
        holder.activity_image.setImageURI(Uri.parse(mdata.get(position).getActivity_image()));
        holder.activity_name.setText(mdata.get(position).getActivity_name());
        holder.activity_location.setText(mdata.get(position).getActivity_location());
        holder.activity_dateTime.setText(mdata.get(position).getActivity_date().concat(" " + mdata.get(position).getActivity_start_time()));
        holder.Volunteam_names.setText(mdata.get(position).getVolunteam_names());
        holder.activity_desc.setText(mdata.get(position).getActivity_description());


        // Get variables from model class base on index

//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                if (mdata.get(position).getTitle().equals("")){} Lauch different activities dependind on title
//                // pass data to book activity
//                Intent intent = new Intent(mContext, BookActivity.class);
//                intent.putExtra("Title", mdata.get(position).getTitle());
//                intent.putExtra("Category", mdata.get(position).getCategory());
//                intent.putExtra("Description", mdata.get(position).getDescription());
//                intent.putExtra("Thumbnail", mdata.get(position).getThumbnail());
//
//               // start book activity
//                mContext.startActivity(intent);
//
//
//            }
//        });

        holder.activity_more_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(mContext, holder.activity_more_img);
                // TODO: 5/19/18 INTERCITY BUS APP -> select menu_option base on user & company
                popupMenu.getMenuInflater().inflate(R.menu.options_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        int itemId = item.getItemId();

                        switch (itemId) {
                            case R.id.edit:
                                Toast.makeText(mContext, "edit", Toast.LENGTH_SHORT).show();
                                //open edit activity hear & sent title to uniquely identify activity to edit
                                Intent intent = new Intent(mContext, ActivityEdit.class);
                                intent.putExtra("title", mdata.get(position).getActivity_name());
                                mContext.startActivity(intent);
                                break;
                            case R.id.delete:
                                sqlConnect.delete_activity(mdata.get(position).getActivity_name());
                                Home.activityRefreshAdapter = new ActivityRefreshAdapter(ActivityRefreshAdapter.context, ActivityRefreshAdapter.view);
                                break;
                            case R.id.share:
                                String activityName = mdata.get(position).getActivity_name();
                                String desc = mdata.get(position).getActivity_description();
                                String loc = mdata.get(position).getActivity_location();

                                String shareBody = "Activity name: "+activityName+
                                        "\nActivity location: " +loc+
                                        "\nActivity description: "+desc;
                                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                                sharingIntent.setType("text/plain");
                                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "ACTIVITY SHARE");
                                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
//                                sharingIntent.putExtra(android.content.Intent.EXTRA_);
                                mContext.startActivity(Intent.createChooser(sharingIntent, "share using"));
//                                Toast.makeText(mContext, "share " + mdata.get(position).getActivity_name(), Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.report:
                                String activityName2 = mdata.get(position).getActivity_name();
                                Toast.makeText(mContext, "report " + activityName2, Toast.LENGTH_SHORT).show();
                                Intent intent1 = new Intent(mContext, Report.class);
                                intent1.putExtra("titleReport", activityName2);
                                mContext.startActivity(intent1);

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
        return mdata.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        View v;
        ImageView activity_image;
        TextView activity_name;
        TextView activity_location;
        TextView activity_dateTime;
        TextView Volunteam_names;
        CardView activity_cardView;
        ImageView activity_more_img;
        TextView activity_desc;

        public MyViewHolder(View itemView) {
            super(itemView);

            this.activity_image = (ImageView) itemView.findViewById(R.id.activity_img_id);
            this.activity_name = (TextView) itemView.findViewById(R.id.activity_name_id);
            this.activity_location = (TextView) itemView.findViewById(R.id.activity_location_id);
            this.activity_dateTime = (TextView) itemView.findViewById(R.id.activity_dateTime_id);
            this.Volunteam_names = (TextView) itemView.findViewById(R.id.activity_Volunteam_names_id);
            this.activity_cardView = (CardView) itemView.findViewById(R.id.activity_cardview_id);
            this.activity_more_img = (ImageView) itemView.findViewById(R.id.activity_more_id);
            this.activity_desc = (TextView) itemView.findViewById(R.id.activity_description_id);

            v = itemView;
        }
    }

    public void setFilter(ArrayList<ActivtyModel> newList) {
        this.mdata = new ArrayList<>();
        mdata.addAll(newList);
        notifyDataSetChanged();
    }
}
