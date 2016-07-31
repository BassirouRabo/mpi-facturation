package controllers;

import com.google.inject.Inject;
import models.Utilisateur;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.connexion;
import views.html.inscription;
import views.html.utilisateur;
import views.html.utilisateurs;


public class UtilisateurController extends Controller {
    @Inject
    FormFactory formFactory;

    public Result connexion() {
        return ok(connexion.render());
    }

    public Result inscription() {
        return ok(inscription.render());
    }

    @Transactional
    public Result authentification() {
        Form<Utilisateur> form = formFactory.form(Utilisateur.class).bindFromRequest();
        if (form.hasErrors()) {
            flash("error", " Error de saisie");
            return redirect(controllers.routes.UtilisateurController.connexion());
        } else {
            Utilisateur login = form.get();
            Utilisateur utilisateur = new Utilisateur().findByTelephoneAndPassword(login.getTelephone(), login.getPassword());

            if (utilisateur == null) {
                flash("error", " Email ou mot de passe incorrect. Veuillez saisir à nouveau");
                return redirect(controllers.routes.UtilisateurController.connexion());
            } else {
                session("email", (utilisateur.getEmail()));
                return redirect(controllers.routes.HomeController.index());
            }
        }
    }

    @Transactional
    public Result reads() {
        return ok(utilisateurs.render(new Utilisateur().findList()));
    }

    @Transactional
    public Result read(Long id) {
        Utilisateur UtilisateurExiste = new Utilisateur().findById(id);
        if (UtilisateurExiste == null) {
            return redirect(controllers.routes.UtilisateurController.reads());
        } else {
            return ok(utilisateur.render(UtilisateurExiste));
        }
    }

    @Transactional
    public Result create() {
        Form<Utilisateur> form = formFactory.form(Utilisateur.class).bindFromRequest();
        if (form.hasErrors()) {
            flash("error", "Veuillez vérifier les données saisies");
        } else {
            Utilisateur utilisateur = form.get();
            String result = utilisateur.create(utilisateur);
            if (result != null) {
                flash("error", "Ce utilisateur existe déjà. Veuillez saisir un nouveau");
            } else {
                flash("success", "L'utilisateur été ajouté");
            }
        }
        return redirect(controllers.routes.UtilisateurController.reads());
    }

    @Transactional
    public Result update(Long id) {
        Form<Utilisateur> form = formFactory.form(Utilisateur.class).bindFromRequest();
        if (form.hasErrors()) {
            flash("error", "Veuillez vérifier les données saisies");
        } else {
            Utilisateur utilisateur = form.get();
            String result = utilisateur.update(utilisateur);
            if (result != null) {
                flash("error", "Veuillez vérifier les données saisie");
            } else {
                flash("success", "Le résultat a été modifié");
            }
        }
        return redirect(controllers.routes.UtilisateurController.read(id));
    }

    @Transactional
    public Result delete(Long id) {
        String result = new Utilisateur().delete(id);
        if (result != null) {
            flash("error", "Erreur de suppression, veuillez réessayer");
        } else {
            flash("success", "L'utilisateur a été supprimé");
        }
        return redirect(controllers.routes.UtilisateurController.read(id));
    }
    
    
}
