package com.lengwe.iVolunteer;

public class ActivtyModel {

    String activity_image;
    String activity_name;
    String activity_location;
    String activity_date;
    String activity_start_time;
    String user_email;
    String Volunteam_names;
    String activity_description;

    public ActivtyModel(String activity_image, String activity_name, String activity_location, String activity_date, String activity_start_time, String user_email, String Volunteam_names, String activity_description) {
        this.activity_image = activity_image;
        this.activity_name = activity_name;
        this.activity_location = activity_location;
        this.activity_date = activity_date;
        this.activity_start_time = activity_start_time;
        this.user_email = user_email;
        this.Volunteam_names = Volunteam_names;
        this.activity_description = activity_description;
    }

    public String getActivity_image() {
        return activity_image;
    }

    public String getActivity_name() {
        return activity_name;
    }

    public String getActivity_location() {
        return activity_location;
    }

    public String getActivity_date() {
        return activity_date;
    }

    public String getActivity_start_time() {
        return activity_start_time;
    }

    public String getUser_email() {
        return user_email;
    }

    public String getVolunteam_names() {
        return Volunteam_names;
    }

    public String getActivity_description() {
        return activity_description;
    }
}
