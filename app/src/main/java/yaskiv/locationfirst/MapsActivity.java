package yaskiv.locationfirst;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public static GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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
        LatLng myLocationInMoment = new LatLng(-34, 151);

           // Add a marker in Sydney and move the camera
        if (MyLocation.listLocation.size() != 0)
        {
            myLocationInMoment = new LatLng(MyLocation.listLocation.get(MyLocation.listLocation.size()-1).getLatitude(),
                    MyLocation.listLocation.get(MyLocation.listLocation.size()-1).getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocationInMoment));
            mMap.setMinZoomPreference(16);
        }
      //  mMap.addMarker(new MarkerOptions().position(myLocationInMoment).title("Marker in Sydney"));

    }
}
