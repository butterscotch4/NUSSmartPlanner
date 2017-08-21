package com.example.myweeklycalender;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.tibolte.agendacalendarview.AgendaCalendarView;
import com.github.tibolte.agendacalendarview.CalendarPickerController;
import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.github.tibolte.agendacalendarview.models.DayItem;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements CalendarPickerController {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Bind(R.id.activity_toolbar)
    Toolbar mToolbar;
    @Bind(R.id.agenda_calendar_view)
    AgendaCalendarView mAgendaCalendarView;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    //List<CalendarEvent> eventList = new ArrayList<>();

    // region Lifecycle methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

         //mockList(Global.eventList);
        // addEventToList();


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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
        if (id == R.id.item1) {
            Intent myIntent = new Intent(this, AddEventActivity.class);
            this.startActivity(myIntent);

            return true;
        }

        if (id == R.id.item2) {
            Intent myIntent = new Intent(this, MeetingRequest.class);
            this.startActivity(myIntent);

            return true;
        }

        if (id == R.id.item3) {
            Intent myIntent = new Intent(this, EventFilter.class);
            this.startActivity(myIntent);

            return true;
        }

        if (id == R.id.item4) {
            Intent myIntent = new Intent(this, Bullettin.class);
            this.startActivity(myIntent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void refreshScreenEvents(View v) {
        // minimum and maximum date of our calendar
        // 2 month behind, one year ahead, example: March 2015 <-> May 2015 <-> May 2016
        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();

        minDate.add(Calendar.MONTH, -2);
        minDate.set(Calendar.DAY_OF_MONTH, 1);
        maxDate.add(Calendar.YEAR, 1);

        Toast.makeText(this, "#Events:: " + Global.eventList.size(), Toast.LENGTH_LONG).show();

        mAgendaCalendarView.init(Global.eventList, minDate, maxDate, Locale.getDefault(), this);
        mAgendaCalendarView.addEventRenderer(new DrawableEventRenderer());
    }

    protected void onResume() {
        super.onResume();
        refreshScreenEvents(null);
    }

    // endregion

    // region Interface - CalendarPickerController

    @Override
    public void onDaySelected(DayItem dayItem) {
        Log.d(LOG_TAG, String.format("Selected day: %s", dayItem));
    }

    @Override
    public void onEventSelected(CalendarEvent event) {
        Log.d(LOG_TAG, String.format("Selected event:  %s", event ));

        Global.refEvent = event; // to pass to editEvent task
        Intent myIntent = new Intent(this, EditEvent.class);
        this.startActivity(myIntent);
    }

    @Override
    public void onScrollToDate(Calendar calendar) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()));
        }
    }


    public void add_event(View v) {

        Toast.makeText(this, "Clicked", Toast.LENGTH_LONG).show();

        Intent myIntent = new Intent(this, AddEventActivity.class);
        this.startActivity(myIntent);

    }
    // endregion

    // region Private Methods

    private void mockList(List<CalendarEvent> eventList) {
        Calendar startTime1 = Calendar.getInstance();
        Calendar endTime1 = Calendar.getInstance();
        endTime1.add(Calendar.MONTH, 1);
        BaseCalendarEvent event1 = new BaseCalendarEvent("Thibault travels in Iceland", "A wonderful journey!", "Iceland",
                ContextCompat.getColor(this,  R.color.orange_dark), startTime1, endTime1, true);
        event1.setId(getEventId());
        eventList.add(event1) ;

        Calendar startTime2 = Calendar.getInstance();
        startTime2.add(Calendar.DAY_OF_YEAR, 1);
        Calendar endTime2 =   Calendar.getInstance();
        endTime2.add(Calendar.DAY_OF_YEAR, 3);
        BaseCalendarEvent event2 = new BaseCalendarEvent("Visit to Dalvík", "A beautiful small town", "Dalvík",
                ContextCompat.getColor(this, R.color.yellow), startTime2, endTime2, true);
        event2.setId(getEventId());
        eventList.add(event2);

        Calendar startTime3 = Calendar.getInstance();
        Calendar endTime3 = Calendar.getInstance();
        startTime3.set(Calendar.HOUR_OF_DAY, 14);
        startTime3.set(Calendar.MINUTE, 0);
        endTime3.set(Calendar.HOUR_OF_DAY, 15);
        endTime3.set(Calendar.MINUTE, 0);
        DrawableCalendarEvent event3 = new DrawableCalendarEvent("Visit of Harpa", "", "Dalvík",
                ContextCompat.getColor(this, R.color.blue_dark), startTime3, endTime3, false, android.R.drawable.ic_dialog_info);
        event3.setId(getEventId());
        eventList.add(event3);
    }

    public long getEventId(){
        Random r = new Random();
        int i1 = r.nextInt(88888) + 10001;

        //  final int rd = Random.nextInt(10000) + 89999;
        long rid= (long) i1;
        return rid;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.myweeklycalender/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.myweeklycalender/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


    // endregion
}
