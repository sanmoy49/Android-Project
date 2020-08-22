package com.ecommerce.garmentstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private static final int RC_SIGN_IN = 007;

    EditText _emailText;
    EditText _passwordText;
    Button _loginButton;
    TextView _signupLink;
    SqliteHelper sqliteHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _emailText = (EditText)findViewById(R.id.input_email);
        _passwordText = (EditText)findViewById(R.id.input_password);
        _loginButton = (Button) findViewById(R.id.btn_login);
        _signupLink = (TextView) findViewById(R.id.link_signup);
        sqliteHelper = new SqliteHelper(LoginActivity.this);



        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });


    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        final String email = _emailText.getText().toString();
        final String password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess(email,password);
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }




    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess(String email,String password) {


        //Authenticate user
        User currentUser = sqliteHelper.Authenticate(new User(null, null, email, password));

        //Check Authentication is successful or not
        if (currentUser != null) {
            Snackbar.make(_loginButton, "Successfully Logged in!", Snackbar.LENGTH_LONG).show();

            //User Logged in Successfully Launch You home screen activity
                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
        } else {

            //User Logged in Failed
            Snackbar.make(_loginButton, "Failed to log in , please try again", Snackbar.LENGTH_LONG).show();

        }
        _loginButton.setEnabled(true);

    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }




    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}