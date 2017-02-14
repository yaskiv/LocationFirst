package yaskiv.locationfirst;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaski on 14.02.2017.
 */

public class ListStaticLisenner {
    public static List<DataOfMap> getMap() {
        return map;
    }

    public static void setMap(List<DataOfMap> map) {
        ListStaticLisenner.map = map;
    }

    public static List<DataOfMap> map=new ArrayList<>() ;
}
