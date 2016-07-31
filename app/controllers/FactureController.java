package controllers;

import play.mvc.Controller;
import play.mvc.Security;
import utils.Secured;

@Security.Authenticated(Secured.class)
public class FactureController extends Controller {
}
