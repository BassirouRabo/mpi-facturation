package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.login;


public class Utilisateurs extends Controller {

    public Result login() {
        return ok(login.render());
    }
}
