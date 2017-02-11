package yaskiv.locationfirst;

/**
 * Created by vladyslavkostenko on 11/02/2017.
 */


import android.content.ContentValues;
import android.database.Cursor;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CombineMaps extends AppCompatActivity {

    private List<DataOfMap> screenShots = new ArrayList<DataOfMap>();
    private  BoxAdapterForComposing boxAdapterForComposing;
    private EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_maps);

        // создаем адаптер
        fillData();
        boxAdapterForComposing = new BoxAdapterForComposing(this, screenShots);
editText= (EditText)findViewById(R.id.nameOfCombineMap);
        // настраиваем список
        ListView lvMain = (ListView) findViewById(R.id.list_maps_to_compose);
        lvMain.setAdapter(boxAdapterForComposing);
    }
private  List<Coordinate> getCoordinate(String name)
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
    // генерируем данные для адаптера
    void fillData() {
        String selectQuery = "SELECT way_name FROM Way";
        Cursor c = MainActivity.myDatabase.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                //assing values

                screenShots.add(new DataOfMap(c.getString(0), "description",
                        "bla", false,getCoordinate(c.getString(0))));
//                list.add(c.getString(0));
            } while (c.moveToNext());

        }
    }
    // выводим информацию о корзине
    public void showResult(View v) {
       // String result = "Screenshots to compose:";
        List<Coordinate> list=new ArrayList<>();
        for (DataOfMap s : boxAdapterForComposing.getBox()) {
            if (s.box){
                list.addAll(s.getList());
            list.add(new Coordinate("0","0"));}
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("way_name", editText.getText().toString());

        Date date = new Date();

        contentValues.put("dateOfway",date.getDate()+"-"+date.getMonth()+"-"+date.getYear());
        MainActivity.myDatabase.insert("Way", null,contentValues );
        String id="";
        String selectQuery = "SELECT id_of_way FROM Way WHERE way_name= ?";
        Cursor c = MainActivity.myDatabase.rawQuery(selectQuery, new String[]{editText.getText().toString()});
        if (c.moveToFirst()) {
            id = c.getString(c.getColumnIndex("id_of_way"));
        }
        c.close();
        Log.d("ID=", id);

        int i=0;
        for (Coordinate loc : list) {
            ContentValues contentValues1 = new ContentValues();
            contentValues1.put("id_of_way", id);
            contentValues1.put("Latitude", loc.getLatitude());
            contentValues1.put("Longitude", loc.getLongitude() );
            MainActivity.myDatabase.insert("Way_of_Data", null,contentValues1 );

            Log.d("ADD", String.valueOf(i));
            i++;
        }
        //Toast.makeText(this, result, Toast.LENGTH_LONG).show();
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
