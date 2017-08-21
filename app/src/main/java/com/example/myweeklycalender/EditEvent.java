package com.example.myweeklycalender;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;


public class EditEvent extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
     Calendar eStart;
     Calendar eFinish;
     String eDesc, eLocation;

    CalendarEvent cEvent ; // current event

    EditText stTm, edTm, desc,loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_event);

        stTm = (EditText) findViewById(R.id.start_date_text);
        edTm = (EditText) findViewById(R.id.end_date_text);
        desc = (EditText) findViewById(R.id.description_text);
        loc = (EditText) findViewById(R.id.location_text);



        cEvent = Global.refEvent;
        eStart = cEvent.getStartTime();
        eFinish = cEvent.getEndTime();
        eDesc = cEvent.getTitle();
      //  eLocation = cEvent.getLocation();  // Check this>> Calendar object does not keep the location ??


        desc.setText(eDesc);
        SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String stime = formatter.format(eStart.getTime());
        stTm.setText(stime);
        String etime = formatter.format(eFinish.getTime());
        edTm.setText(etime);


      //  Log.d(LOG_TAG, String.format("Received event:  %s", cEvent.getTitle() ));



    }

    public void updateFromEdit(View v){
        // delete the current event and add the new one.
        delCurEvent();
        addEventToList();
        Toast.makeText(EditEvent.this,"Event Updated",Toast.LENGTH_LONG).show();
        finish();
    }

    public void cancelEdit(View v){

        Log.d(LOG_TAG, String.format("Going to finish this activity...  " ));
        finish();
    }


    public void deleteCurrEvent(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure,You want to delete this event?");

        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                delCurEvent();
                Toast.makeText(EditEvent.this,"Event Deleted",Toast.LENGTH_LONG).show();
                finish();
            }
        });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void delCurEvent(){
        Boolean needDel = false;
        CalendarEvent delEvnt = null;
        Log.d(LOG_TAG, String.format("Going to delete the current event...  " ));
        for(CalendarEvent ce : Global.eventList){
            if(cEvent.getId() == ce.getId()){
                needDel = true;
                delEvnt = ce;
                break;
            }
        }
        if(needDel){
            Global.eventList.remove(delEvnt);
        }
    }

    public void addEventToList(){
        String startTimeStr, endTimeStr, descStr,locationStr;
        Calendar startTime= Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();

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
        }
        startTime.setTime(d1);


        try {
            d1 = form.parse(endTimeStr);// test
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        endTime.setTime(d1);


        BaseCalendarEvent event1 = new BaseCalendarEvent(descStr, " ", locationStr,
                ContextCompat.getColor(this, R.color.orange_dark), startTime, endTime, true);
        Random r = new Random();
        int i1 = r.nextInt(88888) + 10001;

        //  final int rd = Random.nextInt(10000) + 89999;
        long rid= (long) i1;
        event1.setId(rid);
        event1.setStartTime(startTime);
        event1.setEndTime(endTime);
        event1.setDescription(descStr);
        event1.setLocation(locationStr);
        Global.eventList.add( event1);
        Log.d(LOG_TAG, String.format("New event added to the Global list  " ));
    }

}
