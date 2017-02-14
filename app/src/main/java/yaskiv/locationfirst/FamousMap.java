package yaskiv.locationfirst;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import static yaskiv.locationfirst.ListStaticLisenner.getMap;
import static yaskiv.locationfirst.ListStaticLisenner.setMap;

public class FamousMap  extends AppCompatActivity {
private static List<DataOfMap> map = new ArrayList<>();

    private void addToList(DataOfMap m)
    {
        final boolean add = map.add(m);
        Log.d("Res", String.valueOf(add));
    }
    private ListView listView;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_famous_map);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
         listView =(ListView)findViewById(R.id.list_famous_map);
        DatabaseReference myRef = database.getReference("Map");
        context=this;
final ListStaticLisenner l=new ListStaticLisenner();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                   List<Coordinate> coordinateList= new ArrayList<>();
                    String name = (String) messageSnapshot.child("name").getValue();
                     List <String> message = (List<String>) messageSnapshot.child("ListOfCoordinate").getValue();
                    for(int i =0 ; i<message.size();i+=2)
                    {
                        Coordinate coordinate=new Coordinate(String.valueOf(message.get(i)),String.valueOf(message.get(i+1)));
                        coordinateList.add(coordinate);
                    }
                  DataOfMap dateOfMap=new DataOfMap(name,"","",coordinateList);
                   map.add(dateOfMap);

                    coordinateList.clear();

                    }
                setMap(map);
                List<DataOfMap> map1=new ArrayList<>(getMap());
                BoxAdapter boxAdapter = new BoxAdapter(context, getMap());

                // настраиваем список

                listView.setAdapter(boxAdapter);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DBFireBase", "Failed to read value.", error.toException());
            }
        });




    }


}
