package models;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.persistence.*;

import play.data.validation.*;

import com.avaje.ebean.*;

@Entity
public class FlightSchedule extends Model {
    @Id
    private Long flight_ID;
    @Constraints.Required
    private String origin;
    @ManyToOne
    private Destination destination;
    @Constraints.Required
    private String city;
    @Constraints.Required
    private Date departure_date;
    @Constraints.Required
    private String departure_time;
    @Constraints.Required
    private String arrival_time;
    @Constraints.Required
    private int seats;

    public FlightSchedule(){

    }

    public FlightSchedule(Long id, Destination destination, String city, String origin, Date departure_date, String departure_time, String arrival_time, int seats){
        this.flight_ID = id;
        this.destination = destination;
        this.setCity(city);
        this.origin = origin;
        this.departure_date = departure_date;
        this.departure_time = departure_time;
        this.arrival_time = arrival_time;
        this.setSeats(seats);
    }

    public static Finder<Long, FlightSchedule> find = new Finder<Long,FlightSchedule>(FlightSchedule.class);

    public static List<FlightSchedule> findAll(){
        return FlightSchedule.find.all();
    }

    public static List<FlightSchedule> findAll(String filter){
        return FlightSchedule.find.where()
                .ilike("city", "%" + filter + "%")
                .orderBy("city asc")
                .findList();
    }

    public static List<FlightSchedule> findFilter(Long destID, String filter){
        return FlightSchedule.find.where()
                .eq("destination.id", destID)
                .ilike("city", "%" + filter + "%")
                .orderBy("city asc")
                .findList();
    }

    public Long getFlight_ID() {
        return flight_ID;
    }

    public void setFlight_ID(Long flight_ID) {
        this.flight_ID = flight_ID;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDeparture_date() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateInString = sdf.format(departure_date);
        return dateInString;
    }

    public Date getDate(){
        return departure_date;
    }

    public void setDeparture_date(Date departure_date) {
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

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

}