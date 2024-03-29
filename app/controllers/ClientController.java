package controllers;

import com.google.inject.Inject;
import models.Client;
import models.Facture;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import utils.GenerateRandom;
import utils.GetReference;
import utils.Secured;
import views.html.client;
import views.html.clients;

import java.util.ArrayList;
import java.util.List;

@Security.Authenticated(Secured.class)
public class ClientController extends Controller {
    @Inject
    FormFactory formFactory;

    @Transactional
    public Result reads() {
        List<Client> clientList = new Client().findList();

        if (clientList == null) {
            return ok(clients.render(new ArrayList<>()));
        } else {
            return ok(clients.render(clientList));
        }
    }

    @Transactional
    public Result read(Long id) {
        Client clientExiste = new Client().findById(id);

        if (clientExiste == null) {
            return redirect(controllers.routes.ClientController.reads());
        } else {
            List<Facture> facturesProformas = new Facture().findListByFactureProformaByClient(clientExiste.getId());
            List<Facture> bonsLivraisons = new Facture().findListByBonLivraisonByClient(clientExiste.getId());
            List<Facture> facturesDefinitives = new Facture().findListByFactureDefinitiveByClient(clientExiste.getId());

            if(facturesProformas == null) {
                facturesProformas = new ArrayList<>();
            }

            if(bonsLivraisons == null) {
                bonsLivraisons = new ArrayList<>();
            }

            if(facturesDefinitives == null) {
                facturesDefinitives = new ArrayList<>();
            }

            return ok(client.render(clientExiste, facturesProformas, bonsLivraisons, facturesDefinitives, new GetReference()));
        }
    }

    @Transactional
    public Result create() {
        Form<Client> form = formFactory.form(Client.class).bindFromRequest();
        if (form.hasErrors()) {
            flash("error", "Veuillez vérifier les données saisies");
        } else {
            Client client = form.get();
            client.setReference(new GenerateRandom().generateRandomString());
            String result = client.create(client);
            if (result != null) {
                flash("error", "Ce client existe déjà. Veuillez saisir un nouveau");
            } else {
                flash("success", "L'client été ajouté");
            }
        }
        return redirect(controllers.routes.ClientController.reads());
    }

    @Transactional
    public Result update(Long id) {
        Form<Client> form = formFactory.form(Client.class).bindFromRequest();
        if (form.hasErrors()) {
            flash("error", "Veuillez vérifier les données saisies");
        } else {
            Client client = form.get();
            client.setId(id);
            String result = client.update(client);
            if (result != null) {
                flash("error", "Veuillez vérifier les données saisie");
            } else {
                flash("success", "Le résultat a été modifié");
            }
        }
        return redirect(controllers.routes.ClientController.read(id));
    }

    @Transactional
    public Result delete(Long id) {
        String result = new Client().delete(id);
        if (result != null) {
            flash("error", "Erreur de suppression, veuillez réessayer");
        } else {
            flash("success", "L'client a été supprimé");
        }
        return redirect(controllers.routes.ClientController.read(id));
    }


}
