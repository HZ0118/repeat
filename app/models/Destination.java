package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.Model;
import play.data.validation.*;

@Entity
public class Destination extends Model{
    @Id
    private Long id;
    @Constraints.Required
    private String destination;
    @OneToMany
    private List<FlightSchedule> flights;

    public Destination(){
    }

    public Destination(Long id, String destination, List<FlightSchedule> flights){
        this.setId(id);
        this.setDestination(destination);
        this.setFlights(flights);
    }

    public static Finder<Long, Destination> find = new Finder<Long, Destination>(Destination.class);

    public static List<Destination> findAll(){
        return Destination.find.where().orderBy("destination asc").findList();
    }

    public static Map<String, String> options(){
        LinkedHashMap<String, String> options = new LinkedHashMap<>();
        for(Destination d: Destination.findAll()){
            options.put(d.getId().toString(), d.getDestination());
        }
        return options;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public List<FlightSchedule> getFlights() {
        return flights;
    }

    public void setFlights(List<FlightSchedule> flights) {
        this.flights = flights;
    }
}
