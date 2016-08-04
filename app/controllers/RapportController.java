package controllers;

import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;


public class RapportController extends Controller {

    @Transactional
    public Result download(String codeRapport, String reference) {
        return ok(new java.io.File("/opt/advinteck/tmp_pdf/" + reference + ".pdf"));
    }
}
