package yaskiv.locationfirst;

/**
 * Created by yaski on 11.02.2017.
 */

public class Coordinate {

    private String Latitude;
    private String Longitude;
    public Coordinate(String latitude, String longitude) {
        Latitude = latitude;
        Longitude = longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

}
