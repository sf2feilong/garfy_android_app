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

public class Reset extends ActionBarActivity {

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
    private int code;
    private String resultId = null;
    private String resultFirstName = null;
    private String resultLastName= null;
    private String resultActive=null;
    private String resultName = null;
    final private ArrayList<String> listItems = new ArrayList();
    final private ArrayList<String> listedItems = new ArrayList();
    private ListView l_result;
    private ListView l_select;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> selectAdapter;
    private String[] autoloadArray = new String[]{"ALL","STUDENT","EMPLOYEE","TECH"};
    private String concat= null;
    private ArrayList<Integer> positionList = new ArrayList();
    private String method;
    private String selectedId= null;
    private String pin_code = null;
    private String[] id_token = null;
    private int resetCode;
    private boolean resultClicked = false;
    private boolean exit = false;
    EditText e_idnumber;
    EditText e_first_name;
    EditText e_last_name;
    EditText e_pin_code;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset);

        username = getIntent().getExtras().getString("username");
        level = getIntent().getExtras().getString("level");

        mDrawerList = (ListView)findViewById(R.id.navList);mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        final RadioButton r_find_user = (RadioButton)findViewById(R.id.findUserRadioButton);
        final RadioButton r_auto_load = (RadioButton)findViewById(R.id.autoLoadRadioButton);
        e_idnumber = (EditText)findViewById(R.id.idNumberEditText);
        e_first_name = (EditText)findViewById(R.id.firstNameEditText);
        e_last_name = (EditText)findViewById(R.id.lastNameEditText);
        final Spinner s_auto_load = (Spinner)findViewById(R.id.autoloadSpinner);
        final Button result = (Button)findViewById(R.id.resultButton);
        l_result = (ListView)findViewById(R.id.resultListView);
        l_select = (ListView)findViewById(R.id.selectListView);
        e_pin_code = (EditText)findViewById(R.id.pinEditText);
        final Button reset_password = (Button)findViewById(R.id.resetPasswordButton);
        final Button activate = (Button)findViewById(R.id.activateButton);
        final Button deactivate = (Button)findViewById(R.id.deactivateButton);
        final Button cancel = (Button)findViewById(R.id.cancelButton);

        final ArrayAdapter<String> autoloadAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, autoloadArray);
        s_auto_load.setAdapter(autoloadAdapter);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter != null /*&& resultClicked == true*/) {
                    for (int i = 0; i < adapter.getCount(); i++) {
                        adapter.remove(adapter.getItem(i));

                        adapter.notifyDataSetChanged();

                    }
                }
                idNumber = e_idnumber.getText().toString();
                firstName = e_first_name.getText().toString();
                lastName = e_last_name.getText().toString();
                if (r_find_user.isChecked() == true) {
                    if (!idNumber.isEmpty() && !firstName.isEmpty() && !lastName.isEmpty()) {
                        method = "full";

                    } else if (!idNumber.isEmpty() && !firstName.isEmpty()) {
                        method = "id first";
                        idNumber = e_idnumber.getText().toString();
                        firstName = e_first_name.getText().toString();
                    } else if (!idNumber.isEmpty() && !lastName.isEmpty()) {
                        method = "id last";
                        idNumber = e_idnumber.getText().toString();
                        lastName = e_last_name.getText().toString();
                    } else if (!idNumber.isEmpty()) {
                        method = "id";
                        idNumber = e_idnumber.getText().toString();
                    } else if (!firstName.isEmpty() && lastName.isEmpty()) {
                        method = "first last";
                        firstName = e_first_name.getText().toString();
                        lastName = e_last_name.getText().toString();
                    } else if (!firstName.isEmpty()) {
                        method = "first";
                        firstName = e_first_name.getText().toString();
                    } else if (!lastName.isEmpty()) {
                        method = "last";
                        lastName = e_last_name.getText().toString();
                    } else {

                    }
                    if (listItems.isEmpty()) {
                        searchUserFull();
                        l_result.setAdapter(adapter);
                        Log.e("Empty Result List", "was here");
                    } else {
                        Log.e("if Result List", "was here");
                        int insertInto = 1;
                        boolean check;
                        String output = idNumber;
                        searchUserFull();
                        Log.e("else after search", "was here");
                        for (int i = 0; i < listItems.size(); i++) {

                            Log.e("for start", "was here " + output);
                            String[] tokens = listItems.get(i).split(" ");
                            check = isResultEqual(i, tokens[0]);
                            Log.e("check", "was here " + check);
                            if (check == true) {
                                //for (int j = 0; i < adapter.getCount(); j++) {
                                    adapter.remove(adapter.getItem(i));
                                    //listItems.remove(i);
                                    adapter.notifyDataSetChanged();
                                //}
                                //insertInto = 0;
                                Log.e("check true", "was here " + check);
                            } else {
                                Log.e("adapter count", Integer.toString(adapter.getCount()));
                                //for(int j=0;i<adapter.getCount();j++){
                                    adapter.remove(adapter.getItem(i));
                                    //listItems.remove(i);
                                    adapter.notifyDataSetChanged();
                                //}
                                //searchUserFull();

                            }
                        }
                        l_result.setAdapter(adapter);
                        /*if (insertInto == 1) {
                            /*for(int i=0;i<adapter.getCount();i++){
                                adapter.remove(adapter.getItem(i));
                                //listItems.remove(i);
                                adapter.notifyDataSetChanged();
                            }
                            searchUserFull();
                            l_select.setAdapter(adapter);

                            Log.e("check false", "was here " + listItems.size());
                        }*/
                    }
                }
                if (r_auto_load.isChecked() == true && s_auto_load.getSelectedItemPosition() == 0) {
                    searchAutoLoadAll();
                }

            }
        });

        l_result.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                resultClicked = true;
                addToSelected(position);

            }

        });

        reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pin_code = e_pin_code.getText().toString();
                resetSelected(pin_code);
            }
        });

        activate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pin_code = e_pin_code.getText().toString();
                activateSelected(pin_code);
            }
        });

        deactivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pin_code = e_pin_code.getText().toString();
                deactivateSelected(pin_code);
            }
        });


    }

    private void clearScreen(){
        e_idnumber.setText("");
        e_first_name.setText("");
        e_last_name.setText("");
        e_pin_code.setText("");

        for(int i=0;i<listItems.size();i++){
            adapter.remove(adapter.getItem(0));
            listItems.removeAll(listItems);
        }
        for(int i=0;i<listedItems.size();i++){
            selectAdapter.remove(selectAdapter.getItem(0));
            listedItems.removeAll(listedItems);
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        adapter.notifyDataSetChanged();
        l_result.setAdapter(adapter);

        selectAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listedItems);
        selectAdapter.notifyDataSetChanged();
        l_select.setAdapter(selectAdapter);

    }

    private void resetSelected(String pin){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(exit == true){
                    clearScreen();
                }
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();

        if(!listedItems.isEmpty()){
            for(int i=0;i<listedItems.size();i++){
                id_token = listedItems.get(i).split(" ");
                selectedId = id_token[0];
                resetPassword(selectedId, pin);
            }
            if(resetCode == 1){
                alertDialog.setMessage("Successfully Reset Password");
                alertDialog.show();
                exit = true;

            }
            else{
                alertDialog.setMessage("Invalid PIN Code");
                alertDialog.show();
            }



        }else{
            alertDialog.setMessage("None Selected");
            alertDialog.show();
        }

    }

    private void resetPassword(String id, String pin){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("user", username));
        nameValuePairs.add(new BasicNameValuePair("id", id));
        nameValuePairs.add(new BasicNameValuePair("pin", pin));


        try {
            HttpClient httpclient = new DefaultHttpClient();
            //HttpPost httppost = new HttpPost("http://192.168.0.100/website_android/android_change_password.php");
            HttpPost httppost = new HttpPost("http://192.168.0.101/website_android/android_reset_password.php");
            //HttpPost httppost = new HttpPost("http://10.0.2.2/website_android/android_change_password.php");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            String result = EntityUtils.toString(entity, HTTP.UTF_8);

            Log.e("pass 1", "connection success ");
            try {
                JSONObject json_data = new JSONObject(result);
                code = json_data.getInt("code");

                if(code == 1){
                    Toast.makeText(getApplicationContext(), "Successfully Reset Password",
                            Toast.LENGTH_LONG).show();
                    resetCode = 1;
                }
                else{
                    Toast.makeText(getApplicationContext(), result,
                            Toast.LENGTH_LONG).show();
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

    private void activateSelected(String pin){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (exit == true) {
                    clearScreen();
                }
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();

        if(!listedItems.isEmpty()){
            for(int i=0;i<listedItems.size();i++){
                id_token = listedItems.get(i).split(" ");
                selectedId = id_token[0];
                activateUser(selectedId, pin);
            }
            if(resetCode == 1){
                alertDialog.setMessage("Successfully Activated");
                alertDialog.show();
                exit = true;
            }
            else{
                alertDialog.setMessage("Invalid PIN Code");
                alertDialog.show();
            }



        }else{
            alertDialog.setMessage("None Selected");
            alertDialog.show();
        }

    }

    private void activateUser(String id, String pin){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("user", username));
        nameValuePairs.add(new BasicNameValuePair("id", id));
        nameValuePairs.add(new BasicNameValuePair("pin", pin));


        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://192.168.0.101/website_android/android_activate_user.php");
            //HttpPost httppost = new HttpPost("http://10.0.2.2/website_android/android_change_password.php");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            String result = EntityUtils.toString(entity, HTTP.UTF_8);

            Log.e("pass 1", "connection success ");
            try {
                JSONObject json_data = new JSONObject(result);
                code = json_data.getInt("code");

                if(code == 1){
                    Toast.makeText(getApplicationContext(), "Successfully Activated",
                            Toast.LENGTH_LONG).show();
                    resetCode = 1;
                }
                else{
                    Toast.makeText(getApplicationContext(), result,
                            Toast.LENGTH_LONG).show();
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

    private void deactivateSelected(String pin){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(exit == true){
                    clearScreen();
                }

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();

        if(!listedItems.isEmpty()){
            for(int i=0;i<listedItems.size();i++){
                id_token = listedItems.get(i).split(" ");
                selectedId = id_token[0];
                deactivateUser(selectedId, pin);
            }
            if(resetCode == 1){
                alertDialog.setMessage("Successfully Deactivated");
                alertDialog.show();
                exit = true;
            }
            else{
                alertDialog.setMessage("Invalid PIN Code");
                alertDialog.show();
            }



        }else{
            alertDialog.setMessage("None Selected");
            alertDialog.show();
        }

    }

    private void deactivateUser(String id, String pin){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("user", username));
        nameValuePairs.add(new BasicNameValuePair("id", id));
        nameValuePairs.add(new BasicNameValuePair("pin", pin));


        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://192.168.0.101/website_android/android_deactivate_user.php");
            //HttpPost httppost = new HttpPost("http://10.0.2.2/website_android/android_change_password.php");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            String result = EntityUtils.toString(entity, HTTP.UTF_8);

            Log.e("pass 1", "connection success ");
            try {
                JSONObject json_data = new JSONObject(result);
                code = json_data.getInt("code");

                if(code == 1){
                    Toast.makeText(getApplicationContext(), "Successfully Deactivated",
                            Toast.LENGTH_LONG).show();
                    resetCode = 1;
                }
                else{
                    Toast.makeText(getApplicationContext(), result,
                            Toast.LENGTH_LONG).show();
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

    private void addToSelected(int position){
        boolean isEqual = false;
        int i=0;

        if(listedItems.size()==0){
            positionList.add(position);
            listedItems.add(listItems.get(position));
        }
        if(listedItems.size()>0){
            while(isEqual==false && i < listedItems.size()){
                isEqual = isEqual(listItems.get(position),listedItems.get(i));
                Log.e("isEqual() = ", listItems.get(position)+isEqual);
                i++;
            }
            if(isEqual==false){
                listedItems.add(listItems.get(position));
            }
            else{
                resultClicked = false;
                Toast.makeText(getApplicationContext(), "Record already selected",
                        Toast.LENGTH_LONG).show();
            }
        }


        selectAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listedItems);
        selectAdapter.notifyDataSetChanged();
        l_select.setAdapter(selectAdapter);
    }

    private boolean isEqual(String resultList,String selectList ){
        boolean result = false;

        if(resultList.equals(selectList)){
            result = true;
        }
        return result;
    }

    private boolean isResultEqual(int position, String data){
        boolean result = false;
        String[] result_token = listItems.get(position).split(" ");
        if (result_token[0].equals(data)){
            result = true;
        }

        Toast.makeText(getApplicationContext(), listItems.get(position).toString(),
                Toast.LENGTH_LONG).show();

        return result;
    }

    private void searchAutoLoadAll(){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("user", username));

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://192.168.0.101/website_android/android_select_user_auto_all.php");
            //HttpPost httppost = new HttpPost("http://10.0.2.2/website_android/android_select_user_full.php");
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
                JSONArray json_array = new JSONArray(result);
                int modulus;

                for (int i = 0; i < json_array.length(); i++) {
                    modulus = i % 4;
                    resultName = json_array.optString(i);

                    if (modulus == 0) {

                        resultId = resultName;
                        concat = resultId;
                    }
                    else if (modulus == 1) {
                        resultFirstName = resultName;
                        concat = concat + " " + resultFirstName;
                    }
                    else if (modulus == 2) {
                        resultLastName = resultName;
                        concat = concat + " " + resultLastName;
                    }
                    else if (modulus == 3) {
                        resultActive = resultName;
                        concat = concat + " " + resultActive;

                        listItems.add(concat);
                    }
                    Log.d(resultName, "Output");
                }
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
                l_result.setAdapter(adapter);


            } catch (Exception e) {
                Log.e("Fail 2", e.toString());
            }
        } catch (Exception e) {
            Log.e("Fail 1", e.toString());
            Toast.makeText(getApplicationContext(), "Invalid IP Address",
                    Toast.LENGTH_LONG).show();
        }

    }

    private void searchUserFull(){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("user", username));
        nameValuePairs.add(new BasicNameValuePair("id_number", idNumber));
        nameValuePairs.add(new BasicNameValuePair("first_name", firstName));
        nameValuePairs.add(new BasicNameValuePair("last_name", lastName));
        nameValuePairs.add(new BasicNameValuePair("method", method));

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://192.168.0.101/website_android/android_select_user_full.php");
            //HttpPost httppost = new HttpPost("http://10.0.2.2/website_android/android_select_user_full.php");
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
                JSONArray json_array = new JSONArray(result);
                int modulus;

                for (int i = 0; i < json_array.length(); i++) {
                    modulus = i % 4;
                    resultName = json_array.optString(i);

                    if (modulus == 0) {
                        resultId = resultName;
                        concat = resultId;
                    }
                    else if (modulus == 1) {
                        resultFirstName = resultName;
                        concat = concat + " " + resultFirstName;
                    }
                    else if (modulus == 2) {
                        resultLastName = resultName;
                        concat = concat + " " + resultLastName;
                    }
                    else if (modulus == 3) {
                        resultActive = resultName;
                        concat = concat + " " + resultActive;
                        listItems.add(concat);
                    }
                    Log.d(resultName, "Output");
                }

                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
                //l_result.setAdapter(adapter);


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
