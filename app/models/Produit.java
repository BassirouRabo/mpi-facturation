package models;

import play.db.jpa.JPA;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "produit")
public class Produit {
    @ManyToOne
    Categorie categorie;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "reference")
    private String reference;
    @Column(name = "designation")
    private String designation;
    @Column(name = "caracteristique")
    private String caracteristique;
    @Column(name = "prix_vente")
    private Long prixVente;

    public Produit(Categorie categorie, String reference, String designation, String caracteristique, Long prixVente) {
        this.categorie = categorie;
        this.reference = reference;
        this.designation = designation;
        this.caracteristique = caracteristique;
        this.prixVente = prixVente;
    }

    /**
     *
     */
    public Produit() {
    }

    /**
     * Tous les produit
     *
     * @return
     */
    public List<Produit> findList() {
        try {
            return JPA.em().createQuery("select produit From Produit produit").getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrouver une produit by Id
     *
     * @param id
     * @return
     */
    public Produit findById(Long id) {
        try {
            return (Produit) JPA.em().createQuery("select produit From Produit produit WHERE produit.id = :id").setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrouver une produit by reference
     *
     * @param reference
     * @return
     */
    public Produit findByReference(String reference) {
        try {
            return (Produit) JPA.em().createQuery("select produit From Produit produit WHERE produit.reference = :reference").setParameter("reference", reference).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Créer une produit
     *
     * @param produit
     * @return
     */
    public String create(Produit produit) {
        String result = null;
        try {
            JPA.em().persist(produit);
        } catch (Exception e) {
            result = e.toString();
        }
        return result;
    }

    /**
     * Modifier une produit
     *
     * @param produit
     * @return
     */
    public String update(Produit produit) {
        String result = null;

        Produit produitExiste = findById(produit.getId());

        if (produitExiste == null) {
            return "aucun enregistrement correspondant";
        } else {
            produitExiste.setReference(produit.getReference());
            produitExiste.setDesignation(produit.getDesignation());
            produitExiste.setCaracteristique(produit.getCaracteristique());
            produitExiste.setPrixVente(produit.getPrixVente());
            produitExiste.setCategorie(produit.getCategorie());
            try {
                JPA.em().persist(produitExiste);
            } catch (Exception e) {
                result = e.toString();
            }
            return result;
        }
    }

    /**
     * Supprimer une produit
     *
     * @param id
     * @return
     */
    public String delete(Long id) {
        String result = null;

        Produit produitExiste = findById(id);

        if (produitExiste == null) {
            return "aucun enregistrement correspondant";
        } else {
            try {
                JPA.em().remove(produitExiste);
                JPA.em().flush();
            } catch (Exception e) {
                result = e.toString();
            }
            return result;
        }
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getCaracteristique() {
        return caracteristique;
    }

    public void setCaracteristique(String caracteristique) {
        this.caracteristique = caracteristique;
    }

    public Long getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(Long prixVente) {
        this.prixVente = prixVente;
    }
}
