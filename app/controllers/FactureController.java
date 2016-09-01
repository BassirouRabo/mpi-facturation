package controllers;

import com.google.inject.Inject;
import models.*;
import models.Facture;
import org.pentaho.reporting.engine.classic.core.ReportProcessingException;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import report.PdfPrinter;
import utils.GenerateReference;
import utils.GetReference;
import utils.Secured;
import views.html.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Security.Authenticated(Secured.class)
public class FactureController extends Controller {
    @Inject
    FormFactory formFactory;

    @Transactional
    public Result readsFactureProforma() {
        List<Facture> factureList = new Facture().findListByFactureProforma();

        if(null == factureList) {
            factureList = new ArrayList<>();
        }

        return ok(facture_proformas.render(factureList));

    }

    @Transactional
    public Result readsBonLivraison() {
        List<Facture> factureList = new Facture().findListByBonLivraison();

        if(null == factureList) {
            factureList = new ArrayList<>();
        }

        return ok(bon_livraisons.render(factureList));

    }

    @Transactional
    public Result readsFactureDefinitive() {
        List<Facture> factureList = new Facture().findListByFactureDefinitive();

        if(null == factureList) {
            factureList = new ArrayList<>();
        }

        return ok(facture_definitives.render(factureList));

    }

    @Transactional
    public Result read(Long id) {
        Facture facture = new Facture().findById(id);

        if(null == facture) {
            flash("error", "Cette facture n'existe pas");
            return redirect(controllers.routes.FactureController.readsFactureProforma());
        } else {
            List<Commande> commandeList = new Commande().findListByFacture(facture.getId());

            if(null == commandeList) {
                commandeList = new ArrayList<>();
            }

            Double ht = 0.00;
            Double net;
            Double remiseMontant = 0.00;

            for(Commande commande : commandeList) {
                ht = ht + commande.getMontant();

            }

            if(null == facture.getRemise()) {
                net = ht;
            } else {
                remiseMontant = new BigDecimal(ht).doubleValue() * facture.getRemise() / 100;
                net = new BigDecimal(ht).doubleValue() - remiseMontant;
            }

            Double tva = new BigDecimal(net).doubleValue() * 19/100;

            Double ttc = net - tva;

            facture.setHt(ht.longValue());
            facture.setRemiseMontant(remiseMontant.longValue());
            facture.setNet(net.longValue());
            facture.setTva(tva.longValue());
            facture.setTtc(ttc.longValue());


            List<Produit> produitList = new Produit().findList();

            if(null == produitList) {
                produitList = new ArrayList<>();
            }

            List<Client> clientList = new Client().findList();

            if(null == clientList) {
                clientList = new ArrayList<>();
            }



            return ok(facture_proforma.render(facture, commandeList, produitList, clientList));
        }
    }

    @Transactional
    public Result edit(Long id) {
        Facture facture = new Facture().findById(id);

        if(null == facture) {
            flash("error", "Cette facture n'existe pas");
            return redirect(controllers.routes.FactureController.read(id));
        } else {
            List<Client> clientList = new Client().findList();

            if(null == clientList) {
                clientList = new ArrayList<>();
            }

            return ok(facture_proforma_edit.render(facture, clientList));
        }
    }

    @Transactional
    public Result create() {
        Facture facture = new Facture();
        facture.setReferenceFactureProforma(GenerateReference.generateReferenceFactureProforma());

        String result = facture.create(facture);
        if (result != null) {
            flash("error", "Cette facture existe déjà. Veuillez saisir un nouveau");
            return redirect(controllers.routes.FactureController.readsFactureProforma());
        } else {
            flash("success", "La facture été créée");
            return redirect(controllers.routes.FactureController.read(facture.getId()));
        }
    }

    @Transactional
    public Result update(Long id) {
        Form<Facture> form = formFactory.form(Facture.class).bindFromRequest();
        if (form.hasErrors()) {
            flash("error", "Veuillez vérifier les données saisies");
        } else {
            Facture facture = form.get();
            String result = facture.update(facture);
            if (result != null) {
                flash("error", "Veuillez vérifier les données saisie");
            } else {
                flash("success", "La facture a été modifiée");
            }
        }
        return redirect(controllers.routes.FactureController.read(id));
    }

    @Transactional
    public Result delete(Long id) {
        String result = new Facture().delete(id);
        if (result != null) {
            flash("error", "Erreur de suppression, veuillez réessayer");
        } else {
            flash("success", "La facture a été supprimée");
        }
        return redirect(controllers.routes.FactureController.readsFactureProforma());
    }

}
