package com.example.myweeklycalender;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;


public class AddEventActivity extends AppCompatActivity {


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    String startTimeStr, endTimeStr, descStr,locationStr;
    int eventType; // 1 - Personal, 2 - Social, 0 - Academic
    Button eventTypeBtn;
    int colType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event);

        eventTypeBtn = (Button) findViewById(R.id.button2);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
      //  client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void addEventInMain(View v) {
        EditText stTm = (EditText) findViewById(R.id.start_date_text);
        EditText edTm = (EditText) findViewById(R.id.end_date_text);
        EditText desc = (EditText) findViewById(R.id.description_text);
        EditText loc = (EditText) findViewById(R.id.location_text);

        startTimeStr = stTm.getText().toString();
        endTimeStr = edTm.getText().toString();
        descStr = desc.getText().toString();
        locationStr = loc.getText().toString();

        //Toast.makeText(this, "Add Event Clicked", Toast.LENGTH_LONG).show();



        addEventToList();
        Toast.makeText(this, "Add Event Clicked. ##EVNETS: "+Global.eventList.size(), Toast.LENGTH_LONG).show();

    }

    public void selectEventType(View v) {
        eventType ++;
        if(eventType >2 )eventType = 0;
        switch(eventType){
            case 1:
                eventTypeBtn.setText("Personal");
                colType =   ContextCompat.getColor(this,  R.color.orange_dark);
                break;
            case 2:
                eventTypeBtn.setText("Social");
                colType =   ContextCompat.getColor(this,  R.color.yellow);
                break;
            case 0:
                eventTypeBtn.setText("Academic");
                colType =   ContextCompat.getColor(this,  R.color.blue_dark);
        }
        eventTypeBtn.setBackgroundColor(colType);
    }

    public void addEventToList(){

        Calendar startTime= Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();


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
                colType, startTime, endTime, true);
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
    }

}
