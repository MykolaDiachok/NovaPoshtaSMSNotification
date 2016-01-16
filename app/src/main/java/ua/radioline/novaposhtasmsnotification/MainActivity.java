package ua.radioline.novaposhtasmsnotification;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;


import android.telephony.SmsManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseAnalytics;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ua.radioline.novaposhtasmsnotification.basic.BaseValues;
import ua.radioline.novaposhtasmsnotification.basic.InternetDocument;
import ua.radioline.novaposhtasmsnotification.fragment.GalleryFragment;
import ua.radioline.novaposhtasmsnotification.fragment.MainFragment;
import ua.radioline.novaposhtasmsnotification.fragment.SendFragment;
import ua.radioline.novaposhtasmsnotification.fragment.ShareFragment;
import ua.radioline.novaposhtasmsnotification.fragment.ToolsFragment;
import ua.radioline.novaposhtasmsnotification.idoc.InternetDocumentAsyncTask;
import ua.radioline.novaposhtasmsnotification.idoc.InternetDocumentOnTaskCompleted;
import ua.radioline.novaposhtasmsnotification.sms.BasicSendSMS;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,InternetDocumentOnTaskCompleted {

    public static Context contextOfApplication;
    public static Context getContextOfApplication() {
        return contextOfApplication;
    }
    private Fragment cur_fragment = new GalleryFragment();
    private String keyValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
        contextOfApplication = getApplicationContext();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //      .setAction("Action", null).show();

                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                String currentDate = sdf.format(new Date());
                new InternetDocumentAsyncTask(MainActivity.this,MainActivity.this).execute(currentDate);
//                SmsManager smsManager = SmsManager.getDefault();
//                smsManager.sendTextMessage("+380676112798", null, "sms message", null, null);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        keyValue = BaseValues.GetValue("KeyAPI");
        if (keyValue.isEmpty())
            cur_fragment = new ToolsFragment();

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame, cur_fragment).commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fm = getFragmentManager();
        if (id == R.id.nav_gallery) {
            cur_fragment = new GalleryFragment();

        } else if (id == R.id.nav_manage) {
            cur_fragment = new ToolsFragment();

        } else if (id == R.id.nav_share) {
            cur_fragment = new ShareFragment();

        } else if (id == R.id.nav_send) {
            cur_fragment = new SendFragment();
        }
        fm.beginTransaction().replace(R.id.content_frame,cur_fragment).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onTaskCompleted(ArrayList<InternetDocument> internetDocuments) {
        BasicSendSMS basicSend = new BasicSendSMS(MainActivity.this);
        for (InternetDocument idoc:internetDocuments
             ) {
            if (!idoc.SendSMS){
                basicSend.SendSMS(idoc);
            }
        }
    }
}
