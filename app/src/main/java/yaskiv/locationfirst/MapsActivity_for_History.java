package yaskiv.locationfirst;

import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

public class MapsActivity_for_History extends FragmentActivity implements OnMapReadyCallback {

    public static GoogleMap mMap;
    public static String Latitude="";
    public static String Longitude="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_for__history);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
                        Cursor c1 = MainActivity.myDatabase.rawQuery(selectQuery1, new String[]{FullMaps.list.get(FullMaps.position-1)});
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
