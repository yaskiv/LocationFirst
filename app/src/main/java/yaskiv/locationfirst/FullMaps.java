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

    public static ArrayList<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;
public static int position;
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
            public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3)
            {


FullMaps.position=position;
                startActivity(new Intent(FullMaps.this,MapsActivity_for_History.class));
            }
        });

    }


}
