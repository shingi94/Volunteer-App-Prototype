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
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ActivityAdd extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private Drawable drawable;
    public static final int CAMERA_PERMISSINS_REQUEST_CODE = 1111;
    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0, PLACE_PICKER_REQUEST_CODE = 3;

    SqlConnect sqlConnect;
    //
    String activity_image;
    TextInputLayout activitinameParent;
    TextInputLayout VolunteamFNameParent;
    TextInputLayout VolunteamLNameParent;

    TextInputEditText activity_name;
    TextInputEditText activity_location;
    TextInputEditText Volunteam_firstName;
    TextInputEditText Volunteam_sirName;
    String activity_date;
    TextInputEditText activity_description;



    //Time picker vaiables
    Button timePiker_btn;
    static final int DIALOG_ID = 4;
    String hour_x = "00";
    String minute_x = "00";

    private ConfirmationAlert confirmationAlert;

    public void timePicker(View view) {
        showDialog(DIALOG_ID);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ID) {
            return new TimePickerDialog(ActivityAdd.this, timecPikerListener, Integer.parseInt(hour_x), Integer.parseInt(minute_x), false);
        } else {
            return null;
        }
    }

    //Anonymous inner class
    protected TimePickerDialog.OnTimeSetListener timecPikerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour_x = String.valueOf(hourOfDay);
            minute_x = String.valueOf(minute);
            timePiker_btn = (Button) findViewById(R.id.pick_time_id);
            timePiker_btn.setText(hour_x + " : " + minute_x);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_activity);

        sqlConnect = new SqlConnect(this);

        activitinameParent = (TextInputLayout)findViewById(R.id.add_activity_name_parent_id);
        VolunteamFNameParent = (TextInputLayout)findViewById(R.id.add_activity_Volunteam_firstName_parent_id);
        VolunteamLNameParent = (TextInputLayout)findViewById(R.id.add_activity_Volunteam_sirName_parent_id);

        activity_name = (TextInputEditText) findViewById(R.id.add_activity_name_id);
        activity_location = (TextInputEditText) findViewById(R.id.add_activity_location_id);
        Volunteam_firstName = (TextInputEditText) findViewById(R.id.add_activity_Volunteam_firstName_id);
        Volunteam_sirName = (TextInputEditText) findViewById(R.id.add_activity_Volunteam_sirName_id);
        activity_description = (TextInputEditText)findViewById(R.id.add_activity_description_id);

        drawable = getResources().getDrawable(R.drawable.ic_error);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        confirmationAlert = new ConfirmationAlert(this);



        activity_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!activity_location.getText().toString().isEmpty()){

                }
            }
        });


    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
        setDate(cal);
    }

    private void setDate(final Calendar cal) {
        Button date_btn = (Button) findViewById(R.id.pick_date_id);
        if (cal == null){
            Toast.makeText(this, "Please enter date", Toast.LENGTH_SHORT).show();
        } else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date = simpleDateFormat.format(cal.getTime());
            date_btn.setText(date);
            this.activity_date = date;
        }
    }

    public void datePicker(View view) {
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

    public void add_submit_activity(View view) {

        String activity_name = this.activity_name.getText().toString();
        String activity_location = this.activity_location.getText().toString();
        String user_email = "opps :(";
        String Volunteam_firstName = this.Volunteam_firstName.getText().toString();
        String Volunteam_sirName = this.Volunteam_sirName.getText().toString();
        String activity_time = hour_x + ":" + minute_x + ":00";
        String activity_desc = this.activity_description.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("userData", Context.MODE_PRIVATE);
        user_email = sharedPreferences.getString("email", "");

        if (activity_desc.isEmpty()){
            activity_desc = "One of the most beautifull places I have ever visted, I hope to visit it in the near future.";
        }

        if(activity_name.isEmpty()){
            this.activitinameParent.setErrorEnabled(true);
            this.activitinameParent.setError("Please enter activity name");
        }
        if (Volunteam_firstName.isEmpty()){
            this.VolunteamFNameParent.setErrorEnabled(true);
            this.VolunteamFNameParent.setError("Please enter Volunteam first name");
        }
        if (Volunteam_sirName.isEmpty()){
            this.VolunteamLNameParent.setErrorEnabled(true);
            this.VolunteamLNameParent.setError("Please enter Volunteam sir name");
        }

        if (this.activity_date == null){
            Toast.makeText(this, "Please enter date", Toast.LENGTH_SHORT).show();
        }

        if(this.activity_image == null){
            this.activity_image = "";
        }

        if (!activity_name.isEmpty() && !Volunteam_firstName.isEmpty() && !Volunteam_sirName.isEmpty() && this.activity_date != null) {
            ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(this.activity_image, activity_name, activity_location, this.activity_date, activity_time, user_email, Volunteam_firstName, Volunteam_sirName, activity_desc));
            String msg = "Location: "+activity_location+"\nDate: "+activity_date+"\nTime: "+activity_time+"\nfName: "+Volunteam_firstName+"\nlName: "+Volunteam_sirName;
           confirmationAlert.onAddActivity(this, activity_name, msg, arrayList);
               }
    }

    public void add_image_btn(View view) {
        selectImage();
    }


    private void selectImage() {

        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAdd.this);
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
                Uri imageUri = ImageConveter.getImageUriFromBitmap(ActivityAdd.this, bitmap);
                this.activity_image = imageUri.toString();
            } else if (requestCode == SELECT_FILE) {
                Uri selectImageUri = data.getData();
                this.activity_image = selectImageUri.toString();
            } else if (requestCode == PLACE_PICKER_REQUEST_CODE){
                Place place = PlacePicker.getPlace(data, this);
                String address = place.getName().toString()+"\n"+place.getAddress().toString();

                activity_location.setText(address);
                Toast.makeText(this, "Location: "+address, Toast.LENGTH_SHORT).show();
            }
        }


    }


    public void getPlace(View view) {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            Intent intent = builder.build(getApplicationContext());
            startActivityForResult(intent, PLACE_PICKER_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }
}
