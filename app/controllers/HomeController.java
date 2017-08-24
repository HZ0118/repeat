package controllers;

import play.api.Environment;
import play.mvc.*;
import play.data.*;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import views.html.*;

import models.*;
import models.users.User;


public class HomeController extends Controller {
    private FormFactory FormFactory;
    private Environment env;

    @Inject
    public HomeController(FormFactory f, Environment e){
        this.env = e;
        this.FormFactory = f;
    }

    private User getUserFromSession() {
        return User.getUserById(session().get("email"));
    }

    public Result index() {
        return ok(index.render(getUserFromSession()));
    }

    public Result flights(Long dest, String filter) {
        List<Destination> destinationList = Destination.findAll();
        List<FlightSchedule> flightsList = new ArrayList<FlightSchedule>();
        if(dest == 0){
            flightsList = FlightSchedule.findAll(filter);
        }
        else{
            flightsList = FlightSchedule.findFilter(dest, filter);
        }
        return ok(displayFlights.render(flightsList, destinationList, getUserFromSession(), env, dest, filter));
    }


}