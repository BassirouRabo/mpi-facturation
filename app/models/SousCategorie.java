package models;

import play.db.jpa.JPA;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sous_categorie")
public class SousCategorie {

    @ManyToOne
    Categorie categorie;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "nom", unique = true, nullable = false)
    private String nom;

    /**
     * Constructeur avec paramètres
     *
     * @param nom
     * @param categorie
     */
    public SousCategorie(String nom, Categorie categorie) {
        this.nom = nom;
        this.categorie = categorie;
    }

    /**
     * Constructeur sans paramètres
     */
    public SousCategorie() {
    }

    /**
     * Toutes les sousCategorie
     *
     * @return
     */
    public List<SousCategorie> findList() {
        try {
            return JPA.em().createQuery("select sousCategorie From SousCategorie sousCategorie").getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrouver une sousCategorie by Id
     *
     * @param id
     * @return
     */
    public SousCategorie findById(Long id) {
        try {
            return (SousCategorie) JPA.em().createQuery("select sousCategorie From SousCategorie sousCategorie WHERE sousCategorie.id = :id").setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrouver une sousCategorie by Nom
     *
     * @param nom
     * @return
     */
    public SousCategorie findByNom(String nom) {
        try {
            return (SousCategorie) JPA.em().createQuery("select sousCategorie From SousCategorie sousCategorie WHERE sousCategorie.nom = :nom").setParameter("nom", nom).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * List des sous categorie d'une categorie
     *
     * @param idCategorie
     * @return
     */
    public List<SousCategorie> findListByCategorie(Long idCategorie) {
        try {
            return JPA.em().createQuery("select sousCategorie From SousCategorie sousCategorie WHERE categorie.id = :idCategorie").setParameter("idCategorie", idCategorie).getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Créer une sousCategorie
     *
     * @param sousCategorie
     * @return
     */
    public String create(SousCategorie sousCategorie) {
        SousCategorie sousCategorieExiste = findByNom(sousCategorie.getNom());

        if (sousCategorieExiste != null) {
            return "Cette sousCategorie existe";
        } else {
            String result = null;
            try {
                JPA.em().persist(sousCategorie);
            } catch (Exception e) {
                result = e.toString();
            }
            return result;
        }

    }

    /**
     * Modifier une sousCategorie
     *
     * @param sousCategorie
     * @return
     */
    public String update(SousCategorie sousCategorie) {
        String result = null;

        SousCategorie sousCategorieExiste = findById(sousCategorie.getId());

        if (sousCategorieExiste == null) {
            return "aucun enregistrement correspondant";
        } else {
            sousCategorieExiste.setNom(sousCategorie.getNom());
            sousCategorieExiste.setCategorie(sousCategorie.getCategorie());
            try {
                JPA.em().persist(sousCategorieExiste);
            } catch (Exception e) {
                result = e.toString();
            }
            return result;
        }
    }

    /**
     * Supprimer une sousCategorie
     *
     * @param id
     * @return
     */
    public String delete(Long id) {
        String result = null;

        SousCategorie sousCategorieExiste = findById(id);

        if (sousCategorieExiste == null) {
            return "aucun enregistrement correspondant";
        } else {
            try {
                JPA.em().remove(sousCategorieExiste);
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

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
}
