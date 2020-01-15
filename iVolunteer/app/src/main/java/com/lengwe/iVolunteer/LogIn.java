package com.lengwe.iVolunteer;

// create, insert, update, delete, onupgrade

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LogIn extends AppCompatActivity {


    public static String global_email = null;

    private EditText email;
    private EditText password;
//    ImageView logo;
    SqlConnect sqlConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

//        logo = (ImageView)findViewById(R.id.logo);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);

        sqlConnect = new SqlConnect(this);

        SharedPreferences sharedPreferences = getSharedPreferences("userData", Context.MODE_PRIVATE);
        String _name = sharedPreferences.getString("email", "");
        String _pass = sharedPreferences.getString("password", "");
        this.email.setText(_name);
        this.password.setText(_pass);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
//           LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//           lp.setMargins(0,500, 0, 0);
//           logo.setLayoutParams(lp);

        }
        super.onConfigurationChanged(newConfig);

    }

    public void login(View v){

        String email = this.email.getText().toString();
        String pass = this.password.getText().toString();

        String name_ = null;
        String email_ = "";

        if (!email.isEmpty() && !pass.isEmpty()) {

            boolean validEmail = FieldValidation.isValidEmail(email);
            boolean passwordValid = FieldValidation.isPasswordValid(pass);

            if(validEmail == true && passwordValid == true) {
                Cursor userData = sqlConnect.logIn(email, pass);
                while (userData.moveToNext()) {
                    name_ = userData.getString(0);
                    email_ = userData.getString(1);
                }
                if (email_.equals(email)) {

                    SharedPreferences sharedPreferences = getSharedPreferences("userData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name", name_);
                    editor.putString("email", email_);
                    editor.commit();


                    global_email = email_;
                    Intent intent = new Intent(getApplicationContext(), Home.class);
//                    intent.putExtra("activity", "login");
//                    intent.putExtra("name", name_);
//                    intent.putExtra("email", email_);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Username or Password is incorrect", Toast.LENGTH_LONG).show();
                }
            }
        } else {
            Toast.makeText(this, "Please enter both fields", Toast.LENGTH_LONG).show();
        }
    }

    public void signup_link(View view) {
        Intent intent = new Intent(getApplicationContext(), SignUp.class);
        startActivity(intent);
    }
}
