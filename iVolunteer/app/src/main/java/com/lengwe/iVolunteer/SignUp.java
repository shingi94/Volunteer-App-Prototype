package com.lengwe.iVolunteer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends Activity {

    public static String global_email;

    SqlConnect sqlConnect;
    private EditText userName;
    private EditText emailAddress;
    private EditText password;
    private Boolean sign_up_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        sqlConnect = new SqlConnect(this);
        userName = (EditText)findViewById(R.id.name);
        emailAddress = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);

    }

    public void login_link(View view) {
        Intent intent = new Intent(getApplicationContext(), LogIn.class);
        startActivity(intent);
    }

    public void sign_up(View view) {
        String userName = this.userName.getText().toString();
        String emailAddress = this.emailAddress.getText().toString();
        String password = this.password.getText().toString();

        if (!userName.isEmpty() && !emailAddress.isEmpty() && !password.isEmpty()){

            new FieldValidation(this);
            final boolean validEmail = FieldValidation.isValidEmail(emailAddress);
            final boolean passwordValid = FieldValidation.isPasswordValid(password);

            if (validEmail == true && passwordValid == true){
                sign_up_status = sqlConnect.signUp(userName, emailAddress, password);
                if (sign_up_status == true) {
                    SignUp.global_email = emailAddress;
                   Intent intent = new Intent(getApplicationContext(), Home.class);
                    SharedPreferences sharedPreferences = getSharedPreferences("userData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name", userName);
                    editor.putString("email", emailAddress);
                    editor.putString("password", password);
                    editor.commit();
//                   intent.putExtra("activity", "signup");
//                   intent.putExtra("name_sign_up", userName);
//                   intent.putExtra("email_sign_up", emailAddress);
                   Toast.makeText(this, "Sign Up successfull", Toast.LENGTH_SHORT).show();
                   startActivity(intent);
                 } else {

                }
             }
        } else {
                Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }

    }

//    public void editProfile(View v){
//
//        String userName = this.userName.getText().toString();
//        String password = this.password.getText().toString();
//
//        if(!userName.isEmpty() && !password.isEmpty()){
//
//        }
//
//    }
}
