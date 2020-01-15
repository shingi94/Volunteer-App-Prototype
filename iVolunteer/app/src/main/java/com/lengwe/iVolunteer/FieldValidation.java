package com.lengwe.iVolunteer;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

public class FieldValidation {

    static Context c;

    // TODO: 3/4/18 trim fields

    public FieldValidation(Context c){
        FieldValidation.c = c;
    }

    public static final boolean isValidEmail(CharSequence email){
        if (email == null || TextUtils.isEmpty(email)){
            return false;
        } else if(Patterns.EMAIL_ADDRESS.matcher(email).matches() == true){
            return true;
        } else if (Patterns.EMAIL_ADDRESS.matcher(email).matches() == false){
            Toast.makeText(c, "Please enter a valid email", Toast.LENGTH_SHORT).show();
            return false;
        }
        return false;
    }

  public static final boolean isPasswordValid(CharSequence password){
      if (password == null || TextUtils.isEmpty(password)){
          return false;
      } else if(password.length() < 3){
          Toast.makeText(c, "Password must have at least 4 characters", Toast.LENGTH_SHORT).show();
          return false;
      } else {
          return true;
      }

  }
}
