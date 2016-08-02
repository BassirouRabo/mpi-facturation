package controllers;

import models.Rapport;
import org.pentaho.reporting.engine.classic.core.ReportProcessingException;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import report.PdfPrinter;

import java.io.IOException;

public class RapportController extends Controller {

    @Transactional
    public Result imprimer() throws IOException, ReportProcessingException {


        Rapport rapport = new Rapport().findByCodeRapport("");

        PdfPrinter.printer("10", rapport);

        return TODO;
    }
}
