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
import java.util.List;

public class FullMaps extends AppCompatActivity {
public ListView listView;

    ArrayList<DataOfMap> dataOfMaps = new ArrayList<DataOfMap>();
    BoxAdapter boxAdapter;

    public static ArrayList<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;
public static int position;
    public  void fillData()
    {
        String selectQuery = "SELECT way_name,dateOfway FROM Way";
        Cursor c = MainActivity.myDatabase.rawQuery(selectQuery,null);
        if(c.moveToFirst()){
            do{
                //assing values
                dataOfMaps.add(new DataOfMap(c.getString(0),c.getString(1),"Bla bla",getCoordinate(c.getString(0))));
                list.add(c.getString(0));
            }while(c.moveToNext());
        }

        c.close();

    }
    private List<Coordinate> getCoordinate(String name)
    {
        List<Coordinate> list=new ArrayList<>();
        String id="";
        String selectQuery1 = "SELECT id_of_way FROM Way WHERE way_name= ?";
        Cursor c1 = MainActivity.myDatabase.rawQuery(selectQuery1, new String[]{name});
        if (c1.moveToFirst()) {
            id = c1.getString(c1.getColumnIndex("id_of_way"));
        }
        c1.close();


        String selectQuery = "SELECT Latitude,Longitude FROM Way_of_Data where id_of_way=?";
        Cursor c2 = MainActivity.myDatabase.rawQuery(selectQuery,new String[]{id});
        int count=0;

        if(c2.moveToFirst()){
            do{

                if(count>0){


                    try {
                        list.add( new Coordinate(c2.getString(0), c2.getString(1)));
                    }
                    catch (Exception e){}
                }
                list.add( new Coordinate(c2.getString(0), c2.getString(1)));

                count++;


            }while(c2.moveToNext());
        }
        c2.close();
        return list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_maps);
        listView=(ListView)findViewById(R.id.list_map) ;

        // создаем адаптер
        fillData();
        boxAdapter = new BoxAdapter(this, dataOfMaps);

        // настраиваем список
        listView = (ListView) findViewById(R.id.list_map);
        listView.setAdapter(boxAdapter);
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
