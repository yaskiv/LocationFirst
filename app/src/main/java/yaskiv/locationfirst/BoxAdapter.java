package yaskiv.locationfirst;

/**
 * Created by vladyslavkostenko on 10/02/2017.
 */

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

class BoxAdapter extends BaseAdapter {
    private LayoutInflater lInflater;
    private ArrayList<DataOfMap> objects;

    BoxAdapter(Context context, ArrayList<DataOfMap> listOdData) {
        Context ctx = context;
        objects = listOdData;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int i) {
        return objects.get(i);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item, parent, false);
        }

        DataOfMap dataOfMap = getScreenShot(position);

        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и картинка
        ((TextView) view.findViewById(R.id.tvDescr)).setText(dataOfMap.name);
        ((TextView) view.findViewById(R.id.tvPrice)).setText(dataOfMap.description);


        return view;
    }

    // товар по позиции
    private DataOfMap getScreenShot(int position) {
        return ((DataOfMap) getItem(position));
    }

}