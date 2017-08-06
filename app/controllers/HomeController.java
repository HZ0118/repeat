package controllers;

import controllers.*;
import play.api.Environment;
import play.mvc.*;
import play.data.*;
import play.db.ebean.Transactional;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import views.html.*;

import models.*;
import models.users.User;


public class HomeController extends Controller {
    private FormFactory FormFactory;

    @Inject
    public HomeController(FormFactory f){
        this.FormFactory = f;
    }

    private User getUserFromSession() {
        return User.getUserById(session().get("email"));
    }

    public Result index() {
        return ok(index.render(getUserFromSession()));
    }

    public Result flights() {
        List<FlightSchedule> flightsList = FlightSchedule.findAll();
        return ok(displayFlights.render(flightsList, getUserFromSession()));
    }



}