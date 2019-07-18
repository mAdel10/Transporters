package com.memaro.transporter.Activities.Helpers;

import android.content.Context;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

public class CompanyInputValidator {

    public static boolean signUpValidation(Context context, EditText companyName, EditText companyEmail, EditText comapanyPassword, EditText companyConfirmPassword, EditText companyMarketNumber, EditText companyAddress) {

        String name = companyName.getText().toString().trim();
        String email = companyEmail.getText().toString().trim();
        String password = comapanyPassword.getText().toString().trim();
        String confirmPassword = companyConfirmPassword.getText().toString().trim();
        String marketNumber = companyMarketNumber.getText().toString().trim();
        String address = companyAddress.getText().toString().trim();


        if (name.isEmpty() || email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches() || password.isEmpty() || confirmPassword.isEmpty() || password.length() < 6 || !password.equals(confirmPassword) || marketNumber.isEmpty() || address.isEmpty()) {

            if (name.isEmpty()) {
                companyName.setError("User Name Required", null);
                companyName.requestFocus();
            }


            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                companyEmail.setError("Email Not Valid", null);
                companyEmail.requestFocus();
            }

            if (email.isEmpty()) {
                companyEmail.setError("Email Required", null);
                companyEmail.requestFocus();
            }

            if (password.length() < 6) {
                comapanyPassword.setError("Password should be larger than 6 characters", null);
                comapanyPassword.requestFocus();
            }

            if (password.isEmpty()) {
                comapanyPassword.setError("Password Required", null);
                comapanyPassword.requestFocus();
            }

            if (!(password.equals(confirmPassword))) {
                comapanyPassword.setError("Password does not match", null);
                comapanyPassword.requestFocus();
            }

            if (confirmPassword.isEmpty()){
                companyConfirmPassword.setError("Confirm Password Required", null);
                companyConfirmPassword.requestFocus();
            }

            if (marketNumber.isEmpty()){
                companyMarketNumber.setError("Market Number Required", null);
                companyMarketNumber.requestFocus();
            }

            if (address.isEmpty()){
                companyAddress.setError("Address Required", null);
                companyAddress.requestFocus();
            }


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
}