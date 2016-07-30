package models;

import play.db.jpa.JPA;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "facture")
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "reference_facture_proforma", nullable = false)
    private String referenceFactureProforma;
    @Column(name = "reference_bon_commande")
    private String referenceBonCommande;
    @Column(name = "reference_facture_definitive")
    private String referenceFactureDefinitive;

    // Client
    @Column(name = "reference_client")
    private String referenceClient;
    @Column(name = "nom")
    private String nom;
    @Column(name = "telephone")
    private Long telephone;
    @Column(name = "email")
    private String email;
    @Column(name = "adresse")
    private String adresse;
    @Column(name = "information")
    private String information;

    // Produit
    @Column(name = "reference_produit")
    private String referenceProduit;
    @Column(name = "categorie")
    private String categorie;
    @Column(name = "designation")
    private String designation;
    @Column(name = "caracteristique")
    private String caracteristique;
    @Column(name = "prix_vente")
    private Long prixVente;

    // Add
    @Column(name = "quantite")
    private Long quantite;
    @Column(name = "delai_livraison")
    private String delaiLivraison;
    @Column(name = "garantie")
    private String garantie;
    @Column(name = "mode_paiement")
    private String modePaiement;
    @Column(name = "validite")
    private String validite;
    @Column(name = "remise")
    private Long remise;

    @Column(name = "when_done")
    private Date whenDone;
    @Column(name = "who_done")
    private String whoDone;

    public Facture(String referenceFactureProforma, String referenceBonCommande, String referenceFactureDefinitive, String referenceClient, String nom, Long telephone, String email, String adresse, String information, String referenceProduit, String categorie, String designation, String caracteristique, Long prixVente, Long quantite, String delaiLivraison, String garantie, String modePaiement, String validite, Long remise, Date whenDone, String whoDone) {
        this.referenceFactureProforma = referenceFactureProforma;
        this.referenceBonCommande = referenceBonCommande;
        this.referenceFactureDefinitive = referenceFactureDefinitive;
        this.referenceClient = referenceClient;
        this.nom = nom;
        this.telephone = telephone;
        this.email = email;
        this.adresse = adresse;
        this.information = information;
        this.referenceProduit = referenceProduit;
        this.categorie = categorie;
        this.designation = designation;
        this.caracteristique = caracteristique;
        this.prixVente = prixVente;
        this.quantite = quantite;
        this.delaiLivraison = delaiLivraison;
        this.garantie = garantie;
        this.modePaiement = modePaiement;
        this.validite = validite;
        this.remise = remise;
        this.whenDone = whenDone;
        this.whoDone = whoDone;
    }

    /**
     * Constructeur sans paramètres
     */
    public Facture() {
    }

    /**
     * Toutes les facture
     *
     * @return
     */
    public List<Facture> findList() {
        try {
            return JPA.em().createQuery("select facture From Facture facture").getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrouver une facture by Id
     *
     * @param id
     * @return
     */
    public Facture findById(Long id) {
        try {
            return (Facture) JPA.em().createQuery("select facture From Facture facture WHERE facture.id = :id").setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrouver une facture by referenceFactureProforma
     *
     * @param referenceFactureProforma
     * @return
     */
    public List<Facture> findByReferenceFactureProforma(String referenceFactureProforma) {
        try {
            return JPA.em().createQuery("select facture From Facture facture WHERE facture.referenceFactureProforma = :referenceFactureProforma").setParameter("referenceFactureProforma", referenceFactureProforma).getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrouver une facture by referenceBonCommande
     *
     * @param referenceBonCommande
     * @return
     */
    public List<Facture> findByReferenceBonCommande(String referenceBonCommande) {
        try {
            return JPA.em().createQuery("select facture From Facture facture WHERE facture.referenceBonCommande = :referenceBonCommande").setParameter("referenceBonCommande", referenceBonCommande).getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrouver une facture by referenceFactureDefinitive
     *
     * @param referenceFactureDefinitive
     * @return
     */
    public List<Facture> findByReferenceFactureDefinitive(String referenceFactureDefinitive) {
        try {
            return JPA.em().createQuery("select facture From Facture facture WHERE facture.referenceFactureDefinitive = :referenceFactureDefinitive").setParameter("referenceFactureDefinitive", referenceFactureDefinitive).getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Créer une facture
     *
     * @param facture
     * @return
     */
    public String create(Facture facture) {
        String result = null;
        try {
            JPA.em().persist(facture);
        } catch (Exception e) {
            result = e.toString();
        }
        return result;
    }

    /**
     * @param referenceFactureProforma
     * @return
     */
    public String createBonCommande(String referenceFactureProforma) {
        String result;

        List<Facture> factures = findByReferenceFactureProforma(referenceFactureProforma);
        for (Facture facture : factures) {
            facture.setReferenceBonCommande("BL001");
            result = facture.update(facture);

            if (result != null) {
                return result;
            }
        }
        return null;
    }

    /**
     * @param referenceFactureProforma
     * @return
     */
    public String createFactureDefinitive(String referenceFactureProforma) {
        String result;

        List<Facture> factures = findByReferenceFactureProforma(referenceFactureProforma);
        for (Facture facture : factures) {
            facture.setReferenceFactureDefinitive("FD001");
            result = facture.update(facture);

            if (result != null) {
                return result;
            }
        }
        return null;
    }

    /**
     * @param referenceFactureProforma
     * @return
     */
    public String modification(String referenceFactureProforma) {
        String result;

        List<Facture> factures = findByReferenceFactureProforma(referenceFactureProforma);
        for (Facture facture : factures) {
            result = facture.update(facture);

            if (result != null) {
                return result;
            }
        }
        return null;
    }

    /**
     * @param facture
     * @return
     */
    private String update(Facture facture) {
        String result = null;

        Facture factureExiste = findById(facture.getId());
        if (factureExiste == null) {
            return "aucun enregistrement correspondant";
        } else {
            factureExiste.setDelaiLivraison(facture.getDelaiLivraison());
            factureExiste.setGarantie(facture.getGarantie());
            factureExiste.setModePaiement(facture.getModePaiement());
            factureExiste.setValidite(facture.getValidite());
            factureExiste.setRemise(facture.getRemise());
            try {
                JPA.em().persist(factureExiste);
            } catch (Exception e) {
                result = e.toString();
            }
        }
        return result;
    }

    /**
     * Supprimer une liste de facture en fonction d'une referenceFactureProforma
     *
     * @return
     */
    public String supprimer(String referenceFactureProforma) {
        String result;

        List<Facture> factures = findByReferenceFactureProforma(referenceFactureProforma);

        for (Facture facture : factures) {
            result = facture.delete(facture.getId());

            if (result != null) {
                return result;
            }
        }
        return null;
    }

    /**
     * Supprimer une produit
     *
     * @param id
     * @return
     */
    public String delete(Long id) {
        String result = null;

        Facture factureExiste = findById(id);

        if (factureExiste == null) {
            return "aucun enregistrement correspondant";
        } else {
            try {
                JPA.em().remove(factureExiste);
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

    public String getReferenceFactureProforma() {
        return referenceFactureProforma;
    }

    public void setReferenceFactureProforma(String referenceFactureProforma) {
        this.referenceFactureProforma = referenceFactureProforma;
    }

    public String getReferenceBonCommande() {
        return referenceBonCommande;
    }

    public void setReferenceBonCommande(String referenceBonCommande) {
        this.referenceBonCommande = referenceBonCommande;
    }

    public String getReferenceFactureDefinitive() {
        return referenceFactureDefinitive;
    }

    public void setReferenceFactureDefinitive(String referenceFactureDefinitive) {
        this.referenceFactureDefinitive = referenceFactureDefinitive;
    }

    public String getReferenceClient() {
        return referenceClient;
    }

    public void setReferenceClient(String referenceClient) {
        this.referenceClient = referenceClient;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Long getTelephone() {
        return telephone;
    }

    public void setTelephone(Long telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getReferenceProduit() {
        return referenceProduit;
    }

    public void setReferenceProduit(String referenceProduit) {
        this.referenceProduit = referenceProduit;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
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

    public Long getQuantite() {
        return quantite;
    }

    public void setQuantite(Long quantite) {
        this.quantite = quantite;
    }

    public String getDelaiLivraison() {
        return delaiLivraison;
    }

    public void setDelaiLivraison(String delaiLivraison) {
        this.delaiLivraison = delaiLivraison;
    }

    public String getGarantie() {
        return garantie;
    }

    public void setGarantie(String garantie) {
        this.garantie = garantie;
    }

    public String getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(String modePaiement) {
        this.modePaiement = modePaiement;
    }

    public String getValidite() {
        return validite;
    }

    public void setValidite(String validite) {
        this.validite = validite;
    }

    public Long getRemise() {
        return remise;
    }

    public void setRemise(Long remise) {
        this.remise = remise;
    }

    public Date getWhenDone() {
        return whenDone;
    }

    public void setWhenDone(Date whenDone) {
        this.whenDone = whenDone;
    }

    public String getWhoDone() {
        return whoDone;
    }

    public void setWhoDone(String whoDone) {
        this.whoDone = whoDone;
    }
}
