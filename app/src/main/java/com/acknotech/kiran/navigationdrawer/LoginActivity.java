package com.acknotech.kiran.navigationdrawer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @Bind(R.id.input_email)
    EditText _emailText;
    @Bind(R.id.input_password)
    EditText _passwordText;
    @Bind(R.id.btn_login)
    Button _loginButton;
    @Bind(R.id.link_signup)
    TextView _signupLink;

    //Login variables

    static JSONObject main_usage;
    String email, password, firstName, photo, lastName;
    boolean valid_user;
    ProgressDialog progressDialog;
    SharedPref sp;

    Toast toast;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

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

        progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 3000);
        email = _emailText.getText().toString();
        password = _passwordText.getText().toString();



        // TODO: Implement your own authentication logic here.

        if (isOnline()) {
            requestData("http://128.199.203.43:8080/SportsApp/login");
        } else {
            toast.makeText(this, "Network not available", Toast.LENGTH_SHORT);
            if (toast == null || toast.getView().getWindowVisibility() != View.VISIBLE) {
                toast.show();
            }
        }
    }

    private void requestData(String uri) {

        RequestPackage p = new RequestPackage();
        p.setUri(uri);

        MyTask task = new MyTask();
        task.execute(p);
    }

    private class MyTask extends AsyncTask<RequestPackage, String, String> {

        @Override
        protected String doInBackground(RequestPackage... requestPackages) {

            String content;

            content = (new HttpManager()).getData(requestPackages[0], email, password);

            return content;
        }

        @Override
        protected void onPostExecute(String result) {

            JSONObject jsonObject = null;

            try {
                jsonObject = new JSONObject(result);
                main_usage = jsonObject;
                valid_user = jsonObject.getBoolean("sessionValid");
                firstName = jsonObject.getString("firstName");
                lastName = jsonObject.getString("lastName");
//                photo = jsonObject.getString("photo");

                if (!valid_user) {
                    onLoginFailed();

                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    progressDialog.dismiss();
                                }
                            }, 3000);

                    return;
                } else {
                    Intent intent = getIntent();
                    intent.putExtra("result",result);
                    setResult(RESULT_OK);
                    onLoginSuccess();
                    return;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * Retuns true if device is in online return false when the device is not in online
     * @return
     */
    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {

        Toast toast1 = Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT);

        //Setting email and password when successful login

        sp.setDefaults("email",email,LoginActivity.this);
        sp.setDefaults("password",password,LoginActivity.this);
        sp.setDefaults("firstName",firstName,LoginActivity.this);
        sp.setDefaults("photo", photo, LoginActivity.this);
        sp.setDefaults("lastName", lastName, LoginActivity.this);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {

                    }
                }, 3000);
        progressDialog.dismiss();

        toast1.show();

        _loginButton.setEnabled(true);

        //MainActivity.firstname.setText(firstName);

        finish();
    }

    public void onLoginFailed() {

        if (toast == null || toast.getView().getWindowVisibility() != View.VISIBLE) {
            toast = Toast.makeText(getApplicationContext(),"Login Failed", Toast.LENGTH_SHORT);
            toast.show();
        }
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
