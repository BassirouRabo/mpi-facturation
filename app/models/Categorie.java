package models;

import play.db.jpa.JPA;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categorie")
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "nom", unique = true, nullable = false)
    private String nom;

    /**
     * Constructeur avec paramètres
     *
     * @param nom
     */
    public Categorie(String nom) {
        this.nom = nom;
    }

    /**
     * Constructeur sans paramètres
     */
    public Categorie() {
    }

    /**
     * Toutes les categorie
     *
     * @return
     */
    public List<Categorie> findList() {
        try {
            return JPA.em().createQuery("select categorie From Categorie categorie").getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrouver une categorie by Id
     *
     * @param id
     * @return
     */
    public Categorie findById(Long id) {
        try {
            return (Categorie) JPA.em().createQuery("select categorie From Categorie categorie WHERE categorie.id = :id").setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrouver une categorie by Nom
     *
     * @param nom
     * @return
     */
    public Categorie findByNom(String nom) {
        try {
            return (Categorie) JPA.em().createQuery("select categorie From Categorie categorie WHERE categorie.nom = :nom").setParameter("nom", nom).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Créer une categorie
     *
     * @param categorie
     * @return
     */
    public String create(Categorie categorie) {
        Categorie categorieExiste = findByNom(categorie.getNom());

        if (categorieExiste != null) {
            return "Cette categorie existe";
        } else {
            String result = null;
            try {
                JPA.em().persist(categorie);
            } catch (Exception e) {
                result = e.toString();
            }
            return result;
        }

    }

    /**
     * Modifier une categorie
     *
     * @param categorie
     * @return
     */
    public String update(Categorie categorie) {
        String result = null;

        Categorie categorieExiste = findById(categorie.getId());

        if (categorieExiste == null) {
            return "aucun enregistrement correspondant";
        } else {
            categorieExiste.setNom(categorie.getNom());
            try {
                JPA.em().persist(categorieExiste);
            } catch (Exception e) {
                result = e.toString();
            }
            return result;
        }
    }

    /**
     * Supprimer une categorie
     *
     * @param id
     * @return
     */
    public String delete(Long id) {
        String result = null;

        Categorie categorieExiste = findById(id);

        if (categorieExiste == null) {
            return "aucun enregistrement correspondant";
        } else {
            try {
                JPA.em().remove(categorieExiste);
                JPA.em().flush();
            } catch (Exception e) {
                result = e.toString();
            }
            return result;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
