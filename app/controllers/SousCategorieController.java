package controllers;

import com.google.inject.Inject;
import models.Categorie;
import models.SousCategorie;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import utils.Secured;
import views.html.sous_categorie;
import views.html.sous_categories;
import java.util.ArrayList;
import java.util.List;

@Security.Authenticated(Secured.class)
public class SousCategorieController extends Controller {
    @Inject
    FormFactory formFactory;

    @Transactional
    public Result reads() {
        List<SousCategorie> sousCategorieList = new SousCategorie().findList();
        List<Categorie> categoriesList = new Categorie().findList();

        if(sousCategorieList == null || categoriesList == null) {
            return ok(sous_categories.render(new ArrayList<>(), new ArrayList<>()));
        } else {
            return ok(sous_categories.render(sousCategorieList, categoriesList));
        }

    }

    @Transactional
    public Result read(Long id) {
        SousCategorie SousCategorieExiste = new SousCategorie().findById(id);
        List<Categorie> categoriesList = new Categorie().findList();

        if(SousCategorieExiste == null) {
            return redirect(controllers.routes.SousCategorieController.reads());
        } else {
            if(categoriesList ==null) {
                return ok(sous_categorie.render(SousCategorieExiste, new ArrayList<>()));
            } else {
                return ok(sous_categorie.render(SousCategorieExiste, categoriesList));
            }
        }
    }

    @Transactional
    public Result create() {
        Form<SousCategorie> form = formFactory.form(SousCategorie.class).bindFromRequest();
        if (form.hasErrors()) {
            flash("error", "Veuillez vérifier les données saisies");
        } else {
            SousCategorie sousCategorie = form.get();
            String result = sousCategorie.create(sousCategorie);
            if (result != null) {
                flash("error", "Ce sousCategorie existe déjà. Veuillez saisir un nouveau");
            } else {
                flash("success", "L'sousCategorie été ajouté");
            }
        }
        return redirect(controllers.routes.SousCategorieController.reads());
    }

    @Transactional
    public Result update(Long id) {
        Form<SousCategorie> form = formFactory.form(SousCategorie.class).bindFromRequest();
        if (form.hasErrors()) {
            flash("error", "Veuillez vérifier les données saisies");
        } else {
            SousCategorie sousCategorie = form.get();
            sousCategorie.setId(id);
            String result = sousCategorie.update(sousCategorie);
            if (result != null) {
                flash("error", "Erreur - Veuillez vérifier les données saisie");
            } else {
                flash("success", "Le résultat a été modifié");
            }
        }
        return redirect(controllers.routes.SousCategorieController.read(id));
    }

    @Transactional
    public Result delete(Long id) {
        String result = new SousCategorie().delete(id);
        if (result != null) {
            flash("error", "Erreur de suppression, veuillez réessayer");
        } else {
            flash("success", "L'sousCategorie a été supprimé");
        }
        return redirect(controllers.routes.SousCategorieController.read(id));
    }


}
