package controllers;

import com.google.inject.Inject;
import models.Produit;
import models.SousCategorie;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import report.PdfPrinter;
import utils.GenerateRandom;
import utils.Secured;
import views.html.produit;
import views.html.produitss;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Security.Authenticated(Secured.class)
public class ProduitController extends Controller {

    @Inject
    FormFactory formFactory;

    @Transactional
    public Result reads() {
        List<Produit> produitList = new Produit().findList();
        List<SousCategorie> sousCategories = new SousCategorie().findList();

        if (produitList == null || sousCategories == null) {
            return ok(produitss.render(new ArrayList<>(), new ArrayList<>()));
        } else {
            return ok(produitss.render(produitList, sousCategories));
        }
    }

    @Transactional
    public Result read(Long id) {
        Produit ProduitExiste = new Produit().findById(id);
        List<SousCategorie> sousCategories = new SousCategorie().findList();

        if (ProduitExiste == null) {
            return redirect(controllers.routes.ProduitController.reads());
        } else {
            if (null == sousCategories) {
                return ok(produit.render(ProduitExiste, new ArrayList<>()));
            } else {
                return ok(produit.render(ProduitExiste, new SousCategorie().findList()));
            }
        }
    }

    @Transactional
    public Result create() throws IOException {
        Form<Produit> form = formFactory.form(Produit.class).bindFromRequest();
        if (form.hasErrors()) {
            flash("error", "Veuillez vérifier les données saisies");
        } else {
            String imageReference = new GenerateRandom().generateRandomImage();

            Produit produit = form.get();
            produit.setReference(new GenerateRandom().generateRandomString());

            Http.MultipartFormData<File> body = request().body().asMultipartFormData();
            Http.MultipartFormData.FilePart<File> image = body.getFile("image");


            if (image != null) {
                File file = image.getFile();

                if (file.length() != 0) {

                    File destination = new File(PdfPrinter.destination_folder, imageReference + ".jpg");
                    Files.move(Paths.get(file.getPath()), Paths.get(destination.getPath()));

                    produit.setImage(imageReference);
                }

            }

            String result = produit.create(produit);

            if (result != null) {
                flash("error", "Ce produit existe déjà. Veuillez saisir un nouveau");
            } else {
                flash("success", "Le produit été ajouté");
            }
        }
        return redirect(controllers.routes.ProduitController.reads());
    }

    @Transactional
    public Result upload() throws IOException {
        Http.MultipartFormData<File> body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart<File> picture = body.getFile("image");
        if (picture != null) {
            File file = picture.getFile();

            File destination = new File("/opt/advinteck/tmp_pdf", "image.jpg");
            Files.move(Paths.get(file.getPath()), Paths.get(destination.getPath()));

            return ok("File uploade");
        } else {
            flash("error", "Missing file");
            return ok("Missing file");
        }
    }

    @Transactional
    public Result update(Long id) {
        Form<Produit> form = formFactory.form(Produit.class).bindFromRequest();
        if (form.hasErrors()) {
            flash("error", "Veuillez vérifier les données saisies");
        } else {
            Produit produit = form.get();
            produit.setId(id);
            String result = produit.update(produit);
            if (result != null) {
                flash("error", "Veuillez vérifier les données saisie");
            } else {
                flash("success", "Le résultat a été modifié");
            }
        }
        return redirect(controllers.routes.ProduitController.read(id));
    }

    @Transactional
    public Result delete(Long id) {
        String result = new Produit().delete(id);
        if (result != null) {
            flash("error", "Erreur de suppression, veuillez réessayer");
        } else {
            flash("success", "L'produit a été supprimé");
        }
        return redirect(controllers.routes.ProduitController.read(id));
    }


}
