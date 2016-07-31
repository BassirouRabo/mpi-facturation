package controllers;

import com.google.inject.Inject;
import models.Categorie;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import utils.Secured;
import views.html.categorie;
import views.html.categories;

@Security.Authenticated(Secured.class)
public class CategorieController extends Controller {
    @Inject
    FormFactory formFactory;

    @Transactional
    public Result reads() {
        return ok(categories.render(new Categorie().findList()));
    }

    @Transactional
    public Result read(Long id) {
        Categorie CategorieExiste = new Categorie().findById(id);
        if(CategorieExiste == null) {
            return redirect(controllers.routes.CategorieController.reads());
        } else {
            return ok(categorie.render(CategorieExiste));
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
