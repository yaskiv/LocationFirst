package yaskiv.locationfirst;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class FamousMap <T,V> extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_famous_map);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Map");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    String name = (String) messageSnapshot.child("name").getValue();
                    Log.d("DBFireBase", "name is: " + name);
                   List <String> message = (List<String>) messageSnapshot.child("ListOfCoordinate").getValue();
                    Log.d("DBFireBase", "List is: " + message.toString());
                }



               /*
                HashMap <T,V>value = (HashMap<T, V>) dataSnapshot.getValue();


V vc=value.get("Name1");
                Collection<V> vsdv=value.values();
               List<V>value1= new ArrayList<V>(vsdv) ;
                V vc1=value1.get(0);
                V vc2=value1.get(1);


                Log.d("DBFireBase", "V is: " + vc1.toString());
                Log.d("DBFireBase", "V is: " + vc2.toString());


             //   Log.d("DBFireBase", "Count child is: " +  dataSnapshot.getChildrenCount());

                Log.d("DBFireBase", "Value is: " + value.toString());

                */
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DBFireBase", "Failed to read value.", error.toException());
            }
        });
    }


}
