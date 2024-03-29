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
import views.html.categorie;
import views.html.categories;

import java.util.ArrayList;
import java.util.List;

@Security.Authenticated(Secured.class)
public class CategorieController extends Controller {
    @Inject
    FormFactory formFactory;

    @Transactional
    public Result reads() {
        List<Categorie> categorieList = new Categorie().findList();

        if(categorieList == null) {
            return ok(categories.render(new ArrayList<>()));
        } else {
            return ok(categories.render(categorieList));
        }

    }

    @Transactional
    public Result read(Long id) {
        Categorie CategorieExiste = new Categorie().findById(id);
        List<SousCategorie> sousCategorieList = new SousCategorie().findListByCategorie(id);

        if(CategorieExiste == null) {
            return redirect(controllers.routes.CategorieController.reads());
        } else {
            if(null == sousCategorieList) {
                return ok(categorie.render(CategorieExiste, new ArrayList<>()));
            } else {
                return ok(categorie.render(CategorieExiste, sousCategorieList));
            }
        }
    }

    @Transactional
    public Result create() {
        Form<Categorie> form = formFactory.form(Categorie.class).bindFromRequest();
        if (form.hasErrors()) {
            flash("error", "Veuillez vérifier les données saisies");
        } else {
            Categorie categorie = form.get();
            String result = categorie.create(categorie);
            if (result != null) {
                flash("error", "Ce categorie existe déjà. Veuillez saisir un nouveau");
            } else {
                flash("success", "L'categorie été ajouté");
            }
        }
        return redirect(controllers.routes.CategorieController.reads());
    }

    @Transactional
    public Result update(Long id) {
        Form<Categorie> form = formFactory.form(Categorie.class).bindFromRequest();
        if (form.hasErrors()) {
            flash("error", "Veuillez vérifier les données saisies");
        } else {
            Categorie categorie = form.get();
            categorie.setId(id);
            String result = categorie.update(categorie);
            if (result != null) {
                flash("error", "Erreur - Veuillez vérifier les données saisie");
            } else {
                flash("success", "Le résultat a été modifié");
            }
        }
        return redirect(controllers.routes.CategorieController.read(id));
    }

    @Transactional
    public Result delete(Long id) {
        String result = new Categorie().delete(id);
        if (result != null) {
            flash("error", "Erreur de suppression, veuillez réessayer");
        } else {
            flash("success", "L'categorie a été supprimé");
        }
        return redirect(controllers.routes.CategorieController.read(id));
    }


}
