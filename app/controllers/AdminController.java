package controllers;

import controllers.Security.AuthAdmin;
import controllers.Security.Secured;
import play.api.Environment;
import play.mvc.*;
import play.data.*;
import play.db.ebean.Transactional;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import views.html.admin.*;

import models.FlightSchedule;
import models.users.User;
import models.*;

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

    public Result flights() {
        List<FlightSchedule> flightsList = FlightSchedule.findAll();
        return ok(views.html.admin.displayFlights.render(flightsList, getUserFromSession()));
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
        newFlight.save();
        flash("success", "Flight " + newFlight.getFlightID() + " has been created/ updated");
        return redirect(controllers.routes.AdminController.flights());
    }

    @Transactional
    public Result deleteFlight(int id){
        FlightSchedule.find.ref(id).delete();
        flash("success", "Flight has been deleted");
        return redirect(controllers.routes.AdminController.flights());
    }

    @Transactional
    public Result updateFlight(int id) {
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