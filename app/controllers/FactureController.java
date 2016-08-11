package controllers;

import com.google.inject.Inject;
import models.Client;
import models.Facture;
import models.Produit;
import models.Rapport;
import org.pentaho.reporting.engine.classic.core.ReportProcessingException;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import report.PdfPrinter;
import utils.FactureTotalPojo;
import utils.GenerateReference;
import utils.GetReference;
import utils.Secured;
import views.html.*;

import java.io.IOException;
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
            return ok(facture_proformass.render(new ArrayList<>(), new GetReference()));
        } else {
            return ok(facture_proformass.render(factureList, new GetReference()));
        }
    }

    @Transactional
    public Result readsFirstBonLivraison() {
        List<Facture> factureList = new Facture().findListFirstByBonLivraison();

        if (factureList == null) {
            return ok(bon_livraisonss.render(new ArrayList<>(), new GetReference()));
        } else {
            return ok(bon_livraisonss.render(factureList, new GetReference()));
        }
    }

    @Transactional
    public Result readsFirstFactureDefinitive() {
        List<Facture> factureList = new Facture().findListFirstByFactureDefinitive();

        if (factureList == null) {
            return ok(facture_definitivess.render(new ArrayList<>(), new GetReference()));
        } else {
            return ok(facture_definitivess.render(factureList, new GetReference()));
        }
    }

    @Transactional
    public Result readsFirstFactureProformaByClient(String referenceClient) {
        List<Facture> factureList = new Facture().findListFirstByFactureProformaByClient(referenceClient);

        if (factureList == null) {
            return ok(facture_proformass.render(new ArrayList<>(), new GetReference()));
        } else {
            return ok(facture_proformass.render(factureList, new GetReference()));
        }
    }

    @Transactional
    public Result readsFirstBonLivraisonByClient(String referenceClient) {
        List<Facture> factureList = new Facture().findListFirstByBonLivraisonByClient(referenceClient);

        if (factureList == null) {
            return ok(bon_livraisonss.render(new ArrayList<>(), new GetReference()));
        } else {
            return ok(bon_livraisonss.render(factureList, new GetReference()));
        }
    }

    @Transactional
    public Result readsFirstFactureDefinitiveByClient(String referenceClient) {
        List<Facture> factureList = new Facture().findListFirstByFactureDefinitiveByClient(referenceClient);

        if (factureList == null) {
            return ok(facture_definitivess.render(new ArrayList<>(), new GetReference()));
        } else {
            return ok(facture_definitivess.render(factureList, new GetReference()));
        }
    }

    @Transactional
    public Result readsFactureProforma(String referenceFactureProforma) {
        List<Facture> factureList = new Facture().findListByReferenceFactureProforma(referenceFactureProforma);
        List<Client> clientList = new Client().findList();
        List<Produit> produits = new Produit().findList();

        FactureTotalPojo factureTotalPojo = new FactureTotalPojo().calcul(referenceFactureProforma);

        if(null == factureList) {
            factureList = new ArrayList<>();
        }
        if(null == clientList) {
            clientList = new ArrayList<>();
        }
        if(null == produits) {
            produits = new ArrayList<>();
        }

        return ok(facture_proformas.render(factureList, clientList, produits, factureTotalPojo, new GetReference()));
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
    public Result readFactureProforma(String referenceFactureProforma) {
        Facture facture = new Facture().findFirstByReferenceFactureProforma(referenceFactureProforma);

        if (null == facture) {
            return redirect(controllers.routes.FactureController.readsFactureProforma(referenceFactureProforma));
        } else {
            List<Client> clients = new Client().findList();

            if (null == clients) {
                return ok(facture_proforma.render(facture, new ArrayList<>()));
            } else {
                return ok(facture_proforma.render(facture, clients));
            }
        }
    }

    @Transactional
    public Result initialisation() {
        String referenceFactureProforma = GenerateReference.generateReferenceFactureProforma();

        Facture facture = new Facture();

        facture.setReferenceFactureProforma(referenceFactureProforma);
        facture.setReferenceFactureProformaImpression("0");
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
    public Result createFactureProformaImpression(String referenceFactureProforma) throws IOException, ReportProcessingException {

        String result = new Facture().createFactureProformaImpression(referenceFactureProforma);

            if (result != null) {
                flash("error", "Erreur de création de la facture proforma, veuillez réessayer");
            } else {
                Rapport rapport = new Rapport().findByCodeRapport("FP");
                PdfPrinter.printer("FP", referenceFactureProforma, rapport);

                flash("success", "La facture proforma a été créée");
            }
            return redirect(controllers.routes.FactureController.readsFactureProforma(referenceFactureProforma));
    }

    @Transactional
    public Result createBonLivraison(String referenceFactureProforma) throws IOException, ReportProcessingException {
        String referenceBonLivraison = GenerateReference.generateReferenceBonLivraison();

        String  result = new Facture().createBonLivraison(referenceFactureProforma, referenceBonLivraison);

        if (result != null) {
            flash("error", "Erreur de création de bon de livraison, veuillez réessayer");
        } else {
            Rapport rapport = new Rapport().findByCodeRapport("BL");
            PdfPrinter.printer("BL", referenceBonLivraison, rapport);

            flash("success", "Le bon de livraison a été créé");
        }
        return redirect(controllers.routes.FactureController.readsFactureProforma(referenceFactureProforma));
    }

    @Transactional
    public Result createFactureDefinitive(String referenceFactureProforma) throws IOException, ReportProcessingException {
        String referenceFactureDefinitive = GenerateReference.generateReferenceFactureDefinitive();

        String  result = new Facture().createFactureDefinitive(referenceFactureProforma, referenceFactureDefinitive);

        if (result != null) {
            flash("error", "Erreur de création de la facture définitive, veuillez réessayer");
        } else {
            Rapport rapport = new Rapport().findByCodeRapport("FD");
            PdfPrinter.printer("FD", referenceFactureDefinitive, rapport);

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
                flash("success", "La facture a été modifiéz");
            }
        }
        return redirect(controllers.routes.FactureController.readsFactureProforma(referenceFactureProforma));
    }

    @Transactional
    public Result updateEntete2(String referenceFactureProforma) {
        Form<Facture> form = formFactory.form(Facture.class).bindFromRequest();
        if (form.hasErrors()) {
            flash("error", "Veuillez vérifier les données saisies");
        } else {
            Facture facture = form.get();
            String result = facture.updateEntete(facture);
            if (result != null) {
                flash("error", "Erreur de mise à jour");
            } else {
                flash("success", "La facture a été modifiéz");
                return redirect(controllers.routes.FactureController.readsFactureProforma(referenceFactureProforma));
            }
        }
        return redirect(controllers.routes.FactureController.readFactureProforma(referenceFactureProforma));
    }


    @Transactional
    public Result delete(String referenceFactureProforma, Long id) {
        String result = new Facture().delete(id);
        if (result != null) {
            flash("error", "Erreur de suppression, veuillez réessayer");
        } else {
            flash("success", "La commande a été supprimée");
        }
        return redirect(controllers.routes.FactureController.readsFactureProforma(referenceFactureProforma));
    }

    @Transactional
    public Result deleteFacture(String referenceFactureProforma) {
        String result = new Facture().deleteFacture(referenceFactureProforma);
        if (result != null) {
            flash("error", "Erreur de suppression de la facture, veuillez réessayer");
        } else {
            flash("success", "La facture a été supprimée");
        }
        return redirect(controllers.routes.FactureController.readsFirstFactureProforma());
    }

    @Transactional
    public Result editProduit(String referenceFactureProforma, Long id) {
        Facture facture = new Facture().findById(id);

        if(null == facture) {
            flash("error", "Ce produit n'existe pas dans la facture");
            return redirect(controllers.routes.FactureController.readsFactureProforma(referenceFactureProforma));
        }else {
            return ok(facture_proforma_produit.render(facture));
        }
    }

    @Transactional
    public Result updateProduit(String referenceFactureProforma, Long id) {
        Form<Facture> form = formFactory.form(Facture.class).bindFromRequest();
        if (form.hasErrors()) {
            flash("error", "Veuillez vérifier les données saisies");
            return redirect(controllers.routes.FactureController.editProduit(referenceFactureProforma, id));
        } else {
            Facture facture = form.get();
            String result = facture.updateProduit(facture);
            if (result != null) {
                flash("error", "Erreur de mise à jour");
                return redirect(controllers.routes.FactureController.editProduit(referenceFactureProforma, id));
            } else {
                flash("success", "Le produit a été modifié");
                return redirect(controllers.routes.FactureController.readsFactureProforma(referenceFactureProforma));
            }
        }
    }

}
