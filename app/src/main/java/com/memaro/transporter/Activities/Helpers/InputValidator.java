package com.memaro.transporter.Activities.Helpers;

import android.content.Context;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;


public class InputValidator {

    public static boolean signUpValidation(Context context, EditText userName, EditText emailET,EditText phoneET, EditText passwordET, EditText confirmPasswordET) {

        String name = userName.getText().toString().trim();
        String email = emailET.getText().toString().trim();
        String phone = phoneET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();
        String confirmPassword = confirmPasswordET.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches() || phone.isEmpty() || password.isEmpty() || phone.length() < 11 || confirmPassword.isEmpty() || password.length() < 6 || !password.equals(confirmPassword)) {

            if (name.isEmpty())
                userName.setError("User Name Required", null);


            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                emailET.setError("Email Not Valid", null);

            if (email.isEmpty())
                emailET.setError("Email Required", null);

            if (phone.isEmpty())
                emailET.setError("Email Required", null);

            if (phone.length() < 11)
                passwordET.setError("Password should be larger than 11 Numbers", null);


            if (password.length() < 6)
                passwordET.setError("Password should be larger than 6 characters", null);

            if (password.isEmpty())
                passwordET.setError("Password Required", null);


            if (!(password.equals(confirmPassword)))
                passwordET.setError("Password does not match", null);

            if (confirmPassword.isEmpty())
                confirmPasswordET.setError("Confirm Password Required", null);


            return false;
        }
        return true;
    }

    public static boolean signInValidation(Context context, EditText emailET, EditText passwordET) {

        String email = emailET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches() || password.isEmpty()) {

            if (email.isEmpty())
                emailET.setError("Email Required", null);

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                emailET.setError("Email not valid", null);

            if (password.isEmpty())
                passwordET.setError("Password required", null);

            return false;
        }
        return true;
    }

    public static boolean emailValidation(Context context, EditText emailET) {

        String email = emailET.getText().toString().trim();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                Toast.makeText(context, "البريد الألكتروني غير صالح", Toast.LENGTH_LONG).show();

            if (email.isEmpty())
                Toast.makeText(context, "يرجي كتابة البريد الألكتروني", Toast.LENGTH_LONG).show();

            return false;
        }
        return true;
    }

    public static boolean messageValidation(Context context, EditText messageET, EditText titleET) {
        String message = messageET.getText().toString();
        String title = titleET.getText().toString();

        if (title.isEmpty() || message.isEmpty()) {

            if (title.isEmpty())
                titleET.setError("يرجي أدخال العنوان");

            if (message.isEmpty())
                messageET.setError("يرجي أدخال المحتوي");

            return false;
        }
        return true;
    }
}
