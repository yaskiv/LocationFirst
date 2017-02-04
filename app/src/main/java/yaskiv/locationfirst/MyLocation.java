package yaskiv.locationfirst;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by yaski on 04.02.2017.
 */

public class MyLocation implements LocationListener {

    public static ArrayList<Location> listLocation=  new ArrayList<>();

    public static ArrayList<Location> getListLocation() {
        return listLocation;
    }

    static Location imHere; // здесь будет всегда доступна самая последняя информация о местоположении пользователя.

    public static void SetUpLocationListener(Context context) // это нужно запустить в самом начале работы программы
    {
        Log.d(TAG, "SetUpLocationListener: BLA");
        LocationManager locationManager = (LocationManager)
                context.getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new MyLocation();
        Log.d(TAG, "SetUpLocationListener: BLA1");
        if (ActivityCompat.checkSelfPermission(MainActivity.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 10, locationListener);

        Log.d(TAG, "SetUpLocationListener: BLA2");
        imHere = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(imHere!=null){
         listLocation.add(imHere);
            //Log.d(TAG, String.valueOf(imHere.getLatitude())+String.valueOf(imHere.getLongitude()));
            }
    }

    @Override
    public void onLocationChanged(Location loc) {
        imHere = loc;
    }
    @Override
    public void onProviderDisabled(String provider) {}
    @Override
    public void onProviderEnabled(String provider) {}
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}
}
