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
import utils.GenerateReference;
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
    public Result readsFirstBonLivraison() {
        List<Facture> factureList = new Facture().findListFirstByBonLivraison();

        if (factureList == null) {
            return ok(bon_livraisonss.render(new ArrayList<>()));
        } else {
            return ok(bon_livraisonss.render(factureList));
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
    public Result readsFirstFactureProformaByClient(String referenceClient) {
        List<Facture> factureList = new Facture().findListFirstByFactureProformaByClient(referenceClient);

        if (factureList == null) {
            return ok(facture_proformass.render(new ArrayList<>()));
        } else {
            return ok(facture_proformass.render(factureList));
        }
    }

    @Transactional
    public Result readsFirstBonLivraisonByClient(String referenceClient) {
        List<Facture> factureList = new Facture().findListFirstByBonLivraisonByClient(referenceClient);

        if (factureList == null) {
            return ok(bon_livraisonss.render(new ArrayList<>()));
        } else {
            return ok(bon_livraisonss.render(factureList));
        }
    }

    @Transactional
    public Result readsFirstFactureDefinitiveByClient(String referenceClient) {
        List<Facture> factureList = new Facture().findListFirstByFactureDefinitiveByClient(referenceClient);

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
    public Result readsBonLivraison(String referenceBonLivraison) {
        List<Facture> factureList = new Facture().findListByReferenceBonLivraison(referenceBonLivraison);

        if (factureList == null) {
            return ok(bon_livraisons.render(new ArrayList<>()));
        } else {
            return ok(bon_livraisons.render(factureList));
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
        String referenceFactureProforma = GenerateReference.generateReferenceFactureProforma();

        Facture facture = new Facture();

        facture.setReferenceFactureProforma(referenceFactureProforma);
        facture.setReferenceBonLivraison("0");
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
    public Result createBonLivraison(String referenceFactureProforma) {
        String referenceBonLivraison = GenerateReference.generateReferenceBonLivraison(referenceFactureProforma);

        String  result = new Facture().createBonLivraison(referenceFactureProforma, referenceBonLivraison);

        if (result != null) {
            flash("error", "Erreur de création de bon de livraison, veuillez réessayer");
        } else {
            flash("success", "Le bon de livraison a été créé");
        }
        return redirect(controllers.routes.FactureController.readsFactureProforma(referenceFactureProforma));
    }

    @Transactional
    public Result createFactureDefinitive(String referenceFactureProforma) {
        String referenceFactureDefinitive = GenerateReference.generateReferenceFactureDefinitive(referenceFactureProforma);

        String  result = new Facture().createFactureDefinitive(referenceFactureProforma, referenceFactureDefinitive);

        if (result != null) {
            flash("error", "Erreur de création de la facture définitive, veuillez réessayer");
        } else {
            flash("success", "La facture définitive a été créée");
        }
        return redirect(controllers.routes.FactureController.readsFactureProforma(referenceFactureProforma));
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
            String result = facture.addCommande(referenceFactureProforma, facture.getReferenceProduit(), facture.getPrixVente(), facture.getQuantite(), session("telephone"));
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
