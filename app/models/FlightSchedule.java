package models;

import java.util.*;
import javax.persistence.*;

import play.data.validation.*;

import com.avaje.ebean.*;

@Entity
public class FlightSchedule extends Model {
    @Id
    private int flight_ID;
    @Constraints.Required
    private String destination;
    @Constraints.Required
    private String origin;
    @Constraints.Required
    private String departure_date;
    @Constraints.Required
    private String departure_time;
    @Constraints.Required
    private String arrival_time;

    public FlightSchedule(){

    }

    public FlightSchedule(int id,String destination, String origin, String departure_date, String departure_time, String arrival_time){
        this.flight_ID = id;
        this.destination = destination;
        this.origin = origin;
        this.departure_date = departure_date;
        this.departure_time = departure_time;
        this.arrival_time = arrival_time;
    }

    public static Finder<Integer, FlightSchedule> find = new Finder<Integer,FlightSchedule>(FlightSchedule.class);

    public static List<FlightSchedule> findAll(){
        return FlightSchedule.find.all();
    }

    public int getFlight_ID() {
        return flight_ID;
    }

    public void setFlight_ID(int flight_ID) {
        this.flight_ID = flight_ID;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDeparture_date() {
        return departure_date;
    }

    public void setDeparture_date(String departure_date) {
        this.departure_date = departure_date;
    }

    public String getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(String departure_time) {
        this.departure_time = departure_time;
    }

    public String getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(String arrival_time) {
        this.arrival_time = arrival_time;
    }
}