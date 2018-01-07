package com.example.archit.meracutretail;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import meracutretail.R;

public class LoginScreen extends AppCompatActivity{

    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private static String url_new_user = "http://www.meracut.com/app/login1.php";
    EditText username, pwd;
    Button login;
    TextView register;
    String usrname, pswd, number;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    private static final String TAG_SUCCESS = "success";
    private ProgressDialog mProgressDialog;


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>=21) {
            getWindow().setStatusBarColor(getApplication().getResources().getColor(R.color.colorPrimaryDark));
        }
        setContentView(R.layout.activity_loginscreen);

        sharedPref = getApplicationContext().getSharedPreferences("meracutretail", MODE_PRIVATE);
        editor = sharedPref.edit();

        if(sharedPref.getBoolean("signin",false)==true){
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);

        }


        String [] PermissionsLocation =
                {
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                };

        int RequestLocationId = 0;
        int permissionCheck = ContextCompat.checkSelfPermission(LoginScreen.this,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        Log.d("permisionn:", String.valueOf(permissionCheck));
        if (permissionCheck==-1) {
            requestPermissions(PermissionsLocation, RequestLocationId);
        }






        username = (EditText)findViewById(R.id.username);
        pwd = (EditText)findViewById(R.id.pwd);
        login = (Button)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(username.length()!=0 && pwd.length()!=0) {
                    usrname = username.getText().toString();
                    pswd = pwd.getText().toString();
                    new login().execute();
                }
                else
                    Toast.makeText(getApplication(), "Please Enter Your Details First", Toast.LENGTH_LONG).show();
            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }




    class login extends AsyncTask<String,String,String> {

        int flag = 0;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginScreen.this);
            pDialog.setMessage("Logging In..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {


            List<NameValuePair> param = new ArrayList<NameValuePair>();
            param.add(new BasicNameValuePair("uname", usrname));
            param.add(new BasicNameValuePair("pname", pswd));

            JSONObject json = jsonParser.makeHttpRequest(url_new_user,
                    "POST", param);
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created a user
                    //Intent i = new Intent(getApplicationContext(), startScreen.class);
                    //startActivity(i);
                    flag = 1;
                    // closing this screen
                    //finish();
                } else {
                    // failed to create user

                    //Log.d("failed to login", json.toString());
                    //Intent in = new Intent(getApplicationContext(),MainActivity.class);
                    //startActivity(in);
                    flag = 0;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
            if (flag == 1){
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                editor.putBoolean("signin",true);
                editor.putString("username", usrname);
                editor.putString("passxyz",pswd);
                editor.commit();
                startActivity(i);

            }
            else {
                Toast.makeText(getApplication(), "Invalid Password or Id", Toast.LENGTH_SHORT).show();
                username.setText("");
                pwd.setText("");
            }
        }

    }



}
