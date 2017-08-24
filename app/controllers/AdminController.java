package controllers;

import controllers.*;
import controllers.Security.AuthAdmin;
import controllers.Security.Secured;
import models.Destination;

import play.api.Environment;
import play.mvc.*;
import play.mvc.Http.*;
import play.mvc.Http.MultipartFormData.FilePart;
import play.data.*;
import play.db.ebean.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import java.io.File;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;

import views.html.admin.*;

import models.FlightSchedule;
import models.users.User;

@Security.Authenticated(Secured.class)
@With(AuthAdmin.class)
public class AdminController extends Controller {
    private FormFactory formFactory;
    private Environment env;

    @Inject
    public AdminController(FormFactory f, Environment e){
        this.formFactory = f;
        this.env = e;
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
        Date currentDate = new Date();
        for(FlightSchedule f: flightsList){
            if(currentDate.equals(f.getDate())){
                f.delete();
                f.update();
            }
        }
        return ok(adminFlights.render(flightsList, destinationList, getUserFromSession(), env));
    }

    public Result addFlight(){
        Form<FlightSchedule> addFlightForm = formFactory.form(FlightSchedule.class);
        return ok(addFlights.render(addFlightForm, getUserFromSession(), env));
    }

    public Result addDestination(){
        Form<Destination> addDestinationForm = formFactory.form(Destination.class);
        return ok(addDestination.render(addDestinationForm, getUserFromSession(), env));
    }

    @Transactional
    public Result addFlightSubmit(){
        Form<FlightSchedule> newFlightForm = formFactory.form(FlightSchedule.class).bindFromRequest();
        if(newFlightForm.hasErrors()){
            return badRequest(addFlights.render(newFlightForm, getUserFromSession(), env));
        }
        Date currentDate = new Date();
        if(currentDate.after(newFlightForm.get().getDate())){
            return badRequest(addFlights.render(newFlightForm, getUserFromSession(), env));
        }
        FlightSchedule newFlight = newFlightForm.get();
        if(newFlight.getFlight_ID() == null) {
            newFlight.save();
        }
        else if (newFlight.getFlight_ID() != null) {
            newFlight.update();
        }
        String saveImageMsg;
        MultipartFormData data = request().body().asMultipartFormData();
        FilePart image = data.getFile("upload");
        saveImageMsg = saveFile(newFlight.getFlight_ID(), image);

        flash("success", "Flight to " + newFlight.getCity() + " has been created/updated" + saveImageMsg);
        return redirect(controllers.routes.AdminController.flights(0));
    }

    @Transactional
    public Result addDestinationSubmit(){
        Form<Destination> newDestinationForm = formFactory.form(Destination.class).bindFromRequest();
        if(newDestinationForm.hasErrors()){
            return badRequest(addDestination.render(newDestinationForm, getUserFromSession(), env));
        }
        Destination newDestination = newDestinationForm.get();
        if(newDestination.getId()==null){
            newDestination.save();
        }
        return redirect(controllers.routes.AdminController.flights(0));
    }

    public String saveFile(Long id, FilePart<File> image){
        if(image != null){
            String mimeType = image.getContentType();
            if(mimeType.startsWith("image/")){
                File file = image.getFile();
                ConvertCmd cmd = new ConvertCmd();
                IMOperation op = new IMOperation();
                op.addImage(file.getAbsolutePath());
                op.resize(300,200);
                op.addImage("public/images/flightImages/" + id + ".jpg");
                IMOperation thumb = new IMOperation();
                thumb.addImage(file.getAbsolutePath());
                thumb.thumbnail(60);
                thumb.addImage("public/images/flightImages/thumbnails/" + id + ".jpg");
                try{
                    cmd.run(op);
                    cmd.run(thumb);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                return " and image saved";
            }
        }
        return "image file missing";
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
        return ok(addFlights.render(flightForm, getUserFromSession(), env));
    }

}