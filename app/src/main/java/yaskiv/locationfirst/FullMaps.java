package yaskiv.locationfirst;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class FullMaps extends AppCompatActivity {
ListView listView;
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
    }
}
