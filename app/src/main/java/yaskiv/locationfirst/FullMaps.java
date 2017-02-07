package yaskiv.locationfirst;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;

public class FullMaps extends AppCompatActivity {
public ListView listView;
    public static String Latitude="";
    public static String Longitude="";
    ArrayList<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_maps);
        listView=(ListView)findViewById(R.id.list_map) ;

        String selectQuery = "SELECT way_name FROM Way";
        Cursor c = MainActivity.myDatabase.rawQuery(selectQuery,null);
  if(c.moveToFirst()){
            do{
                //assing values
                list.add(c.getString(0));
            }while(c.moveToNext());
        }

        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                list);
        listView.setAdapter(adapter);
        c.close();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {

                String selectQuery = "SELECT Latitude,Longitude FROM Way_of_Data where way_name=?";
                Cursor c = MainActivity.myDatabase.rawQuery(selectQuery,new String[]{list.get(position-1)});
                int count=0;

                if(c.moveToFirst()){
                    do{

                       if(count>0){



                        Polygon polygon = MapsActivity_for_History.mMap.addPolygon(new PolygonOptions()
                                .add(new LatLng(Double.parseDouble(Latitude),Double.parseDouble( Longitude)),
                                        new LatLng(Double.parseDouble(c.getString(0)),Double.parseDouble( c.getString(1)))
                                        )
                                .strokeColor(Color.RED));}
                        Latitude=c.getString(0);
                        Longitude=c.getString(1);

                        count++;


                    }while(c.moveToNext());
                }
                c.close();

                startActivity(new Intent(FullMaps.this,MapsActivity_for_History.class));
            }
        });

    }


}
