package com.example.edgar.oids;

//import android.app.Activity;
import android.app.AlertDialog;
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

import java.util.ArrayList;
import java.util.StringTokenizer;

public class CreateARF extends ActionBarActivity {

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
    private ArrayList<String> array = new ArrayList();
    private String title;
    private String endDate;
    private String details;
    private ArrayList<String> postTo = new ArrayList();
    private String postToFull = null;
    private ArrayList<String> viewableTo = new ArrayList();
    private String viewableToFull = null;
    private String confirmBy = null;
    private String remarks;
    private int code;
    private String category = null;
    private String subType = null;
    private String arf_id = null;
    private String data = null;
    private String select_title = null;
    private String select_endDate = null;
    private String select_details = null;
    private String select_postTo = null;
    private String select_viewTo = null;
    private String select_confirmBy = null;
    private String select_remarks = null;

    private EditText e_title;
    private DatePicker d_endDate;
    private EditText e_details;
    private CheckBox c_sao;
    private CheckBox c_faculty;
    private CheckBox c_dean;
    private CheckBox c_guidance;
    private CheckBox c_pastoral;
    private CheckBox c_library;
    private CheckBox c_trc;
    private CheckBox c_it;
    private CheckBox c_ie;
    private CheckBox c_te;
    private CheckBox c_me;
    private CheckBox c_ece;
    private CheckBox c_all;
    private CheckBox c_employee;
    private CheckBox c_viewFaculty;
    private CheckBox c_personVp;
    private CheckBox c_personRector;
    private RadioButton r_sao;
    private RadioButton r_pastoral;
    private RadioButton r_chairperson;
    private RadioButton r_dean;
    private RadioButton r_rector;
    private EditText e_remarks;
    private StringTokenizer tokenizer;
    private StringTokenizer view_tokenizer;
    private ArrayList<String> select_post_to = new ArrayList();
    private ArrayList<String> select_view_to = new ArrayList();
    private String endDateDay = null;
    private String endDateYear = null;
    private String endDateMonth = null;
    private String[] post_token;
    private String[] view_token;
    private String type = null;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_arf);

        username = getIntent().getExtras().getString("username");
        level = getIntent().getExtras().getString("level");
        category = getIntent().getExtras().getString("category");
        type = getIntent().getExtras().getString("type");

        if(category.equals("arf details")){
            arf_id = getIntent().getExtras().getString("arf_id");
        }


        mDrawerList = (ListView)findViewById(R.id.navList);mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        e_title = (EditText)findViewById(R.id.titleEditText);
        d_endDate = (DatePicker)findViewById(R.id.endDatePicker);
        e_details = (EditText)findViewById(R.id.idNumberEditText);
        c_sao = (CheckBox)findViewById(R.id.saoCheckBox);
        c_faculty = (CheckBox)findViewById(R.id.facultyCheckBox);
        c_dean = (CheckBox)findViewById(R.id.deanCheckBox);
        c_guidance = (CheckBox)findViewById(R.id.guidanceCheckBox);
        c_pastoral = (CheckBox)findViewById(R.id.pastoralCheckBox);
        c_library = (CheckBox)findViewById(R.id.libraryCheckBox);
        c_trc = (CheckBox)findViewById(R.id.trcCheckBox);
        c_it = (CheckBox)findViewById(R.id.bsitCheckBox);
        c_ie = (CheckBox)findViewById(R.id.bsieCheckBox);
        c_te = (CheckBox)findViewById(R.id.bsteCheckBox);
        c_me = (CheckBox)findViewById(R.id.bsmeCheckBox);
        c_ece = (CheckBox)findViewById(R.id.bseceCheckBox);
        c_all = (CheckBox)findViewById(R.id.viewAllCheckBox);
        c_employee = (CheckBox)findViewById(R.id.viewEmployeesCheckBox);
        c_viewFaculty = (CheckBox)findViewById(R.id.viewFacultyCheckBox);
        c_personVp = (CheckBox)findViewById(R.id.personVpCheckBox);
        c_personRector = (CheckBox)findViewById(R.id.personRectorCheckBox);
        r_sao = (RadioButton)findViewById(R.id.saoRadioButton);
        r_pastoral = (RadioButton)findViewById(R.id.pastoralRadioButton);
        r_chairperson = (RadioButton)findViewById(R.id.chairpersonRadioButton);
        r_dean = (RadioButton)findViewById(R.id.deanRadioButton);
        r_rector = (RadioButton)findViewById(R.id.rectorRadioButton);
        e_remarks = (EditText)findViewById(R.id.remarksEditText);

        Button submit = (Button)findViewById(R.id.submitButton);
        Button draft = (Button)findViewById(R.id.saveDraftButton);
        Button edit = (Button)findViewById(R.id.editSaveButton);
        Button confirm = (Button)findViewById(R.id.confirmButton);
        Button for_re_edit = (Button)findViewById(R.id.reeditButton);
        Button reject = (Button)findViewById(R.id.rejectButton);
        Button delete = (Button)findViewById(R.id.deleteButton);
        Button cancel = (Button)findViewById(R.id.cancelButton);

        if(category.equals("create")){
            Log.e("2nd IF", category);

            if(level.equals("low")){
                confirm.setVisibility(View.GONE);
                for_re_edit.setVisibility(View.GONE);
                reject.setVisibility(View.GONE);
                delete.setVisibility(View.GONE);
            }
            else if(level.equals("mid")){
                submit.setVisibility(View.GONE);
                draft.setVisibility(View.GONE);
                edit.setVisibility(View.GONE);
            }
            else if(level.equals("high")){
                submit.setVisibility(View.GONE);
                draft.setVisibility(View.GONE);
                edit.setVisibility(View.GONE);
            }

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setInformation();
                    category = "submit";
                    insertARF();
                }
            });

            draft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setInformation();
                    category = "draft";
                    insertARF();
                }
            });

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setInformation();
                    category = "edit";
                    insertARF();
                }
            });

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setInformation();
                    category = "confirm";
                    insertARF();
                }
            });

            for_re_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setInformation();
                    category = "for re-edit";
                    insertARF();
                }
            });

            reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setInformation();
                    category = "rejected";
                    insertARF();
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setInformation();
                    category = "delete";
                    insertARF();
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.exit(0);
                }
            });
        }
        else if(category.equals("arf details")){
            if(level.equals("low") && type.equals("submitted")){
                submit.setVisibility(View.GONE);
                draft.setVisibility(View.GONE);
                edit.setVisibility(View.GONE);
                confirm.setVisibility(View.GONE);
                for_re_edit.setVisibility(View.GONE);
                reject.setVisibility(View.GONE);
                delete.setVisibility(View.GONE);
            }
            else if(level.equals("low") && type.equals("drafts")){
                //submit.setVisibility(View.GONE);
                //draft.setVisibility(View.GONE);
                //edit.setVisibility(View.GONE);
                confirm.setVisibility(View.GONE);
                for_re_edit.setVisibility(View.GONE);
                reject.setVisibility(View.GONE);
                delete.setVisibility(View.GONE);
            }
            else if(level.equals("low") && type.equals("for re-edit")){
                //submit.setVisibility(View.GONE);
                //draft.setVisibility(View.GONE);
                //edit.setVisibility(View.GONE);
                confirm.setVisibility(View.GONE);
                for_re_edit.setVisibility(View.GONE);
                reject.setVisibility(View.GONE);
                delete.setVisibility(View.GONE);
            }
            else if(level.equals("low") && type.equals("rejected")){
                submit.setVisibility(View.GONE);
                draft.setVisibility(View.GONE);
                edit.setVisibility(View.GONE);
                confirm.setVisibility(View.GONE);
                for_re_edit.setVisibility(View.GONE);
                reject.setVisibility(View.GONE);
                delete.setVisibility(View.GONE);
            }
            else if(level.equals("mid") && type.equals("received")){
                submit.setVisibility(View.GONE);
                draft.setVisibility(View.GONE);
                edit.setVisibility(View.GONE);
            }
            else if(level.equals("mid") && type.equals("drafts")){
                submit.setVisibility(View.GONE);
                //draft.setVisibility(View.GONE);
                edit.setVisibility(View.GONE);
            }
            else if(level.equals("mid") && type.equals("for re-edit")){
                //submit.setVisibility(View.GONE);
                //draft.setVisibility(View.GONE);
                //edit.setVisibility(View.GONE);
            }
            else if(level.equals("mid") && type.equals("rejected")){
                submit.setVisibility(View.GONE);
                draft.setVisibility(View.GONE);
                edit.setVisibility(View.GONE);
                confirm.setVisibility(View.GONE);
                for_re_edit.setVisibility(View.GONE);
                reject.setVisibility(View.GONE);
            }
            else if(level.equals("high") && type.equals("received")){
                submit.setVisibility(View.GONE);
                draft.setVisibility(View.GONE);
                edit.setVisibility(View.GONE);
            }
            else if(level.equals("high") && type.equals("drafts")){
                submit.setVisibility(View.GONE);
                //draft.setVisibility(View.GONE);
                edit.setVisibility(View.GONE);
            }
            else if(level.equals("high") && type.equals("for re-edit")){
                //submit.setVisibility(View.GONE);
                //draft.setVisibility(View.GONE);
                //edit.setVisibility(View.GONE);
            }
            else if(level.equals("high") && type.equals("rejected")){
                submit.setVisibility(View.GONE);
                draft.setVisibility(View.GONE);
                edit.setVisibility(View.GONE);
                confirm.setVisibility(View.GONE);
                for_re_edit.setVisibility(View.GONE);
                reject.setVisibility(View.GONE);
            }
            selectARFDetails();

        }



    }

    private void setInformation(){
        title = e_title.getText().toString();
        int month = d_endDate.getMonth();
        int currentmonth = month + 1;
        String monthString=null;
        switch(currentmonth){
            case 1:monthString = "01";break;
            case 2:monthString = "02";break;
            case 3:monthString = "03";break;
            case 4:monthString = "04";break;
            case 5:monthString = "05";break;
            case 6:monthString = "06";break;
            case 7:monthString = "07";break;
            case 8:monthString = "08";break;
            case 9:monthString = "09";break;
            case 10:monthString = "10";break;
            case 11:monthString = "11";break;
            case 12:monthString = "12";break;
        }
        int day = d_endDate.getDayOfMonth();
        String dayString = null;
        switch(day){
            case 1:dayString = "01";break;
            case 2:dayString = "02";break;
            case 3:dayString = "03";break;
            case 4:dayString = "04";break;
            case 5:dayString = "05";break;
            case 6:dayString = "06";break;
            case 7:dayString = "07";break;
            case 8:dayString = "08";break;
            case 9:dayString = "09";break;
            case 10:dayString = "10";break;
            case 11:dayString = "11";break;
            case 12:dayString = "12";break;
            case 13:dayString = "13";break;
            case 14:dayString = "14";break;
            case 15:dayString = "15";break;
            case 16:dayString = "16";break;
            case 17:dayString = "17";break;
            case 18:dayString = "18";break;
            case 19:dayString = "19";break;
            case 20:dayString = "20";break;
            case 21:dayString = "21";break;
            case 22:dayString = "22";break;
            case 23:dayString = "23";break;
            case 24:dayString = "24";break;
            case 25:dayString = "25";break;
            case 26:dayString = "26";break;
            case 27:dayString = "27";break;
            case 28:dayString = "28";break;
            case 29:dayString = "29";break;
            case 30:dayString = "30";break;
            case 31:dayString = "31";break;
        }
        endDate = d_endDate.getYear() + "-" + monthString + "-" + dayString;

        Toast.makeText(getBaseContext(), endDate,
                Toast.LENGTH_SHORT).show();



        details = e_details.getText().toString();
        if(c_sao.isChecked()== true){
            postTo.add("student affairs");
        }
        if(c_faculty.isChecked()== true){
            postTo.add("faculty");

        }
        if(c_dean.isChecked()== true){
            postTo.add("dean");
        }
        if(c_guidance.isChecked()== true){
            postTo.add("guidance");
        }
        if(c_pastoral.isChecked()== true){
            postTo.add("pastoral animation");
        }
        if(c_library.isChecked()== true){
            postTo.add("library");
        }
        if(c_trc.isChecked()== true){
            postTo.add("training and research");
        }
        if(c_it.isChecked()== true){
            postTo.add("i.t. student org");
        }
        if(c_ie.isChecked()== true){
            postTo.add("i.e. student org");
        }
        if(c_te.isChecked()== true){
            postTo.add("t.e. student org");
        }
        if(c_me.isChecked()== true){
            postTo.add("m.e. student org");
        }
        if(c_ece.isChecked()== true){
            postTo.add("e.c.e. student org");
        }

        if(c_all.isChecked() == true){
            viewableTo.add("all");
        }
        if(c_employee.isChecked() == true){
            viewableTo.add("employees only");
        }
        if(c_viewFaculty.isChecked() == true){
            viewableTo.add("faculty");
        }
        if(c_personVp.isChecked() == true){
            viewableTo.add("personnel under vp of ed");
        }
        if(c_personRector.isChecked() == true){
            viewableTo.add("personnel under rector");
        }

        if(r_sao.isChecked() == true){
            confirmBy = "student affairs officer";
        }
        else if(r_pastoral.isChecked() == true){
            confirmBy = "pastoral animator";
        }
        else if(r_chairperson.isChecked() == true){
            confirmBy = "chairpersons";
        }
        else if(r_dean.isChecked() == true){
            confirmBy = "dean";
        }
        else if(r_rector.isChecked() == true){
            confirmBy = "rector";
        }
    }

    private void setSelectedInformation(){
        e_title.setText(select_title);
        updateEndDate();
        e_details.setText(select_details);
        //Log.e("string", select_postTo);
        post_token = select_postTo.split(",",12);
        //Log.e("string2", post_token[1]);
        postToTokenizer();
        //view_token = select_viewTo.split(delim,5);
        setConfirmBy();
        e_remarks.setText(select_remarks);
    }

    private void setConfirmBy(){
        if(select_confirmBy.equals("student affairs officer")){
            r_sao.setChecked(true);
        }
        else if(select_confirmBy.equals("pastoral animator")){
            r_pastoral.setChecked(true);
        }
        else if(select_confirmBy.equals("chairpersons")){
            r_chairperson.setChecked(true);
        }
        else if(select_confirmBy.equals("dean")){
            r_dean.setChecked(true);
        }
        else if(select_confirmBy.equals("rector")){
            r_rector.setChecked(true);
        }
    }

    private void viewToTokenizer(){
        for(int i=0;i<view_token.length;i++){
            if(view_token[i].equals("all")){
                c_all.setChecked(true);
            }
            if(view_token[i].equals("employees only")){
                c_employee.setChecked(true);
            }
            if(view_token[i].equals("faculty")){
                c_viewFaculty.setChecked(true);
            }
            if(view_token[i].equals("personnel under vp of ed")){
                c_personVp.setChecked(true);
            }
            if(view_token[i].equals("personnel under rector")) {
                c_personRector.setChecked(true);
            }
        }
    }

    private void postToTokenizer(){
        for(int i=0;i<post_token.length;i++){
            if(post_token[i].equals("student affairs")){
                c_sao.setChecked(true);
            }
            if(post_token[i].equals("faculty")){
                c_faculty.setChecked(true);
            }
            if(post_token[i].equals("dean")){
                c_dean.setChecked(true);
            }
            if(post_token[i].equals("guidance")){
                c_guidance.setChecked(true);
            }
            if(post_token[i].equals("pastoral animation")){
                c_pastoral.setChecked(true);
            }
            if(post_token[i].equals("library")){
                c_library.setChecked(true);
            }
            if(post_token[i].equals("training and research")){
                c_trc.setChecked(true);
            }
            if(post_token[i].equals("i.t. student org")){
                c_it.setChecked(true);
            }
            if(post_token[i].equals("i.e. student org")){
                c_ie.setChecked(true);
            }
            if(post_token[i].equals("t.e. student org")){
                c_te.setChecked(true);
            }
            if(post_token[i].equals("m.e. student org")){
                c_me.setChecked(true);
            }
            if(post_token[i].equals("e.c.e. student org")){
                c_ece.setChecked(true);
            }
        }
    }

    private void updateEndDate()
    {
        String day  = /*Integer.parseInt(*/endDateDay.substring(8, 10)/*)*/;
        int dayInt = Integer.parseInt(day);
        Toast.makeText(getApplicationContext(), "day "+ day,
                Toast.LENGTH_LONG).show();
        int year = Integer.parseInt(endDateYear.substring(0, 4));
        Toast.makeText(getApplicationContext(), "year "+ year,
                Toast.LENGTH_LONG).show();
        String month = endDateMonth.substring(5,7);
        int month_code = getMonthInt(month);
        Toast.makeText(getApplicationContext(), "month "+month,
                Toast.LENGTH_LONG).show();

        d_endDate.updateDate(year, month_code, dayInt);
    }

    private int getMonthInt(String month) {
        if (month.equalsIgnoreCase("01")) return 0;
        if (month.equalsIgnoreCase("02")) return 1;
        if (month.equalsIgnoreCase("03")) return 2;
        if (month.equalsIgnoreCase("04")) return 3;
        if (month.equalsIgnoreCase("05")) return 4;
        if (month.equalsIgnoreCase("06")) return 5;
        if (month.equalsIgnoreCase("07")) return 6;
        if (month.equalsIgnoreCase("08")) return 7;
        if (month.equalsIgnoreCase("09")) return 8;
        if (month.equalsIgnoreCase("10")) return 9;
        if (month.equalsIgnoreCase("11")) return 10;
        if (month.equalsIgnoreCase("12")) return 11;

        //return -1 if month code not found
        return -1;
    }

    private void insertARF(){
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
                postToFull = postToFull + "," + postTo.get(i);
            }
        }
        if(viewableTo.size() == 0){
            viewableToFull = null;
        }else{
            viewableToFull = viewableTo.get(0);
            for(int j=1;j<viewableTo.size();j++){
                viewableToFull = viewableToFull + "," + viewableTo.get(j);
            }
        }

        nameValuePairs.add(new BasicNameValuePair("confirmBy", confirmBy));
        nameValuePairs.add(new BasicNameValuePair("postTo", postToFull));
        nameValuePairs.add(new BasicNameValuePair("viewableTo", viewableToFull));
        nameValuePairs.add(new BasicNameValuePair("remarks", remarks));
        nameValuePairs.add(new BasicNameValuePair("category", category));


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
    }

    private void selectARFDetails(){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("user", username));
        nameValuePairs.add(new BasicNameValuePair("arf_id", arf_id));



        try {
            HttpClient httpclient = new DefaultHttpClient();
            //HttpPost httppost = new HttpPost("http://192.168.0.100/website_android/android_insert_arf.php");
            HttpPost httppost = new HttpPost("http://192.168.0.101/website_android/android_select_arf_details.php");
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
                JSONArray json_array = new JSONArray(result);
                int modulus;
                for (int i = 0; i < json_array.length(); i++) {
                    modulus = i % 7;
                    if(modulus == 0){
                        select_title = json_array.optString(i);
                        e_title.setText(select_title);
                        Log.e("modulus0 ", "was here");
                    }
                    if(modulus == 1){
                        select_endDate = json_array.optString(i);
                        endDateDay = json_array.optString(i);
                        endDateMonth = json_array.optString(i);
                        endDateYear = json_array.optString(i);
                        Log.e("modulus1 ", "was here");
                        updateEndDate();
                        Log.e("after updateDate ", "was here");
                    }
                    if(modulus == 2){
                        select_details = json_array.optString(i);
                        e_details.setText(select_details);
                        Log.e("modulus2 ", "was here");
                    }
                    if(modulus == 3){
                        select_postTo = json_array.optString(i);
                        post_token = select_postTo.split(",", 12);
                        Log.e("modulus3 ", "was here");
                        postToTokenizer();
                        Log.e("after post token ", "was here");
                    }
                    if(modulus == 4){
                        select_viewTo = json_array.optString(i);
                        view_token = select_viewTo.split(",", 5);
                        Log.e("modulus4 ", "was here");
                        viewToTokenizer();
                        Log.e("after view token", "was here");
                    }
                    if(modulus == 5){
                        select_confirmBy = json_array.optString(i);
                        Log.e("modulus5 ", "was here");
                        setConfirmBy();
                        Log.e("after set confirmby ", "was here");

                    }
                    if(modulus == 6){
                        select_remarks = json_array.optString(i);
                        e_remarks.setText(select_remarks);
                        Log.e("modulus6 ", "was here");
                    }


                }


            } catch (ArrayIndexOutOfBoundsException a){
                Log.e("array outbound", a.getLocalizedMessage());
            } catch (Exception e) {
                Log.e("details Fail 2", e.toString());
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
