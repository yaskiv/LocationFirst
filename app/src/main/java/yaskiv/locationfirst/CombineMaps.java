package yaskiv.locationfirst;

/**
 * Created by vladyslavkostenko on 11/02/2017.
 */

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
import java.util.Date;

public class CombineMaps extends AppCompatActivity {
    public ListView listView;

    ArrayList<DataOfMap> screenShots = new ArrayList<DataOfMap>();
    BoxAdapterForComposing boxAdapterForComposing;

    public static ArrayList<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;
    public static int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_maps);

        // создаем адаптер
        fillData();
        boxAdapterForComposing = new BoxAdapterForComposing(this, screenShots);

        // настраиваем список
        ListView lvMain = (ListView) findViewById(R.id.list_maps_to_compose);
        lvMain.setAdapter(boxAdapterForComposing);
    }

    // генерируем данные для адаптера
    void fillData() {
        String selectQuery = "SELECT way_name FROM Way";
        Cursor c = MainActivity.myDatabase.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                //assing values

                screenShots.add(new DataOfMap(c.getString(0), "description",
                        "bla", false));
//                list.add(c.getString(0));
            } while (c.moveToNext());

        }
    }
    // выводим информацию о корзине
    public void showResult(View v) {
        String result = "Screenshots to compose:";
        for (DataOfMap s : boxAdapterForComposing.getBox()) {
            if (s.box)
                result += "\n" + s.name;
        }
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }

    //listView = (ListView) findViewById(R.id.list_map);



//        adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1,
//                list);
//        listView.setAdapter(adapter);
//        c.close();
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {
//
//
//                FullMaps.position = position;
//                startActivity(new Intent(FullMaps.this, MapsActivity_for_History.class));
//            }
//        });

//    }


}
