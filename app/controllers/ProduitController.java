package controllers;

import com.google.inject.Inject;
import models.Produit;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.produit;
import views.html.produits;


public class ProduitController extends Controller {
    @Inject
    FormFactory formFactory;

    @Transactional
    public Result reads() {
        return ok(produits.render(new Produit().findList()));
    }

    @Transactional
    public Result read(Long id) {
        Produit ProduitExiste = new Produit().findById(id);
        if (ProduitExiste == null) {
            return redirect(controllers.routes.ProduitController.reads());
        } else {
            return ok(produit.render(ProduitExiste));
        }
    }

    @Transactional
    public Result create() {
        Form<Produit> form = formFactory.form(Produit.class).bindFromRequest();
        if (form.hasErrors()) {
            flash("error", "Veuillez vérifier les données saisies");
        } else {
            Produit produit = form.get();
            String result = produit.create(produit);
            if (result != null) {
                flash("error", "Ce produit existe déjà. Veuillez saisir un nouveau");
            } else {
                flash("success", "L'produit été ajouté");
            }
        }
        return redirect(controllers.routes.ProduitController.reads());
    }

    @Transactional
    public Result update(Long id) {
        Form<Produit> form = formFactory.form(Produit.class).bindFromRequest();
        if (form.hasErrors()) {
            flash("error", "Veuillez vérifier les données saisies");
        } else {
            Produit produit = form.get();
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
