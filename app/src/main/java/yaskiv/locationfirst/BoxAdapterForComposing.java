package yaskiv.locationfirst;

/**
 * Created by vladyslavkostenko on 11/02/2017.
 */

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class BoxAdapterForComposing extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<DataOfMap> objects;

    public BoxAdapterForComposing(Context context, ArrayList<DataOfMap> screenShots) {
        ctx = context;
        objects = screenShots;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
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
            view = lInflater.inflate(R.layout.item_for_composing, parent, false);
        }

        DataOfMap s = getScreenShot(position);

        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и картинка
        ((TextView) view.findViewById(R.id.tvName)).setText(s.name);
        ((TextView) view.findViewById(R.id.tvDescription)).setText(s.description);

        CheckBox cbBuy = (CheckBox) view.findViewById(R.id.cbBox);
        // присваиваем чекбоксу обработчик
        cbBuy.setOnCheckedChangeListener(myCheckChangeList);
        // пишем позицию
        cbBuy.setTag(position);
        // заполняем данными из товаров: в корзине или нет
        cbBuy.setChecked(s.box);
        return view;
    }

    // товар по позиции
    DataOfMap getScreenShot(int position) {
        return ((DataOfMap) getItem(position));
    }

    ArrayList<DataOfMap> getBox() {
        ArrayList<DataOfMap> box = new ArrayList<DataOfMap>();
        for (DataOfMap s : objects) {
            // если в корзине
            if (s.box)
                box.add(s);
        }
        return box;
    }

    // обработчик для чекбоксов
    OnCheckedChangeListener myCheckChangeList = new OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            // меняем данные товара (в корзине или нет)
            getScreenShot((Integer) buttonView.getTag()).box = isChecked;
        }
    };
}