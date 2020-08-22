package com.ecommerce.garmentstore;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";
    EditText _nameText;
    EditText _emailText;
    EditText _passwordText;
    Button _signupButton;
    TextView _loginLink;
    SqliteHelper sqliteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        _nameText = (EditText) findViewById(R.id.input_name);
        _emailText = (EditText) findViewById(R.id.input_email);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _signupButton = (Button) findViewById(R.id.btn_signup);
        _loginLink = (TextView) findViewById(R.id.link_login);

        sqliteHelper = new SqliteHelper(SignupActivity.this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validate()) {
                    _signupButton.setEnabled(false);
                    final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                            R.style.AppTheme_Dark_Dialog);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Creating Account...");
                    progressDialog.show();

                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    // On complete call either onSignupSuccess or onSignupFailed
                                    // depending on success
                                    register();
                                    // onSignupFailed();
                                    progressDialog.dismiss();
                                }
                            }, 3000);
                }


            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void register() {
        String UserName = _nameText.getText().toString();
        String Email = _emailText.getText().toString();
        String Password = _passwordText.getText().toString();

        //Check in the database is there any user associated with  this email
        if (!sqliteHelper.isEmailExists(Email)) {

            //Email does not exist now add new user to database
            sqliteHelper.addUser(new User(null, UserName, Email, Password));
            Snackbar.make(_signupButton, "User created successfully! Please Login ", Snackbar.LENGTH_LONG).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, Snackbar.LENGTH_LONG);
        } else {

            //Email exists with email input provided so show error user already exist
            Snackbar.make(_signupButton, "User already exists with same email ", Snackbar.LENGTH_LONG).show();
        }
        _signupButton.setEnabled(true);


    }


    private boolean validate() {
        boolean valid = false;

        //Get values from EditText fields
        String UserName = _nameText.getText().toString();
        String Email = _emailText.getText().toString();
        String Password = _passwordText.getText().toString();

        //Handling validation for UserName field
        if (UserName.isEmpty()) {
            valid = false;
            _nameText.setError("Please enter valid username!");
        } else {
            if (UserName.length() > 5) {
                valid = true;
                _nameText.setError(null);
            } else {
                valid = false;
                _nameText.setError("Username is to short!");
            }
        }

        //Handling validation for Email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid = false;
            _emailText.setError("Please enter valid email!");
        } else {
            valid = true;
            _emailText.setError(null);
        }

        //Handling validation for Password field
        if (Password.isEmpty()) {
            valid = false;
            _passwordText.setError("Please enter valid password!");
        } else {
            if (Password.length() > 5) {
                valid = true;
                _passwordText.setError(null);
            } else {
                valid = false;
                _passwordText.setError("Password is to short!");
            }
        }


        return valid;
    }
}