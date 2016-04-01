package com.example.edgar.oids;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Login extends Activity {

    String username;
    String password;
    InputStream is=null;
    String result = null;
    String line = null;
    int code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        final EditText e_user=(EditText) findViewById(R.id.usernameEditText);
        final EditText e_pass=(EditText) findViewById(R.id.passwordEditText);

        Button signIn=(Button) findViewById(R.id.signInButton);
        Button cancel=(Button) findViewById(R.id.cancelButton);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = e_user.getText().toString();
                password = e_pass.getText().toString();


                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                select();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);


            }
        });




    }

    public void select() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("user", username));
        nameValuePairs.add(new BasicNameValuePair("pass", password));

        try{
            HttpClient httpClient = new DefaultHttpClient();
            //HttpPost httpPost = new HttpPost("http://192.168.0.100/website_android/android_login.php");
            HttpPost httpPost = new HttpPost("http://192.168.0.101/website_android/android_login.php");
            //HttpPost httpPost = new HttpPost("http://10.0.2.2/website_android/android_login.php");
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            Log.e("pass 1", "connection success ");
        }catch(Exception e){
            Log.e("Fail 1", e.toString());
            Toast.makeText(getApplicationContext(), "Invalid IP Address", Toast.LENGTH_LONG).show();
        }

        try
        {
            BufferedReader reader = new BufferedReader
                    (new InputStreamReader(is,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
            Log.e("pass 2", "connection success ");
        }
        catch(Exception e)
        {
            Log.e("Fail 2", e.toString());
        }

        try
        {
            JSONObject json_data = new JSONObject(result);
            code=(json_data.getInt("code"));

            if(code==1)
            {
                Toast.makeText(getBaseContext(), "Signed In Successfully",
                        Toast.LENGTH_SHORT).show();
                View v = null;
                goToAnnouncements(v);
            }
            else if(code==2)
            {
                Toast.makeText(getBaseContext(), "Username can't be blank",
                        Toast.LENGTH_SHORT).show();
            }
            else if(code==3)
            {
                Toast.makeText(getBaseContext(), "Password can't be blank",
                        Toast.LENGTH_SHORT).show();
            }
            else if(code==4)
            {
                Toast.makeText(getBaseContext(), "Invalid username or password",
                        Toast.LENGTH_SHORT).show();
            }
            else if(code==5)
            {
                Toast.makeText(getBaseContext(), "Username does not exist",
                        Toast.LENGTH_SHORT).show();
            }
            else if(code==6)
            {
                Toast.makeText(getBaseContext(), "Username and Password can't be blank",
                        Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getBaseContext(), "Sorry, Try Again " + code,
                        Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception e)
        {
            Log.e("Fail 3",code  + " " +  e.toString());
            Toast.makeText(getBaseContext(), "Sorry, Try Again " + result,
                    Toast.LENGTH_LONG).show();
        }
    }

    public void goToAnnouncements(View v){
        Intent intent = new Intent(this, Announcements.class);
        intent.putExtra("username", this.username);
        startActivity(intent);
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


