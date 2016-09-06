package com.acknotech.kiran.navigationdrawer;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.acknotech.kiran.navigationdrawer.models.SignUpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    @Bind(R.id.first_name)
    EditText _firstnameText;
    @Bind(R.id.last_name)
    EditText _lastnameText;
    @Bind(R.id.input_email)
    EditText _emailText;
    @Bind(R.id.btn_signup)
    Button _signupButton;
    @Bind(R.id.link_login)
    TextView _loginLink;
    @Bind(R.id.phone_number)
    TextView _phone_number;
    Spinner all_roles;
    Spinner primary_games;
    List<Integer> allgame_ids = new ArrayList<>();
    List<String> allNames = new ArrayList<String>();
    int statuscode;
    List<Integer> secGameIds = new ArrayList<Integer>();
    String firstname, lastname, email, password, phonenumber, gender, date;
    Date dateofbirth;
    int primary_game_id;
    int allroles_id;

    ProgressDialog progressDialog;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;
    Toast toast;
    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            showDate(arg1, arg2 + 1, arg3);
        }
    };

    public static final String ENDPOINT = "http://192.168.0.107:8080";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);

        final Spinner spinner = (Spinner) findViewById(R.id.primary_game_spinner);

        final Spinner roles_spinner = (Spinner) findViewById(R.id.allroles_spinner);

        final RadioGroup rg = (RadioGroup) findViewById(R.id.radioGrp);


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                final String value = ((RadioButton) findViewById(rg.getCheckedRadioButtonId())).getText().toString();
                gender = value;
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("SignupActivity","Selected position id is :::: " + allgame_ids.get(position));
                primary_game_id = allgame_ids.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.v("SignupActivity","Nothing selected");
            }
        });

        dateView = (TextView) findViewById(R.id.date_of_birth);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);

        if (isOnline()) {
            requestData("http://128.199.203.43:8080/SportsApp/allgames");
            requestRoles();
        } else {
            Toast.makeText(this, "Network not available", Toast.LENGTH_LONG);
        }

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstname = _firstnameText.getText().toString();
                lastname = _lastnameText.getText().toString();
                email = _emailText.getText().toString();
                phonenumber = _phone_number.getText().toString();
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void requestRoles() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .build();

        AllRolesAPI allRolesParams = retrofit.create(AllRolesAPI.class);
        Call<AllRolesParams> allRolesParamsCall = allRolesParams.getAllRoles();
        allRolesParamsCall.enqueue(new Callback<AllRolesParams>() {
            @Override
            public void onResponse(Call<AllRolesParams> call, Response<AllRolesParams> response) {
                //response.body().getErrDesc();
                Log.v("SignupActivity", "Response :: " + response.body().getErrDesc());
            }

            @Override
            public void onFailure(Call<AllRolesParams> call, Throwable t) {
                Log.v("SignupActivity", "Failure :: ");
            }
        });
    }



    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "Select Date Of Birth", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    public Date stringToDate(String date) {
        Date date1 = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date1 = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }

    private void showDate(int year, int month, int day) {

        date = String.valueOf(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));
        dateView.setText(date);
        dateofbirth = stringToDate(date);
    }

    private void requestData(String uri) {

        RequestPackage p = new RequestPackage();
        p.setUri(uri);

        MyTask task = new MyTask();
        task.execute(p);
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        if (isOnline()) {
            //Setting Data for Signup

            //Setting secGameIds as null for
            secGameIds.add(0);
            secGameIds.add(0);

            _signupButton.setEnabled(false);

            Retrofit retrofit = new Retrofit
                    .Builder().baseUrl("http://192.168.0.107:8080")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            SignUpAPI requestInterface = retrofit.create(SignUpAPI.class);

            SignUpRequest registrationRequest = new SignUpRequest();

            registrationRequest.setFirstName(firstname);
            registrationRequest.setLastName(lastname);
            registrationRequest.setEmail(email);
            registrationRequest.setDateOfBirth(date);
            registrationRequest.setPhoneNum(phonenumber);
            registrationRequest.setGender(gender);
            registrationRequest.setSecGameIds(secGameIds);
            registrationRequest.setGameId(primary_game_id);
            registrationRequest.setRoleId(102);

            Call<SignUpParams> loginResponseCall = requestInterface.getErrDesc(registrationRequest);

            loginResponseCall.enqueue(new Callback<SignUpParams>() {
                @Override
                public void onResponse(Call<SignUpParams> call, Response<SignUpParams> response) {
                    statuscode = response.code();

                    SignUpParams loginResponse = response.body();

                    Headers h = response.headers();

                    String session_id = h.get("JSESSION_ID");

                    Log.d("SignupActivity", "session id is ... :: " + session_id);

                    Log.d("MainActivity", "onResponse: " + statuscode);

                    if (statuscode == 200) {
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Creating Account...");
                        progressDialog.show();
                        onSignupSuccess();
                    }
                }

                @Override
                public void onFailure(Call<SignUpParams> call, Throwable t) {
                    Log.d("MainActivity", "onFailure: " + t.getMessage());

                    onSignupFailed();
                }
            });

        } else {
            toast.makeText(getBaseContext(), "Network not available", Toast.LENGTH_SHORT);
            if (toast == null || toast.getView().getWindowVisibility() != View.VISIBLE) {
                toast.show();
            }
        }
    }

    /**
     * Retuns true if device is in online return false when the device is not in online
     *
     * @return
     */

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        Toast.makeText(getBaseContext(), "Registration success", Toast.LENGTH_LONG).show();
        SharedPref sp = null;
        sp.setDefaults("email", email, SignupActivity.this);
        sp.setDefaults("password", password, SignupActivity.this);
        sp.setDefaults("firstName", firstname, SignupActivity.this);
        //sp.setDefaults("photo", photo, SignupActivity.this);
        sp.setDefaults("lastName", lastname, SignupActivity.this);

        setResult(RESULT_OK, null);
        progressDialog.dismiss();
        finish();
    }

    public void onSignupFailed() {
        if (toast == null || toast.getView().getWindowVisibility() != View.VISIBLE) {
            toast = Toast.makeText(getApplicationContext(),"Registration Failed", Toast.LENGTH_SHORT);
            toast.show();
        }
        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        //Validation for inputs

        if (firstname.isEmpty() || firstname.length() < 3) {
            _firstnameText.setError("at least 3 characters");
            valid = false;
        } else {
            _firstnameText.setError(null);
        }

        if (lastname.isEmpty() || lastname.length() < 3) {
            _lastnameText.setError("at least 3 characters");
            valid = false;
        } else {
            _lastnameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }
        if (phonenumber.isEmpty() || phonenumber.length() < 10 || phonenumber.length() > 10) {
            _phone_number.setError("Must be 10 alphanumeric characters");
            valid = false;
        } else {
            _phone_number.setError(null);
        }
        return valid;
    }

    private class MyTask extends AsyncTask<RequestPackage, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(RequestPackage... requestPackages) {
            String allGames;
            allGames = (new HttpManager()).getAllGames(requestPackages[0]);
            return allGames;
        }

        @Override
        protected void onPostExecute(String s) {

            try {
                JSONArray cast = new JSONArray(s);
                for (int i = 0; i < cast.length(); i++) {
                    JSONObject actor = cast.getJSONObject(i);
                    String name = actor.getString("gameName");
                    int id = actor.getInt("gameId");
                    allNames.add(name);
                    allgame_ids.add(id);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            primary_games = (Spinner) findViewById(R.id.primary_game_spinner);
            ArrayAdapter<String> games_adapter = new ArrayAdapter<String>(SignupActivity.this,
                    android.R.layout.simple_spinner_item, allNames);
            primary_games.setAdapter(games_adapter);

        }
    }
}