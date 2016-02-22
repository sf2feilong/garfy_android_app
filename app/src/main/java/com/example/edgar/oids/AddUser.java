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
    private String idNumber;
    private String firstName;
    private String lastName;
    private String contactNumber;
    private String eFirstName;
    private String eLastName;
    private String userLevel;
    private String extra1;
    private String extra2;
    private String extra3;

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
                //idNumber =.getText().toString();
                int month = 0;//d_endDate.getMonth();
                int currentmonth = month + 1;
                String monthString = null;
                switch (currentmonth) {
                    case 1:
                        monthString = "01";
                        break;
                    case 2:
                        monthString = "02";
                        break;
                    case 3:
                        monthString = "03";
                        break;
                    case 4:
                        monthString = "04";
                        break;
                    case 5:
                        monthString = "05";
                        break;
                    case 6:
                        monthString = "06";
                        break;
                    case 7:
                        monthString = "07";
                        break;
                    case 8:
                        monthString = "08";
                        break;
                    case 9:
                        monthString = "09";
                        break;
                    case 10:
                        monthString = "10";
                        break;
                    case 11:
                        monthString = "11";
                        break;
                    case 12:
                        monthString = "12";
                        break;
                }
                int day = 0;//d_endDate.getDayOfMonth();
                String dayString = null;
                switch (day) {
                    case 1:
                        dayString = "01";
                        break;
                    case 2:
                        dayString = "02";
                        break;
                    case 3:
                        dayString = "03";
                        break;
                    case 4:
                        dayString = "04";
                        break;
                    case 5:
                        dayString = "05";
                        break;
                    case 6:
                        dayString = "06";
                        break;
                    case 7:
                        dayString = "07";
                        break;
                    case 8:
                        dayString = "08";
                        break;
                    case 9:
                        dayString = "09";
                        break;
                    case 10:
                        dayString = "10";
                        break;
                    case 11:
                        dayString = "11";
                        break;
                    case 12:
                        dayString = "12";
                        break;
                    case 13:
                        dayString = "13";
                        break;
                    case 14:
                        dayString = "14";
                        break;
                    case 15:
                        dayString = "15";
                        break;
                    case 16:
                        dayString = "16";
                        break;
                    case 17:
                        dayString = "17";
                        break;
                    case 18:
                        dayString = "18";
                        break;
                    case 19:
                        dayString = "19";
                        break;
                    case 20:
                        dayString = "20";
                        break;
                    case 21:
                        dayString = "21";
                        break;
                    case 22:
                        dayString = "22";
                        break;
                    case 23:
                        dayString = "23";
                        break;
                    case 24:
                        dayString = "24";
                        break;
                    case 25:
                        dayString = "25";
                        break;
                    case 26:
                        dayString = "26";
                        break;
                    case 27:
                        dayString = "27";
                        break;
                    case 28:
                        dayString = "28";
                        break;
                    case 29:
                        dayString = "29";
                        break;
                    case 30:
                        dayString = "30";
                        break;
                    case 31:
                        dayString = "31";
                        break;
                }
                /*endDate = d_endDate.getYear() + "-" + monthString + "-" + dayString;

                Toast.makeText(getBaseContext(), endDate,
                        Toast.LENGTH_SHORT).show();


                details = e_details.getText().toString();
                if (c_sao.isChecked() == true) {
                    postTo.add("student affairs");
                }
                if (c_faculty.isChecked() == true) {
                    postTo.add("faculty");
                }
                if (c_dean.isChecked() == true) {
                    postTo.add("dean");
                }
                if (c_guidance.isChecked() == true) {
                    postTo.add("guidance");
                }
                if (c_pastoral.isChecked() == true) {
                    postTo.add("pastoral animation");
                }
                if (c_library.isChecked() == true) {
                    postTo.add("library");
                }
                if (c_trc.isChecked() == true) {
                    postTo.add("training and research");
                }
                if (c_it.isChecked() == true) {
                    postTo.add("i.t. student org");
                }
                if (c_ie.isChecked() == true) {
                    postTo.add("i.e. student org");
                }
                if (c_te.isChecked() == true) {
                    postTo.add("t.e. student org");
                }
                if (c_me.isChecked() == true) {
                    postTo.add("m.e. student org");
                }
                if (c_ece.isChecked() == true) {
                    postTo.add("e.c.e. student org");
                }

                if (c_all.isChecked() == true) {
                    viewableTo.add("all");
                }
                if (c_employee.isChecked() == true) {
                    viewableTo.add("employees only");
                }
                if (c_viewFaculty.isChecked() == true) {
                    viewableTo.add("faculty");
                }
                if (c_personVp.isChecked() == true) {
                    viewableTo.add("personnel under vp of ed");
                }
                if (c_personRector.isChecked() == true) {
                    viewableTo.add("personnel under rector");
                }

                if (r_sao.isChecked() == true) {
                    confirmBy = "student affairs officer";
                } else if (r_pastoral.isChecked() == true) {
                    confirmBy = "pastoral animator";
                } else if (r_chairperson.isChecked() == true) {
                    confirmBy = "chairpersons";
                } else if (r_dean.isChecked() == true) {
                    confirmBy = "dean";
                } else if (r_rector.isChecked() == true) {
                    confirmBy = "rector";
                }*/

                //insertARF();
            }
        });

    }

   /* private void insertARF(){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("user", username));
        nameValuePairs.add(new BasicNameValuePair("title", title));
        nameValuePairs.add(new BasicNameValuePair("end", endDate));
        nameValuePairs.add(new BasicNameValuePair("details", details));
        if(postTo.size() == 0){
            postToFull = null;
        }else{
            postToFull = postTo.get(0);
            for(int i=1;i<postTo.size();i++){
                postToFull = postToFull + ", " + postTo.get(i);
            }
        }
        if(viewableTo.size() == 0){
            viewableToFull = null;
        }else{
            viewableToFull = viewableTo.get(0);
            for(int j=1;j<viewableTo.size();j++){
                viewableToFull = viewableToFull + ", " + viewableTo.get(j);
            }
        }

        nameValuePairs.add(new BasicNameValuePair("confirmBy", confirmBy));
        nameValuePairs.add(new BasicNameValuePair("postTo", postToFull));
        nameValuePairs.add(new BasicNameValuePair("viewableTo", viewableToFull));


        try {
            HttpClient httpclient = new DefaultHttpClient();
            //HttpPost httppost = new HttpPost("http://192.168.0.100/website_android/android_insert_arf.php");
            HttpPost httppost = new HttpPost("http://192.168.0.101/website_android/android_insert_arf.php");
            //HttpPost httppost = new HttpPost("http://10.0.2.2/website_android/select.php");
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
                    case 1:Toast.makeText(getBaseContext(), "Successfully created ARF",
                            Toast.LENGTH_SHORT).show(); break;
                    case 2:Toast.makeText(getBaseContext(), "Missing Title",
                            Toast.LENGTH_SHORT).show(); break;
                    case 4:Toast.makeText(getBaseContext(), "Missing Details",
                            Toast.LENGTH_SHORT).show(); break;
                    case 5:Toast.makeText(getBaseContext(), "Missing Post To",
                            Toast.LENGTH_SHORT).show(); break;
                    case 6:Toast.makeText(getBaseContext(), "Missing Viewable To",
                            Toast.LENGTH_SHORT).show(); break;
                    case 7:Toast.makeText(getBaseContext(), "Missing Confirm By",
                            Toast.LENGTH_SHORT).show(); break;
                    case 9:Toast.makeText(getBaseContext(), "Missing Title, Details",
                            Toast.LENGTH_SHORT).show(); break;
                    case 10:Toast.makeText(getBaseContext(), "Missing Title, Details, Post To",
                            Toast.LENGTH_SHORT).show(); break;
                    case 11:Toast.makeText(getBaseContext(), "Missing Title, Details, Post To, Viewable To",
                            Toast.LENGTH_SHORT).show(); break;
                    case 12:Toast.makeText(getBaseContext(), "Missing Title, Details, Post To, Viewable To, Confirm By",
                            Toast.LENGTH_SHORT).show(); break;
                    case 17:Toast.makeText(getBaseContext(), "Missing Details, Post To",
                            Toast.LENGTH_SHORT).show(); break;
                    case 18:Toast.makeText(getBaseContext(), "Missing Details, Post To, Viewable To",
                            Toast.LENGTH_SHORT).show(); break;
                    case 19:Toast.makeText(getBaseContext(), "Missing Details, Post To, Viewable To, Confirm By",
                            Toast.LENGTH_SHORT).show(); break;
                    case 20:Toast.makeText(getBaseContext(), "Missing Post To, Viewable To",
                            Toast.LENGTH_SHORT).show(); break;
                    case 21:Toast.makeText(getBaseContext(), "Missing Post To, Viewable To, Confirm By",
                            Toast.LENGTH_SHORT).show(); break;
                    case 22:Toast.makeText(getBaseContext(), "Missing Viewable To, Confirm by",
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
        //return level;
    }*/

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
