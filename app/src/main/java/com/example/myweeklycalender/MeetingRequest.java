package com.example.myweeklycalender;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View ;
import android.widget.EditText;
import android.widget.Toast;

import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;


public class MeetingRequest extends AppCompatActivity {

    String startTimeStr, endTimeStr, descStr,locationStr;
    Boolean addEventSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_mtg);
        addEventSuccess = false;
    }

    public void addThenSend(View v){
        addEventToList();
        if(addEventSuccess) sendMtgRequest();
    }

    public void sendMtgRequest(){
       // Toast.makeText(this, "Send meeting request pressed", Toast.LENGTH_LONG).show();


        String textToSend = "Hi \n\nMay I request for a meeting to be scheduled as follows? \n\n   Start Time  : "
                +startTimeStr+"\n   End Time    : "+endTimeStr+"\n   Description : "+descStr+"\n   Location      : "+locationStr+
                "\n\nPlease confirm your availability. \n\n Thank you.";

        Intent textShareIntent = new Intent(Intent.ACTION_SEND);
        textShareIntent.putExtra(Intent.EXTRA_TEXT, textToSend);
        textShareIntent.setType("text/plain");
        startActivity(Intent.createChooser(textShareIntent, "Send Request with..."));
    }

    public void addEventToList(){

        Calendar startTime= Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();

        EditText stTm = (EditText) findViewById(R.id.start_date_text);
        EditText edTm = (EditText) findViewById(R.id.end_date_text);
        EditText desc = (EditText) findViewById(R.id.description_text);
        EditText loc = (EditText) findViewById(R.id.location_text);

        startTimeStr = stTm.getText().toString();
        endTimeStr = edTm.getText().toString();
        descStr = desc.getText().toString();
        locationStr = loc.getText().toString();


        SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        java.util.Date d1 = null;

        try {
            d1 = form.parse(startTimeStr);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Invalid Start Time", Toast.LENGTH_LONG).show();
            return;
        }

        startTime.setTime(d1);



        try {
            d1 = form.parse(endTimeStr);// test
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Invalid End Time", Toast.LENGTH_LONG).show();
            return;
        }

        endTime.setTime(d1);

        if(descStr.length() < 1) {
            Toast.makeText(this, "Invalid Description", Toast.LENGTH_LONG).show();
            return;
        }


        BaseCalendarEvent event1 = new BaseCalendarEvent(descStr, " ", locationStr,
                ContextCompat.getColor(this, R.color.yellow), startTime, endTime, true);
        Random r = new Random();
        int i1 = r.nextInt(88888) + 10001;  // creates a random number between 10001 and 10001+88888
        // Note: the event ID should be auto increment in the server database.  A bit complex to implement
        // so we go with a random number for now.  What is needed is a unique id.

        //  final int rd = Random.nextInt(10000) + 89999;
        long rid= (long) i1;
        event1.setId(rid);
        event1.setStartTime(startTime);
        event1.setEndTime(endTime);
        event1.setDescription(descStr);
        event1.setLocation(locationStr);
        Global.eventList.add( event1);
        addEventSuccess=true;
    }
}
