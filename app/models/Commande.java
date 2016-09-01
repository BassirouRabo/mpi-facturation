package models;

import play.data.format.Formats;
import play.db.jpa.JPA;
import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "commande")
public class Commande {
    @ManyToOne
    private Produit produit;
    @ManyToOne
    private Facture facture;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "prix")
    private Long prix;
    @Column(name = "quantite")
    private Long quantite;
    @Column(name = "montant")
    private Long montant;

    /**
     * Constructeur avec arguments
     * @param produit
     * @param facture
     * @param prix
     * @param quantite
     * @param montant
     */
    public Commande(Produit produit, Facture facture, Long prix, Long quantite, Long montant) {
        this.produit = produit;
        this.facture = facture;
        this.prix = prix;
        this.quantite = quantite;
        this.montant = montant;
    }

    /**
     * Constructeur sans argument
     */
    public Commande() {
    }

    /**
     * Tous les commande
     *
     * @return
     */
    public List<Commande> findList() {
        try {
            return JPA.em().createQuery("select commande From Commande commande").getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrouver une commande by Id
     *
     * @param id
     * @return
     */
    public Commande findById(Long id) {
        try {
            return (Commande) JPA.em().createQuery("select commande From Commande commande WHERE commande.id = :id").setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * Retrouver une commande by Id
     *
     * @param idFacture
     * @return
     */
    public List<Commande> findListByFacture(Long idFacture) {
        try {
            return JPA.em().createQuery("select commande From Commande commande WHERE commande.facture.id = :idFacture").setParameter("idFacture", idFacture).getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrouver une commande by Id
     *
     * @param idProduit
     * @return
     */
    public List<Commande> findListByProduit(Long idProduit) {
        try {
            return JPA.em().createQuery("select commande From Commande commande WHERE commande.produit.id = :idProduit").setParameter("idProduit", idProduit).getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrouver une commande by Id
     *
     * @param idClient
     * @return
     */
    public List<Commande> findListByClient(Long idClient) {
        try {
            return JPA.em().createQuery("select commande From Commande commande WHERE commande.facture.client.id = :idClient").setParameter("idClient", idClient).getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Créer une commande
     *
     * @param commande
     * @return
     */
    public String create(Commande commande) {
        Produit produitExiste = new Produit().findById(commande.getProduit().getId());

        if (null == produitExiste) {
            return "aucun produit correspondant";
        } else {

            Long prix = produitExiste.getPrix();

            if (null == prix) {
                prix = 0l;
            }

            String result = null;
            try {
                commande.setMontant(0l);
                commande.setPrix(prix);
                commande.setMontant(prix * commande.getQuantite());
                JPA.em().persist(commande);
            } catch (Exception e) {
                result = e.toString();
            }

            return result;
        }

    }

    /**
     * Modifier une commande
     *
     * @param commande
     * @return
     */
    public String update(Commande commande) {
        String result = null;

        Commande commandeExiste = findById(commande.getId());

        if (commandeExiste == null) {
            return "aucun enregistrement correspondant";
        } else {
            commandeExiste.setPrix(commande.getPrix());
            commandeExiste.setQuantite(commande.getQuantite());
            commandeExiste.setMontant(commande.getQuantite() * commande.getPrix());
            try {
                JPA.em().persist(commandeExiste);
            } catch (Exception e) {
                result = e.toString();
            }
            return result;
        }
    }

    /**
     * Supprimer une commande
     *
     * @param id
     * @return
     */
    public String delete(Long id) {
        String result = null;

        Commande commandeExiste = findById(id);

        if (commandeExiste == null) {
            return "aucun enregistrement correspondant";
        } else {
            try {
                JPA.em().remove(commandeExiste);
                JPA.em().flush();
            } catch (Exception e) {
                result = e.toString();
            }
            return result;
        }
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Facture getFacture() {
        return facture;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPrix() {
        return prix;
    }

    public void setPrix(Long prix) {
        this.prix = prix;
    }

    public Long getQuantite() {
        return quantite;
    }

    public void setQuantite(Long quantite) {
        this.quantite = quantite;
    }

    public Long getMontant() {
        return montant;
    }

    public void setMontant(Long montant) {
        this.montant = montant;
    }

}
