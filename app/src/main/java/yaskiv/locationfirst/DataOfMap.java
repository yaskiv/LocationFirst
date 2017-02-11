package yaskiv.locationfirst;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by vladyslavkostenko on 10/02/2017.
 */

public  class DataOfMap {

         String name;
       String description;
        String date;
        boolean box;
        List<Coordinate> list=new ArrayList<>();

    public List<Coordinate> getList() {
        return list;
    }

    public DataOfMap(String name, String description, String date, List<Coordinate> list) {
            this.name = name;
            this.description = description;
            this.date = date;
           this.list=list;
        }
       public DataOfMap(String _name, String _description,  String _date, boolean _box,List<Coordinate> list) {
            name = _name;
            description = _description;
            date = _date;
            box = _box;
           this.list=list;

        }
    }