package yaskiv.locationfirst;

import android.Manifest;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static Context context;
    public static boolean startOrStop = true;
    Button button;
    Button buttonStop;
    Button buttonSave;

    EditText text_for_name_of_map;

    private static final String DATABASE_NAME = "AllWay.db";
    public static  SQLiteDatabase myDatabase;

    private void createDatabase() {
        myDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE,
                null);

    }

    public static TextView textView;

    public Context getContext() {
        return context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DataBase dbh = new DataBase(this,DATABASE_NAME,null,1);
        myDatabase = dbh.getWritableDatabase();
        String svcName = Context.LOCATION_SERVICE;
        LocationManager locationManager = (LocationManager) getSystemService(svcName);
        createDatabase();
        context = this;
        text_for_name_of_map=(EditText)findViewById(R.id.text_for_name);
        button = (Button) findViewById(R.id.buttonLocation);
        textView = (TextView) findViewById(R.id.textLocation);
        button.setOnClickListener(mListener);
        buttonStop = (Button) findViewById(R.id.buttonLocationStop);
        buttonStop.setOnClickListener(stopListener);
        buttonSave = (Button) findViewById(R.id.buttonLocationSave);
        buttonSave.setOnClickListener(saveListener);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private View.OnClickListener saveListener = new View.OnClickListener() {
        public void onClick(View v) {
             ContentValues contentValues = new ContentValues();
            contentValues.put("way_name", text_for_name_of_map.getText().toString());

            Date date = new Date();

            contentValues.put("dateOfway",date.getDate()+"-"+date.getMonth()+"-"+date.getYear());

            myDatabase.insert("Way", null,contentValues );
String id="";
            String selectQuery = "SELECT id_of_way FROM Way WHERE way_name= ?";
            Cursor c = myDatabase.rawQuery(selectQuery, new String[]{text_for_name_of_map.getText().toString()});
            if (c.moveToFirst()) {
                id = c.getString(c.getColumnIndex("id_of_way"));
            }
            c.close();
            Log.d("ID=", id);

int i=0;
            Log.d("List of location",MyLocation.listLocation.toString());
            for (Location loc : MyLocation.listLocation) {
                ContentValues contentValues1 = new ContentValues();
                contentValues1.put("id_of_way", id);
                contentValues1.put("Latitude", loc.getLatitude());
                contentValues1.put("Longitude", loc.getLongitude() );
                myDatabase.insert("Way_of_Data", null,contentValues1 );

                Log.d("ADD", String.valueOf(i));
                i++;
            }
        }
    };

    private View.OnClickListener stopListener = new View.OnClickListener() {
        public void onClick(View v) {
            MyLocation.listLocation.clear();
            startOrStop = false;

        }
    };
    public static String s;
    private View.OnClickListener mListener = new View.OnClickListener() {
        public void onClick(View v) {
            MyLocation.SetUpLocationListener(context);
            startOrStop = true;
            Thread thread = new Thread() {
                @Override
                public void run() {

                    while (startOrStop) {

                        MyLocation.LocationManagerWork();
                        if (MyLocation.listLocation.size() != 0) {
                            s = String.valueOf(MyLocation.listLocation.get(
                                    MyLocation.listLocation.size() - 1)
                                    .getLatitude()) + " " + String.valueOf(MyLocation.listLocation.get(
                                    MyLocation.listLocation.size() - 1)
                                    .getLongitude());

                            Log.d("Set Text:", s);
                            Log.d("Size :", String.valueOf(MyLocation.listLocation.size()));
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    if (MyLocation.listLocation.size() > 1) {
                                        Polygon polygon = MapsActivity.mMap.addPolygon(new PolygonOptions()
                                                .add(new LatLng(MyLocation.listLocation.get(
                                                        MyLocation.listLocation.size() - 1)
                                                        .getLatitude(), MyLocation.listLocation.get(
                                                        MyLocation.listLocation.size() - 1)
                                                        .getLongitude()), new LatLng(MyLocation.listLocation.get(
                                                        MyLocation.listLocation.size() - 2)
                                                        .getLatitude(), MyLocation.listLocation.get(
                                                        MyLocation.listLocation.size() - 2)
                                                        .getLongitude()))
                                                .strokeColor(Color.RED)
                                        );

                                    }
                                    textView.setText(s);
                                }
                            });

                        }
                        try {
                            sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            thread.start();
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(intent);

        }
    };


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
startActivity(new Intent(MainActivity.this,SettingActivity.class));

            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.my_maps) {
            startActivity(new Intent(MainActivity.this,FullMaps.class));
            // Handle the camera action
        } else if (id == R.id.combine_maps) {
        startActivity(new Intent(MainActivity.this,CombineMaps.class));

        } else if (id == R.id.nav_slideshow) {
            startActivity(new Intent(MainActivity.this,FamousMap.class));
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {
            startActivity(new Intent(MainActivity.this,Share.class));

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
