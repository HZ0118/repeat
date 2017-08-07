package controllers;

import controllers.Security.AuthAdmin;
import controllers.Security.Secured;
import models.Destination;
import play.mvc.*;
import play.data.*;
import play.db.ebean.Transactional;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import views.html.admin.*;

import models.FlightSchedule;
import models.users.User;

@Security.Authenticated(Secured.class)
@With(AuthAdmin.class)
public class AdminController extends Controller {
    private FormFactory formFactory;

    @Inject
    public AdminController(FormFactory f){
        this.formFactory = f;
    }

    private User getUserFromSession() {
        return User.getUserById(session().get("email"));
    }

    public Result flights(Long dest) {
        List<Destination> destinationList = Destination.findAll();
        List<FlightSchedule> flightsList = new ArrayList<FlightSchedule>();
        if(dest == 0){
            flightsList = FlightSchedule.findAll();
        }
        else {
            flightsList = Destination.find.ref(dest).getFlights();
        }
        return ok(views.html.admin.displayFlights.render(flightsList, destinationList, getUserFromSession()));
    }

    public Result addFlight(){
        Form<FlightSchedule> addFlightForm = formFactory.form(FlightSchedule.class);
        return ok(addFlights.render(addFlightForm, getUserFromSession()));
    }

    @Transactional
    public Result addFlightSubmit(){
        Form<FlightSchedule> newFlightForm = formFactory.form(FlightSchedule.class).bindFromRequest();
        if(newFlightForm.hasErrors()){
            return badRequest(addFlights.render(newFlightForm, getUserFromSession()));
        }
        FlightSchedule newFlight = newFlightForm.get();
        if(newFlight.getFlight_ID() == null) {
            newFlight.save();
        }
        else if (newFlight.getFlight_ID() != null) {
            newFlight.update();
        }
        flash("success", "Flight to " + newFlight.getCity() + " has been created/ updated");
        return redirect(controllers.routes.AdminController.flights(0));
    }

    @Transactional
    public Result deleteFlight(Long id){
        FlightSchedule.find.ref(id).delete();
        flash("success", "Flight has been deleted");
        return redirect(controllers.routes.AdminController.flights(0));
    }

    @Transactional
    public Result updateFlight(Long id) {
        FlightSchedule f;
        Form<FlightSchedule> flightForm;
        try{
            f = FlightSchedule.find.byId(id);
            flightForm = formFactory.form(FlightSchedule.class).fill(f);
        } catch (Exception ex) {
            return badRequest("error");
        }
        return ok(addFlights.render(flightForm, getUserFromSession()));
    }

}