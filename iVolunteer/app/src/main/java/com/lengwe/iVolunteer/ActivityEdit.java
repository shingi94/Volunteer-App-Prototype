package com.lengwe.iVolunteer;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ActivityEdit extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public static final int CAMERA_PERMISSINS_REQUEST_CODE = 1111;
    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0, PLACE_PICKER_REQUEST_CODE = 3;
    SqlConnect sqlConnect;

    String activity_image;
    TextView title;
    String old_title;

    TextInputLayout activitinameParent;
    TextInputLayout VolunteamFNameParent;
    TextInputLayout VolunteamLNameParent;

    TextInputEditText activity_name;
    TextInputEditText activity_location;
    TextInputEditText Volunteam_firstName;
    TextInputEditText Volunteam_sirName;
    String activity_date;
    String activity_time;
    TextInputEditText activity_description;

    Bundle edit_bundle;
    ConfirmationAlert confirmationAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_activity);

        this.activitinameParent = (TextInputLayout) findViewById(R.id.edit_activity_name_parent_id);
        this.VolunteamFNameParent = (TextInputLayout) findViewById(R.id.edit_activity_Volunteam_firstName_parent);
        this.VolunteamLNameParent = (TextInputLayout) findViewById(R.id.edit_activity_Volunteam_sirName_parent_id);


        this.title = (TextView) findViewById(R.id.edit_activity_title);
        this.activity_name = (TextInputEditText) findViewById(R.id.edit_activity_name_id);
        this.activity_location = (TextInputEditText) findViewById(R.id.edit_activity_location_id);
        this.Volunteam_firstName = (TextInputEditText) findViewById(R.id.edit_activity_Volunteam_firstName);
        this.Volunteam_sirName = (TextInputEditText) findViewById(R.id.edit_activity_Volunteam_sirName_id);
        this.activity_description = (TextInputEditText) findViewById(R.id.edit_activity_description_id);

        sqlConnect = new SqlConnect(this);
        confirmationAlert = new ConfirmationAlert(this);

        edit_bundle = getIntent().getExtras();
        old_title = edit_bundle.getString("title");
        this.title.setText(old_title);

    }


    //DatePicker variable
    Button timePiker_btn;
    static final int DIALOG_ID = 0;

    //TimePicker variables
    int hour_x;
    int minute_x;

    //TimePicker callbacks
    public void edit_timePicker(View view) {
        showDialog(DIALOG_ID);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ID) {
            return new TimePickerDialog(ActivityEdit.this, timecPikerListener, hour_x, minute_x, false);
        } else {
            return null;
        }
    }

    //Anonymous inner class
    protected TimePickerDialog.OnTimeSetListener timecPikerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour_x = hourOfDay;
            minute_x = minute;
            timePiker_btn = (Button) findViewById(R.id.edit_pick_time_id);
            timePiker_btn.setText(hour_x + " : " + minute_x);
            activity_time = hourOfDay + "." + minute + ".00";
        }
    };


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
        setDate(cal);
    }

    private void setDate(final Calendar cal) {
        Button date_btn = (Button) findViewById(R.id.edit_pick_date_id);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy:MM:dd");
        String formated_date = simpleDateFormat.format(cal.getTime());
        date_btn.setText(formated_date);
        this.activity_date = formated_date;
    }

    public void edit_datePicker(View view) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getSupportFragmentManager(), "datePicker");
    }



    public static class DatePickerFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);
        }
    }

    public void edit_submit_btn_clicked(View view) {

        String activity_name = this.activity_name.getText().toString();
        String activity_location = this.activity_location.getText().toString();
        String user_email = "opps :(";
        String Volunteam_firstName = this.Volunteam_firstName.getText().toString();
        String Volunteam_sirName = this.Volunteam_sirName.getText().toString();
        String activity_desc = this.activity_description.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("userData", Context.MODE_PRIVATE);
        user_email = sharedPreferences.getString("email", "");

        if (activity_desc.isEmpty()){
            activity_desc = "One of the most beautifull places I have ever visted, I hope to visit it in the near future.";
        }

        if (activity_name.isEmpty()){
            this.activitinameParent.setErrorEnabled(true);
            this.activitinameParent.setError("Please enter activity name");
        }
        if (Volunteam_firstName.isEmpty()){
            this.VolunteamFNameParent.setErrorEnabled(true);
            this.VolunteamFNameParent.setError("Please enter Volunteam first name");
        }
        if (Volunteam_firstName.isEmpty()){
            this.VolunteamLNameParent.setErrorEnabled(true);
            this.VolunteamLNameParent.setError("Please enter Volunteam sir name");
        }
        if (activity_date == null){
            Toast.makeText(this, "Please enter date", Toast.LENGTH_SHORT).show();
        }

        if(this.activity_image == null){
            this.activity_image = "";
        }


        if (!activity_name.isEmpty() && !Volunteam_firstName.isEmpty() && !Volunteam_sirName.isEmpty()) {
                boolean editedActivity = sqlConnect.edit_activity(this.activity_image, activity_name, activity_location, this.activity_date, activity_time, user_email, Volunteam_firstName, Volunteam_sirName, this.old_title, activity_desc);
                if (editedActivity) {
                    Home.activityRefreshAdapter = new ActivityRefreshAdapter(ActivityRefreshAdapter.context, ActivityRefreshAdapter.view);
                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    startActivity(intent);

                }
        }
    }

    public void edit_image_btn(View view) {
        selectImage();
    }

    private void selectImage() {

        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityEdit.this);
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (items[i].equals("Camera")) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        invokeCamera();
                    } else {
                        String[] permissinRequest = {Manifest.permission.CAMERA};
                        requestPermissions(permissinRequest, CAMERA_PERMISSINS_REQUEST_CODE);
                    }
                } else if (items[i].equals("Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    intent.setType("image/*");
                    startActivityForResult(intent, SELECT_FILE);
                } else if (items[i].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void invokeCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSINS_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                invokeCamera();
            } else {
                Toast.makeText(this, "Cannot take photo without permisions", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == REQUEST_CAMERA) {
                Bundle bundle = data.getExtras();
                final Bitmap bitmap = (Bitmap) bundle.get("data");
                Uri imageUri = ImageConveter.getImageUriFromBitmap(ActivityEdit.this, bitmap);
                this.activity_image = imageUri.toString();

            } else if (requestCode == SELECT_FILE) {
                Uri selectImageUri = data.getData();
                this.activity_image = selectImageUri.toString();
            }else if (requestCode == PLACE_PICKER_REQUEST_CODE){
                Place place = PlacePicker.getPlace(data, this);
                String address = place.getName().toString()+"\n"+place.getAddress().toString();
                    activity_location.setText(address);
                Toast.makeText(this, "Location: "+address, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void getLocation(View view) {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        Intent intent;
        try {
            intent = builder.build(getApplicationContext());
            startActivityForResult(intent, PLACE_PICKER_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }
}
