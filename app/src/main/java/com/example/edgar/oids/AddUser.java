package com.example.edgar.oids;

//import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ExpandableListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddUser extends ActionBarActivity {

    private DrawerLayout drawer;
    private ExpandableListView drawerList;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    private String username;
    private String level;
    ArrayList<String> array = new ArrayList();
    private String idNumber = null;
    private String firstName = null;
    private String lastName = null;
    private String contactNumber = null;
    private String eFirstName = null;
    private String eLastName = null;
    private String userLevel = null;
    private String userType = null;
    private String extra = null;

    private ArrayList<String> postTo = new ArrayList();
    private String postToFull = null;
    private ArrayList<String> viewableTo = new ArrayList();
    private String viewableToFull = null;
    private String confirmBy = null;
    private String remarks;
    private int code;

    private String[] studentArray = new String[]{"COURSE","BSIT","BSIE","BSTE","BSME","BSECE"};
    private String[] employeeArray = new String[]{"POSITION","TEACHER","STAFF","LIBRARY","SAO","GUIDANCE", "PASTORAL ANIMATOR",
    "DEAN", "RECTOR", "CENSOR", "TECH"};
    private String[] chairArray = new String[]{"DEPT","BSIT","BSIE","BSTE","BSME","BSECE"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_user);

        username = getIntent().getExtras().getString("username");
        level = getIntent().getExtras().getString("level");

        mDrawerList = (ListView)findViewById(R.id.navList);mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        final EditText e_idnumber = (EditText)findViewById(R.id.idNumberEditText);
        final EditText e_first_name = (EditText)findViewById(R.id.firstNameEditText);
        final EditText e_last_name = (EditText)findViewById(R.id.lastNameEditText);
        final EditText e_contact = (EditText)findViewById(R.id.contactNumberEditText);
        final EditText e_e_first_name = (EditText)findViewById(R.id.eFirstNameEditText);
        final EditText e_e_last_name = (EditText)findViewById(R.id.eLastNameEditText);
        final RadioButton r_low = (RadioButton)findViewById(R.id.lowRadioButton);
        final RadioButton r_mid = (RadioButton)findViewById(R.id.midRadioButton);
        final RadioButton r_tech = (RadioButton)findViewById(R.id.techRadioButton);
        final RadioButton r_high = (RadioButton)findViewById(R.id.highRadioButton);
        final RadioButton r_extra1 = (RadioButton)findViewById(R.id.extraRadioButton1);
        final RadioButton r_extra2 = (RadioButton)findViewById(R.id.extraRadioButton2);
        final Spinner s_extra3 = (Spinner)findViewById(R.id.extraSpinner);

        final ArrayAdapter<String> studentAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, studentArray);
        s_extra3.setAdapter(studentAdapter);

        final ArrayAdapter<String> employeeAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, employeeArray);

        final ArrayAdapter<String> chairAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, chairArray);

        Button add = (Button)findViewById(R.id.addButton);

        r_low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r_extra1.setText("Student");
                r_extra2.setText("Employee");
                r_extra2.setVisibility(View.VISIBLE);
                s_extra3.setAdapter(studentAdapter);
            }
        });

        r_mid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r_extra1.setText("Chairperson");
                r_extra2.setText("Employee");
                r_extra2.setVisibility(View.VISIBLE);
                s_extra3.setAdapter(chairAdapter);
            }
        });

        r_high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r_extra1.setText("Employee");
                r_extra2.setVisibility(View.INVISIBLE);
                s_extra3.setAdapter(employeeAdapter);
            }
        });

        r_tech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r_extra1.setText("Employee");
                r_extra2.setVisibility(View.INVISIBLE);
                s_extra3.setAdapter(employeeAdapter);
            }
        });

        r_extra1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(r_low.isChecked() == true){
                    r_extra1.setText("Student");
                    r_extra2.setText("Employee");
                    s_extra3.setAdapter(studentAdapter);
                }
                else if(r_mid.isChecked() == true){
                    r_extra1.setText("Chairperson");
                    r_extra2.setText("Employee");
                    s_extra3.setAdapter(chairAdapter);
                }
            }
        });

        r_extra2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(r_low.isChecked() == true){
                    s_extra3.setAdapter(employeeAdapter);
                }
                else if(r_mid.isChecked() == true){
                    s_extra3.setAdapter(employeeAdapter);
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idNumber = e_idnumber.getText().toString();
                firstName = e_first_name.getText().toString();
                lastName = e_last_name.getText().toString();
                contactNumber = e_contact.getText().toString();
                eFirstName = e_e_first_name.getText().toString();
                eLastName = e_e_last_name.getText().toString();

                if(r_low.isChecked() == true){
                    userLevel = "low";
                    if(r_extra1.isChecked() == true){
                        userType = "student";
                        extra = studentArray[s_extra3.getSelectedItemPosition()];
                    }
                    else if(r_extra2.isChecked() == true){
                        userType = "employee";
                        extra = employeeArray[s_extra3.getSelectedItemPosition()];
                    }
                }
                else if(r_mid.isChecked() == true){
                    userLevel = "mid";
                    if(r_extra1.isChecked() == true){
                        userType = "chairperson";
                        extra = "chairperson " + chairArray[s_extra3.getSelectedItemPosition()];
                    }
                    else if(r_extra2.isChecked() == true){
                        userType = "employee";
                        extra = employeeArray[s_extra3.getSelectedItemPosition()];
                    }
                }
                else if(r_tech.isChecked() == true){
                    userLevel = "tech";
                    userType = "employee";
                    extra = employeeArray[s_extra3.getSelectedItemPosition()];
                }
                else if(r_high.isChecked() == true){
                    userLevel = "high";
                    userType = "employee";
                    extra = employeeArray[s_extra3.getSelectedItemPosition()];
                }
                insertUser();
            }
        });

    }

    private void insertUser(){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("user", username));
        nameValuePairs.add(new BasicNameValuePair("id_number", idNumber));
        nameValuePairs.add(new BasicNameValuePair("first_name", firstName));
        nameValuePairs.add(new BasicNameValuePair("last_name", lastName));
        nameValuePairs.add(new BasicNameValuePair("contact_number", contactNumber));
        nameValuePairs.add(new BasicNameValuePair("e_first_name", eFirstName));
        nameValuePairs.add(new BasicNameValuePair("e_last_name", eLastName));
        nameValuePairs.add(new BasicNameValuePair("user_level", userLevel));
        nameValuePairs.add(new BasicNameValuePair("user_type", userType));
        nameValuePairs.add(new BasicNameValuePair("extra", extra));

        try {
            HttpClient httpclient = new DefaultHttpClient();
            //HttpPost httppost = new HttpPost("http://192.168.0.100/website_android/android_insert_user.php");
            HttpPost httppost = new HttpPost("http://192.168.0.101/website_android/android_insert_user.php");
            //HttpPost httppost = new HttpPost("http://10.0.2.2/website_android/android_insert_user.php");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            String result = EntityUtils.toString(entity, HTTP.UTF_8);


            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();

            alertDialog.setMessage(result);

            alertDialog.show();



            Toast.makeText(getBaseContext(), result,
                    Toast.LENGTH_SHORT).show();

            Log.e("pass 1", "connection success ");
            try {
                JSONObject json_data = new JSONObject(result);
                code = json_data.getInt("code");

                switch(code){
                    case 1:Toast.makeText(getBaseContext(), "Successfully added User",
                            Toast.LENGTH_SHORT).show(); break;
                    case 2:Toast.makeText(getBaseContext(), "Missing Id Number",
                            Toast.LENGTH_SHORT).show(); break;
                    case 3:Toast.makeText(getBaseContext(), "Missing First Name",
                            Toast.LENGTH_SHORT).show(); break;
                    case 4:Toast.makeText(getBaseContext(), "Missing Last Name",
                            Toast.LENGTH_SHORT).show(); break;
                    case 5:Toast.makeText(getBaseContext(), "Missing Contact Number",
                            Toast.LENGTH_SHORT).show(); break;
                    case 6:Toast.makeText(getBaseContext(), "Missing Emergency First Name",
                            Toast.LENGTH_SHORT).show(); break;
                    case 7:Toast.makeText(getBaseContext(), "Missing Emergency Last Name",
                            Toast.LENGTH_SHORT).show(); break;
                    case 8:Toast.makeText(getBaseContext(), "Missing Id Number, First Name",
                            Toast.LENGTH_SHORT).show(); break;
                    case 9:Toast.makeText(getBaseContext(), "Missing Id Number, First Name, Last Name",
                            Toast.LENGTH_SHORT).show(); break;
                    case 10:Toast.makeText(getBaseContext(), "Missing Id Number, First Name, Last Name, Contact Number",
                            Toast.LENGTH_SHORT).show(); break;
                    case 11:Toast.makeText(getBaseContext(), "Missing Id Number, First Name, Last Name, Contact Number, Emergency First Name",
                            Toast.LENGTH_SHORT).show(); break;
                    case 12:Toast.makeText(getBaseContext(), "Missing Id Number, First Name, Last Name, Contact Number, Emergency First Name, Emergency Last Name",
                            Toast.LENGTH_SHORT).show(); break;
                    case 13:Toast.makeText(getBaseContext(), "Missing First Name, Last Name",
                            Toast.LENGTH_SHORT).show(); break;
                    case 14:Toast.makeText(getBaseContext(), "Missing First Name, Last Name, Contact Number",
                            Toast.LENGTH_SHORT).show(); break;
                    case 15:Toast.makeText(getBaseContext(), "Missing First Name, Last Name, Contact Number, Emergency First Name",
                            Toast.LENGTH_SHORT).show(); break;
                    case 16:Toast.makeText(getBaseContext(), "Missing First Name, Last Name, Contact Number, Emergency First Name, Emergency Last Name",
                            Toast.LENGTH_SHORT).show(); break;
                    case 17:Toast.makeText(getBaseContext(), "Missing Last Name, Contact Number",
                            Toast.LENGTH_SHORT).show(); break;
                    case 18:Toast.makeText(getBaseContext(), "Missing Last Name, Contact Number, Emergency First Name",
                            Toast.LENGTH_SHORT).show(); break;
                    case 19:Toast.makeText(getBaseContext(), "Missing Last Name, Contact Number, Emergency First Name, Emergency Last Name",
                            Toast.LENGTH_SHORT).show(); break;
                    case 20:Toast.makeText(getBaseContext(), "Missing Contact Number, Emergency First Name",
                            Toast.LENGTH_SHORT).show(); break;
                    case 21:Toast.makeText(getBaseContext(), "Missing Contact Number, Emergency First Name, Emergency Last Name",
                            Toast.LENGTH_SHORT).show(); break;
                    case 22:Toast.makeText(getBaseContext(), "Missing Emergency First Name, Emergency Last Name",
                            Toast.LENGTH_SHORT).show(); break;
                    case 111:Toast.makeText(getBaseContext(), "Successfully added Student",
                            Toast.LENGTH_SHORT).show(); break;
                    case 112:Toast.makeText(getBaseContext(), "Successfully added Employee",
                            Toast.LENGTH_SHORT).show(); break;
                    case 113:Toast.makeText(getBaseContext(), "Successfully added Chairperson",
                            Toast.LENGTH_SHORT).show(); break;
                    case 1111:Toast.makeText(getBaseContext(), "Successfully added Student & Log",
                            Toast.LENGTH_SHORT).show(); break;
                    case 1112:Toast.makeText(getBaseContext(), "Successfully added Employee & Log",
                            Toast.LENGTH_SHORT).show(); break;
                    case 1113:Toast.makeText(getBaseContext(), "Successfully added Chairperson & Log",
                            Toast.LENGTH_SHORT).show(); break;
                    default:Toast.makeText(getBaseContext(), result,
                            Toast.LENGTH_SHORT).show();break;
                }


            } catch (Exception e) {
                Log.e("Fail 2", e.toString());
            }
        } catch (Exception e) {
            Log.e("Fail 1", e.toString());
            Toast.makeText(getApplicationContext(), "Invalid IP Address",
                    Toast.LENGTH_LONG).show();
        }

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

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addDrawerItems() {

        if(level.equals("low")){
            goToLowItems();
        }
        else if(level.equals("mid")){
            goToMidItems();
        }
        else if(level.equals("high")){
            goToHighItems();
        }
        else if(level.equals("tech")){
            goToTechItems();
        }

        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(level.equals("low")){
                    goToLowOptions(position);
                }
                else if(level.equals("mid")){
                    goToMidOptions(position);
                }
                else if(level.equals("high")){
                    goToHighOptions(position);
                }
                else if(level.equals("tech")){
                    goToTechOptions(position);
                }

            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.mipmap.ic_launcher, R.string.drawer_open, R.string.drawer_close) {

            //** Called when a drawer has settled in a completely open state.
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("User Level: " + level + " Menu");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            //** Called when a drawer has settled in a completely closed state.
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void goToLowItems(){
        array.add("Check My A.R.F.s");
        array.add("      Submitted");
        array.add("      Drafts");
        array.add("      For Re-Edit");
        array.add("      Rejected");
        array.add("Create A.R.F.s");
        array.add("Change My Password");
        array.add("Sign Out");
    }

    private void goToMidItems(){
        array.add("Check A.R.F.s");
        array.add("      Received");
        array.add("      Submitted");
        array.add("      Drafts");
        array.add("      For Re-Edit");
        array.add("      Rejected");
        array.add("Create A.R.F.s");
        array.add("Change My Password");
        array.add("View Logs");
        array.add("Sign Out");
    }

    private void goToHighItems(){
        array.add("Check A.R.F.s");
        array.add("      Received");
        array.add("      Submitted");
        array.add("      Drafts");
        array.add("      For Re-Edit");
        array.add("      Rejected");
        array.add("Create A.R.F.s");
        array.add("System Notifications");
        array.add("      Standard");
        array.add("      Emergency");
        array.add("Manage Users");
        array.add("      Add New Users");
        array.add("      Edit Registered User");
        array.add("      Reset / Deactivate");
        array.add("Change My Password");
        array.add("Change My PIN Code");
        array.add("View Logs");
        array.add("Sign Out");
    }

    private void goToTechItems(){
        array.add("System Notifications");
        array.add("      Standard");
        array.add("      Emergency");
        array.add("Manage Users");
        array.add("      Add New Users");
        array.add("      Edit Registered User");
        array.add("      Reset / Deactivate");
        array.add("Change My Password");
        array.add("Change My PIN Code");
        array.add("View Logs");
        array.add("Sign Out");
    }

    private void goToLowOptions(int position){
        View v = null;
        switch(position){
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5: goToCreateARF(v);
                break;
            case 6:
                break;
            case 7: gotoSignOut(v);
                break;
        }
    }

    private void goToMidOptions(int position){
        View v = null;
        switch(position){
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6: goToCreateARF(v);
                break;
            case 7:
                break;
            case 8:
                break;
            case 9: gotoSignOut(v);
                break;
        }
    }

    private void goToHighOptions(int position){
        switch(position){
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
                break;
            case 11:
                break;
            case 12:
                break;
            case 13:
                break;
            case 14:
                break;
            case 15:
                break;
            case 16:
                break;
            case 17: View v = null; gotoSignOut(v);
                break;
        }
    }

    private void goToTechOptions(int position){
        switch(position){
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10: View v = null; gotoSignOut(v);
                break;
        }
    }

    private void goToCreateARF(View v){
        Intent intent = new Intent();
        intent.putExtra("level", this.level);
        startActivity(intent);
    }

    private void gotoSignOut(View v){
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getBaseContext().getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }


}
