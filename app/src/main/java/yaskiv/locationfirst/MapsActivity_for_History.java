package yaskiv.locationfirst;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class MapsActivity_for_History extends FragmentActivity implements OnMapReadyCallback {

    public static GoogleMap mMap;
    public static String Latitude="";
    public static String Longitude="";
    private View rootView;
    private Button buttonScreen;
    String path;
    private View.OnClickListener TakeScreen = new View.OnClickListener() {
        public void onClick(View v)
        {

            mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                public void onMapLoaded() {
                    mMap.snapshot(new GoogleMap.SnapshotReadyCallback() {
                        public void onSnapshotReady(Bitmap bitmap) {
                            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
                            path= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) +"/"+String.valueOf( System.currentTimeMillis()) +".jpg";

                            String[] perms = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};

                            int permsRequestCode = 200;

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions(perms, permsRequestCode);
                            }

                            //DateFormat.getDateTimeInstance().format(new Date())
                            Log.d("PAth", path);

                            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) ,String.valueOf( System.currentTimeMillis()) +".jpg");

                            MapsActivity.file1=file;
                            try {
                                file.createNewFile();
                                FileOutputStream outputStream = new FileOutputStream(file);
                                outputStream.write(bytes.toByteArray());
                                outputStream.close();
                                startActivity(new Intent(MapsActivity_for_History.this,Share.class));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
        }};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_for__history);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        buttonScreen=(Button)findViewById(R.id.button_screenshot_for_history);
        buttonScreen.setOnClickListener(TakeScreen);
        rootView = getWindow().getDecorView().findViewById(android.R.id.content);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;



                        String id="";
                        String selectQuery1 = "SELECT id_of_way FROM Way WHERE way_name= ?";
                        Cursor c1 = MainActivity.myDatabase.rawQuery(selectQuery1, new String[]{FullMaps.list.get(FullMaps.position)});
                        if (c1.moveToFirst()) {
                            id = c1.getString(c1.getColumnIndex("id_of_way"));
                        }
                        c1.close();


                        String selectQuery = "SELECT Latitude,Longitude FROM Way_of_Data where id_of_way=?";
                        Cursor c = MainActivity.myDatabase.rawQuery(selectQuery,new String[]{id});
                        int count=0;

                        if(c.moveToFirst()){
                            do{

                                if(count>0){


                                    try{
                                        if(!c.getString(0).equals("0")&&!c.getString(1).equals("0")){
                                        Polygon polygon = MapsActivity_for_History.mMap.addPolygon(new PolygonOptions()
                                                .add(new LatLng(Double.parseDouble(Latitude),Double.parseDouble( Longitude)),
                                                        new LatLng(Double.parseDouble(c.getString(0)),Double.parseDouble( c.getString(1)))
                                                )
                                                .strokeColor(Color.BLACK));

                                        Log.d("Polygon",Latitude+ "||"+Longitude);}
                                    else
                                        {
                                            count=0;
                                        }
                                    }
                                    catch (Exception e){}
                                }
                                if(!c.getString(0).equals("0")&&!c.getString(1).equals("0")) {

                                    Latitude = c.getString(0);
                                    Longitude = c.getString(1);

                                    count++;
                                }

                            }while(c.moveToNext());
                        }
                        c.close();



try{

    //mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(38.127,-126.617)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Double.parseDouble(Latitude),Double.parseDouble(Longitude ))));
        mMap.setMinZoomPreference(14);}
catch (Exception e){}
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
