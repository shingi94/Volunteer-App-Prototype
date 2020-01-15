package com.lengwe.iVolunteer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class SqlConnect extends SQLiteOpenHelper {

    private Context context;
    SQLiteDatabase writableDatabase;

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "XDISCOVERY_DB";

    public static final String USER_TABLE = "user_";
    public static final String USER_NAME = "user_name";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_PASSWORD = "user_password";


    public static final String Volunteam_FIRSTNAME = "Volunteam_firstName";
    public static final String Volunteam_SIRNAME = "Volunteam_sirName";

    public static final String ACTIVITY_TABLE = "activity";
    public static final String ACTIVITY_IMAGE = "activity_image";
    public static final String ACTIVITY_NAME = "activity_name";
    public static final String ACTIVITY_LOCATION = "activity_location";
    public static final String ACTIVITY_DATE = "activity_date";
    public static final String ACTIVITY_START_TIME = "activity_start_time";
    public static final String ACTIVITY_DESCRIPTION = "activity_desription";

    public static final String REPORT_TABLE = "report";
    public static final String REPORT_ACTIVITY_STATUS = "report_activity_status";
    public static final String REPORT_HEADER = "report_header";
    public static final String REPORT_CONTENT = "report_content";
    public static final String REPORT_TIME = "report_time";

    public static final String CREATE_USER_TABLE = "CREATE TABLE " + USER_TABLE + "(\n" +
            "" + USER_NAME + " VARCHAR(32) NOT NULL, \n" +
            "" + USER_EMAIL + " VARCHAR(32) PRIMARY KEY NOT NULL, \n" +
            "" + USER_PASSWORD + " VARCHAR(32) CHECK (length(" + USER_PASSWORD + ") > 3))";

    public static final String CREATE_ACTIVITY_TABLE = "CREATE TABLE " + ACTIVITY_TABLE + "(\n" +
            "" + ACTIVITY_IMAGE + " VARCHAR(4096), \n" +
            "" + ACTIVITY_NAME + " VARCHAR(32) PRIMARY KEY NOT NULL, \n" +
            "" + ACTIVITY_LOCATION + " VARCHAR(64), \n" +
            "" + ACTIVITY_DATE + " DATE NOT NULL, \n" +
            "" + ACTIVITY_START_TIME + " TIME,\n" +
            "" + USER_EMAIL + " VARCHAR(32),\n" +
            "" + Volunteam_FIRSTNAME + " VARCHAR(32) NOT NULL,\n" +
            "" + Volunteam_SIRNAME + " VARCHAR(32) NOT NULL,\n" +
            "" + ACTIVITY_DESCRIPTION + " VARCHAR(4096),\n" +
            "FOREIGN KEY(" + USER_EMAIL + ") REFERENCES user_(" + USER_EMAIL + "))";

    public static final String CREATE_REPORT_TABLE = "CREATE TABLE " + REPORT_TABLE + "(\n" +
            "" + REPORT_ACTIVITY_STATUS + " VARCHAR(3) CHECK(" + REPORT_ACTIVITY_STATUS + " = \"on\" or (" + REPORT_ACTIVITY_STATUS + " = \"off\")),\n" +
            "" + REPORT_HEADER + " VARCHAR(1024) NOT NULL,\n" +
            "" + REPORT_CONTENT + " VARCHAR(10000) NOT NULL,\n" +
            "" + REPORT_TIME + " TIME,\n" +
            "" + ACTIVITY_NAME + " VARCHAR(32) NOT NULL,\n" +
            "FOREIGN KEY(" + ACTIVITY_NAME + ") REFERENCES activity(" + ACTIVITY_NAME + "),\n" +
            "PRIMARY KEY(" + ACTIVITY_NAME + ", " + REPORT_CONTENT + "))";


    public SqlConnect(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        writableDatabase = this.getWritableDatabase();
        this.context = context;
    }


    public boolean signUp(String uname, String email, String pass) {

        writableDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_NAME, uname);
        contentValues.put(USER_EMAIL, email);
        contentValues.put(USER_PASSWORD, pass);
        long id = writableDatabase.insert(USER_TABLE, null, contentValues);

        if (id == -1) {
            return false;
        } else {
            return true;
        }
    }


    public Cursor logIn(String email, String password) {
        Cursor cursor = writableDatabase.rawQuery("SELECT " + USER_NAME + ", " + USER_EMAIL + " FROM " + USER_TABLE + " WHERE " + USER_EMAIL + " = ? AND " + USER_PASSWORD + " = ?", new String[]{email, password});
        return cursor;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_USER_TABLE);
            db.execSQL(CREATE_ACTIVITY_TABLE);
            db.execSQL(CREATE_REPORT_TABLE);
            Log.d("Lengwe", "DB Created");
        } catch (SQLException e) {
            Log.d("Lengwe", "DB not created");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
            onCreate(db);
            Log.d("Lengwe", "onUpgrade Succesfull");
        } catch (SQLException e) {
            Log.d("Lengwe", "Upgrade failed");
        }
    }

    public boolean add_activity(String activity_image, String activity_name, String activity_location,
                             String activity_date, String activity_time,
                             String user_email, String Volunteam_fName, String Volunteam_sName, String activity_description) {

        writableDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ACTIVITY_IMAGE, activity_image);
        contentValues.put(ACTIVITY_NAME, activity_name);
        contentValues.put(ACTIVITY_LOCATION, activity_location);
        contentValues.put(ACTIVITY_DATE, activity_date);
        contentValues.put(ACTIVITY_START_TIME, activity_time);
        contentValues.put(USER_EMAIL, user_email);
        contentValues.put(Volunteam_FIRSTNAME, Volunteam_fName);
        contentValues.put(Volunteam_SIRNAME, Volunteam_sName);
        contentValues.put(ACTIVITY_DESCRIPTION, activity_description);

        long id = writableDatabase.insert(ACTIVITY_TABLE, null, contentValues);

        if (id == -1) {
            Toast.makeText(context, "Activity already exists", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(context, "ActivtyModel successfully entered", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public boolean edit_activity(String activity_image, String activity_name, String activity_location,
                              String activity_date, String activity_time,
                              String user_email, String Volunteam_fName, String Volunteam_sName,
                              String old_title, String activity_description) {

        writableDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ACTIVITY_IMAGE, activity_image);
        contentValues.put(ACTIVITY_NAME, activity_name);
        contentValues.put(ACTIVITY_LOCATION, activity_location);
        contentValues.put(ACTIVITY_DATE, activity_date);
        contentValues.put(ACTIVITY_START_TIME, activity_time);
        contentValues.put(USER_EMAIL, user_email);
        contentValues.put(Volunteam_FIRSTNAME, Volunteam_fName);
        contentValues.put(Volunteam_SIRNAME, Volunteam_sName);
        contentValues.put(ACTIVITY_DESCRIPTION, activity_description);

        // return number of row updated
        long id = writableDatabase.update(ACTIVITY_TABLE, contentValues, ACTIVITY_NAME+" = ?", new String[]{old_title});

        if (id == 0) {
            Toast.makeText(context, "Failed to edit activity", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(context, id+" activity successfully edited", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public boolean delete_activity(String activity_name){
        writableDatabase = this.getWritableDatabase();
        int rows_affected = writableDatabase.delete(ACTIVITY_TABLE, ACTIVITY_NAME + " = ?", new String[]{activity_name});
        if (rows_affected < 0){
            Toast.makeText(context, rows_affected+" deleted", Toast.LENGTH_SHORT).show();
            return  true;
        } else{
            Toast.makeText(context, rows_affected+ " deleted", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    //you can restrict users to see only activities they entered by selecting based on email
    public Cursor view_activity(){
        Cursor cursor = writableDatabase.rawQuery("SELECT * FROM " + ACTIVITY_TABLE, null);
        return cursor;
    }

    public boolean add_report(String reportHeader, String reportContent, String reportTime, String activityName){
        writableDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(REPORT_ACTIVITY_STATUS, "on");
        contentValues.put(REPORT_HEADER, reportHeader);
        contentValues.put(REPORT_CONTENT, reportContent);
        contentValues.put(REPORT_TIME, reportTime);
        contentValues.put(ACTIVITY_NAME, activityName);

        long id = writableDatabase.insert(REPORT_TABLE, null, contentValues);

        if (id == -1) {
            Toast.makeText(context, "Failed to add report", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(context, "report successfully entered", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    //you can restrict users to see only reports they entered by selecting based on email
    public Cursor view_report(String activityName){
        Cursor cursor = writableDatabase.rawQuery("SELECT * FROM " +REPORT_TABLE+ " WHERE "+ACTIVITY_NAME+"= ?", new String[]{activityName});
        return cursor;
    }

    public boolean delete_report(String reportHeading){

        writableDatabase = this.getWritableDatabase();
        int rows_affected = writableDatabase.delete(REPORT_TABLE, REPORT_HEADER + " = ?", new String[]{reportHeading});
        if (rows_affected < 0){
            Toast.makeText(context, rows_affected+" deleted", Toast.LENGTH_SHORT).show();
            return  true;
        } else{
            Toast.makeText(context, rows_affected+ " deleted", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

}
