package controllers.Security;

import play.*;
import play.mvc.*;
import play.mvc.Http.*;
import controllers.*;

import models.*;
import models.users.*;

public class Secured extends Security.Authenticator {
    public String getUsername(Context ctx) {
        return ctx.session().get("email");
    }

    public Result onUnauthorized(Context ctx) {
        return redirect(routes.LoginController.login());
    }

}