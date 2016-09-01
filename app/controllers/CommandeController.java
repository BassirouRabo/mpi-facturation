package controllers;

import models.Commande;
import models.Produit;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.commande;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class CommandeController extends Controller {
    @Inject
    FormFactory formFactory;

    @Transactional
    public Result create(Long idParent) {
        Form<Commande> form = formFactory.form(Commande.class).bindFromRequest();
        if (form.hasErrors()) {
            flash("error", "Veuillez vérifier les données saisies");
        } else {
            Commande commande = form.get();
            String result = commande.create(commande);
            if (result != null) {
                flash("error", "Erreur d'enregistrement");
            } else {
                flash("success", "La commande été ajoutée");
            }
        }
        return redirect(controllers.routes.FactureController.read(idParent));
    }

    @Transactional
    public Result read(Long idParent, Long id) {
        Commande commandeExiste = new Commande().findById(id);

        if(null == commandeExiste) {
            return redirect(controllers.routes.FactureController.read(idParent));
        } else {
            return ok(commande.render(commandeExiste));
        }

    }

    @Transactional
    public Result update(Long idParent, Long id) {
        Form<Commande> form = formFactory.form(Commande.class).bindFromRequest();
        if (form.hasErrors()) {
            flash("error", "Veuillez vérifier les données saisies");
        } else {
            Commande commande = form.get();
            String result = commande.update(commande);
            if (result != null) {
                flash("error", "Erreur - Veuillez vérifier les données saisie");
            } else {
                flash("success", "La commande a été modifiée");
            }
        }
        return redirect(controllers.routes.FactureController.read(idParent));
    }


    @Transactional
    public Result delete(Long idParent, Long id) {
        String result = new Commande().delete(id);
        if (result != null) {
            flash("error", "Erreur de suppression, veuillez réessayer");
        } else {
            flash("success", "La commande a été supprimée");
        }
        return redirect(controllers.routes.FactureController.read(idParent));
    }
}
