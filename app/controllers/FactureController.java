package controllers;

import com.google.inject.Inject;
import models.Client;
import models.Facture;
import models.Produit;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import utils.GenerateRandom;
import utils.Secured;
import views.html.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Security.Authenticated(Secured.class)
public class FactureController extends Controller {
    @Inject
    FormFactory formFactory;

    @Transactional
    public Result readsFirstFactureProforma() {
        List<Facture> factureList = new Facture().findListFirstByFactureProforma();

        if (factureList == null) {
            return ok(facture_proformass.render(new ArrayList<>()));
        } else {
            return ok(facture_proformass.render(factureList));
        }
    }

    @Transactional
    public Result readsFirstBonCommande() {
        List<Facture> factureList = new Facture().findListFirstByBonCommande();

        if (factureList == null) {
            return ok(bon_commandess.render(new ArrayList<>()));
        } else {
            return ok(bon_commandess.render(factureList));
        }
    }

    @Transactional
    public Result readsFirstFactureDefinitive() {
        List<Facture> factureList = new Facture().findListFirstByFactureDefinitive();

        if (factureList == null) {
            return ok(facture_definitivess.render(new ArrayList<>()));
        } else {
            return ok(facture_definitivess.render(factureList));
        }
    }

    @Transactional
    public Result readsFactureProforma(String referenceFactureProforma) {
        List<Facture> factureList = new Facture().findListByReferenceFactureProforma(referenceFactureProforma);
        List<Client> clientList = new Client().findList();
        List<Produit> produits = new Produit().findList();

        if (factureList == null || clientList == null || produits == null) {
            return ok(facture_proformas.render(new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        } else {
            return ok(facture_proformas.render(factureList, clientList, produits));
        }
    }

    @Transactional
    public Result readsBonCommande(String referenceBonCommande) {
        List<Facture> factureList = new Facture().findListByReferenceBonCommande(referenceBonCommande);

        if (factureList == null) {
            return ok(bon_commandes.render(new ArrayList<>()));
        } else {
            return ok(bon_commandes.render(factureList));
        }
    }

    @Transactional
    public Result readsFactureDefinitive(String referenceFactureDefinitive) {
        List<Facture> factureList = new Facture().findListByReferenceFactureDefinitive(referenceFactureDefinitive);

        if (factureList == null) {
            return ok(facture_definitives.render(new ArrayList<>()));
        } else {
            return ok(facture_definitives.render(factureList));
        }
    }

    @Transactional
    public Result initialisation() {
        String referenceFactureProforma = new GenerateRandom().generateRandomString();

        Facture facture = new Facture();

        facture.setReferenceFactureProforma(referenceFactureProforma);
        facture.setReferenceBonCommande("0");
        facture.setReferenceFactureDefinitive("0");
        facture.setWhenDone(new Date());
        facture.setWhoDone(session("telephone"));

        String result = facture.create(facture);
        if (result != null) {
            flash("error", "Erreur lors de la création de la facture proforma, veuillez réessayer à nouveau");
            return redirect(controllers.routes.FactureController.readsFirstFactureProforma());
        } else {
            return redirect(controllers.routes.FactureController.readsFactureProforma(referenceFactureProforma));
        }
    }

    @Transactional
    public Result updateEntete(String referenceFactureProforma) {
        Form<Facture> form = formFactory.form(Facture.class).bindFromRequest();
        if (form.hasErrors()) {
            flash("error", "Veuillez vérifier les données saisies");
        } else {
            Facture facture = form.get();
            facture.setReferenceFactureProforma(referenceFactureProforma);
            String result = facture.updateEntete(facture);
            if (result != null) {
                flash("error", "Erreur de mise à jour");
            } else {
                flash("success", "Le résultat a été modifié");
            }
        }
        return redirect(controllers.routes.FactureController.readsFactureProforma(referenceFactureProforma));
    }

    @Transactional
    public Result create(String referenceFactureProforma) {
        Form<Facture> form = formFactory.form(Facture.class).bindFromRequest();
        if (form.hasErrors()) {
            flash("error", "Veuillez vérifier les données saisies");
        } else {
            Facture facture = form.get();
            facture.setReferenceFactureProforma(referenceFactureProforma);
            String result = facture.addCommande(referenceFactureProforma, facture.getReferenceProduit(), facture.getQuantite(), session("telephone"));
            if (result != null) {
                flash("error", "Erreur d'ajout de la commande");
            } else {
                flash("success", "Le produit a été ajouté");
            }
        }
        return redirect(controllers.routes.FactureController.readsFactureProforma(referenceFactureProforma));
    }

    @Transactional
    public Result delete(String referenceFactureProforma, Long id) {
        String result = new Facture().delete(id);
        if (result != null) {
            flash("error", "Erreur de suppression, veuillez réessayer");
        } else {
            flash("success", "Le produit a été supprimé");
        }
        return redirect(controllers.routes.FactureController.readsFactureProforma(referenceFactureProforma));
    }

}
