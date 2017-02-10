package yaskiv.locationfirst;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.Date;

import static android.R.attr.bitmap;
import static yaskiv.locationfirst.MainActivity.context;
import static yaskiv.locationfirst.R.id.imageView;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public static GoogleMap mMap;
    private Button buttonScreen;
private  View rootView;
   public static Bitmap mbitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        buttonScreen=(Button)findViewById(R.id.button_screenshot);
        buttonScreen.setOnClickListener(TakeScreen);
      rootView = getWindow().getDecorView().findViewById(android.R.id.content);
    }
    public static Bitmap getScreenShot(View view) {
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }
    static  String dirPath1="";
    public static void store(Bitmap bm, String fileName){
        final  String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Screenshots";
        dirPath1=dirPath;
        File dir = new File(dirPath);
        if(!dir.exists())
            dir.mkdirs();
        File file = new File(dirPath, fileName);
        dirPath1=file.getAbsolutePath();
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void shareImage(File file){
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");

        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        try {
            startActivity(Intent.createChooser(intent, "Share Screenshot"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No App Available", Toast.LENGTH_SHORT).show();
        }
    }
    public Bitmap getBitmapOFRootView(View v) {
        View rootview = v.getRootView();
        rootview.setDrawingCacheEnabled(true);
        Bitmap bitmap1 = rootview.getDrawingCache();
        return bitmap1;
    }
String path;
    public  static File file1;

    @Override

    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults){

        switch(permsRequestCode){

            case 200:

                boolean audioAccepted = grantResults[0]==PackageManager.PERMISSION_GRANTED;

                boolean cameraAccepted = grantResults[1]== PackageManager.PERMISSION_GRANTED;

                break;

        }

    }
    private View.OnClickListener TakeScreen = new View.OnClickListener() {
        public void onClick(View v)
        {

            mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                public void onMapLoaded() {
                    mMap.snapshot(new GoogleMap.SnapshotReadyCallback() {
                        public void onSnapshotReady(Bitmap bitmap) {
                            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
                            path=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) +"/"+String.valueOf( System.currentTimeMillis()) +".jpg";

                            String[] perms = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};

                            int permsRequestCode = 200;

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions(perms, permsRequestCode);
                            }

                            //DateFormat.getDateTimeInstance().format(new Date())
                            Log.d("PAth", path);

                            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) ,String.valueOf( System.currentTimeMillis()) +".jpg");

                            file1=file;
                            try {
                                file.createNewFile();
                                FileOutputStream outputStream = new FileOutputStream(file);
                                outputStream.write(bytes.toByteArray());
                                outputStream.close();
startActivity(new Intent(MapsActivity.this,Share.class));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
        }};





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
