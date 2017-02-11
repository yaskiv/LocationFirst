package yaskiv.locationfirst;

import java.util.Date;

/**
 * Created by vladyslavkostenko on 10/02/2017.
 */

public class DataOfMap {

        String name;
        String description;
        String date;
        boolean box;


       public DataOfMap(String _name, String _description, String _date) {
            name = _name;
            description = _description;
            date = _date;
        }
       public DataOfMap(String _name, String _description,  String _date, boolean _box) {
            name = _name;
            description = _description;
            date = _date;
            box = _box;

        }
    }