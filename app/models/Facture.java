package models;

import play.data.format.Formats;
import play.db.jpa.JPA;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "facture")
public class Facture {
    @ManyToOne
    private Client client;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "reference_facture_proforma", nullable = false)
    private String referenceFactureProforma;
    @Column(name = "reference_bon_livraison")
    private String referenceBonLivraison;
    @Column(name = "reference_facture_definitive")
    private String referenceFactureDefinitive;

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
    @Column(name = "intitule")
    private String intitule;

    @Transient
    private Long ht;
    @Transient
    private Long remiseMontant;
    @Transient
    private Long net;
    @Transient
    private Long tva;
    @Transient
    private Long ttc;

    @Column(name = "when_done")
    @Formats.DateTime(pattern = "dd-MM-yyyy")
    private Date whenDone;



    /**
     * Constructeur avec paramètre
     * @param client
     * @param referenceFactureProforma
     * @param referenceBonLivraison
     * @param referenceFactureDefinitive
     * @param delaiLivraison
     * @param garantie
     * @param modePaiement
     * @param validite
     * @param remise
     * @param intitule
     * @param whenDone
     * @param ht
     * @param remiseMontant
     * @param net
     * @param tva
     * @param ttc
     */
    public Facture(Client client, String referenceFactureProforma, String referenceBonLivraison, String referenceFactureDefinitive, String delaiLivraison, String garantie, String modePaiement, String validite, Long remise, String intitule, Date whenDone, Long ht, Long remiseMontant, Long net, Long tva, Long ttc) {
        this.client = client;
        this.referenceFactureProforma = referenceFactureProforma;
        this.referenceBonLivraison = referenceBonLivraison;
        this.referenceFactureDefinitive = referenceFactureDefinitive;
        this.delaiLivraison = delaiLivraison;
        this.garantie = garantie;
        this.modePaiement = modePaiement;
        this.validite = validite;
        this.remise = remise;
        this.intitule = intitule;
        this.whenDone = whenDone;
        this.ht = ht;
        this.remiseMontant = remiseMontant;
        this.net = net;
        this.tva = tva;
        this.ttc = ttc;
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
     * Retrouver une facture by idClient
     *
     * @param idClient
     * @return
     */
    public List<Facture> findListByClient(Long idClient) {
        try {
            return JPA.em().createQuery("select facture From Facture facture WHERE facture.client.id = :idClient").setParameter("idClient", idClient).getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrouver liste facture by referenceFactureProforma
     *
     * @return
     */
    public List<Facture> findListByFactureProforma() {
        try {
            return JPA.em().createQuery("select facture From Facture facture WHERE facture.referenceFactureProforma <> null").getResultList();
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * Retrouver liste facture by referenceBonLivraison
     *
     * @return
     */
    public List<Facture> findListByBonLivraison() {
        try {
            return JPA.em().createQuery("select facture From Facture facture WHERE facture.referenceBonLivraison <> null").getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrouver liste facture by referenceFactureDefinitive
     *
     * @return
     */
    public List<Facture> findListByFactureDefinitive() {
        try {
            return JPA.em().createQuery("select facture From Facture facture WHERE facture.referenceFactureDefinitive <> null").getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrouvez liste facture proforma by client
     * @param idClient
     * @return
     */
    public List<Facture> findListByFactureProformaByClient(Long idClient) {
        try {
            return JPA.em().createQuery("select facture From Facture facture WHERE facture.referenceFactureProforma <> null AND  facture.client.id = :idClient").setParameter("idClient", idClient).getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrouvez liste facture definitive by client
     * @param idClient
     * @return
     */
    public List<Facture> findListByFactureDefinitiveByClient(Long idClient) {
        try {
            return JPA.em().createQuery("select facture From Facture facture WHERE facture.referenceFactureDefinitive <> null AND  facture.client.id = :idClient").setParameter("idClient", idClient).getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrouvez liste bon livraison by client
     * @param idClient
     * @return
     */
    public List<Facture> findListByBonLivraisonByClient(Long idClient) {
        try {
            return JPA.em().createQuery("select facture From Facture facture WHERE facture.referenceBonLivraison <> null AND  facture.client.id = :idClient").setParameter("idClient", idClient).getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrouver un facture by referenceFactureProforma
     *
     * @param referenceFactureProforma
     * @return
     */
    public Facture findByReferenceFactureProforma(String referenceFactureProforma) {
        try {
            return (Facture) JPA.em().createQuery("select facture From Facture facture WHERE facture.referenceFactureProforma = :referenceFactureProforma").setParameter("referenceFactureProforma", referenceFactureProforma).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * Retrouver un facture by referenceBonLivraison
     *
     * @param referenceBonLivraison
     * @return
     */
    public Facture findByReferenceBonLivraison(String referenceBonLivraison) {
        try {
            return (Facture) JPA.em().createQuery("select facture From Facture facture WHERE facture.referenceBonLivraison = :referenceBonLivraison").setParameter("referenceBonLivraison", referenceBonLivraison).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrouver un facture by referenceFactureDefinitive
     *
     * @param referenceFactureDefinitive
     * @return
     */
    public Facture findByReferenceFactureDefinitive(String referenceFactureDefinitive) {
        try {
            return (Facture) JPA.em().createQuery("select facture From Facture facture WHERE facture.referenceFactureDefinitive = :referenceFactureDefinitive").setParameter("referenceFactureDefinitive", referenceFactureDefinitive).getSingleResult();
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
        Facture factureExiste = findByReferenceFactureProforma(facture.getReferenceFactureProforma());

        if (factureExiste != null) {
            return "Cette facture existe";
        } else {
            String result = null;
            try {
                facture.setWhenDone(new Date());
                JPA.em().persist(facture);
                JPA.em().flush();
            } catch (Exception e) {
                result = e.toString();
            }
            return result;
        }

    }

    /**
     * Modifier une facture
     *
     * @param facture
     * @return
     */
    public String update(Facture facture) {
        String result = null;

        Facture factureExiste = findById(facture.getId());

        if (factureExiste == null) {
            return "aucun enregistrement correspondant";
        } else {
            factureExiste.setClient(facture.getClient());
            factureExiste.setReferenceFactureProforma(facture.getReferenceFactureProforma());
            factureExiste.setReferenceBonLivraison(facture.getReferenceBonLivraison());
            factureExiste.setReferenceFactureDefinitive(facture.getReferenceFactureDefinitive());

            factureExiste.setDelaiLivraison(facture.getDelaiLivraison());
            factureExiste.setRemise(facture.getRemise());
            factureExiste.setGarantie(facture.getGarantie());
            factureExiste.setModePaiement(facture.getModePaiement());
            factureExiste.setNet(facture.getNet());
            factureExiste.setValidite(facture.getValidite());
            factureExiste.setIntitule(facture.getIntitule());
            try {
                JPA.em().persist(factureExiste);
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

        Facture factureExiste = findById(id);

        if (factureExiste == null) {
            return "aucun enregistrement correspondant";
        } else {

            List<Commande> commandeList = new Commande().findListByFacture(factureExiste.getId());

            String resultCommande;

            for(Commande commande : commandeList) {
                resultCommande = new Commande().delete(commande.getId());

                if(null != resultCommande) {
                    return "Erreur de suppression de commandes";
                }
            }

            try {
                JPA.em().remove(factureExiste);
                JPA.em().flush();
            } catch (Exception e) {
                result = e.toString();
            }
            return result;
        }
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
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

    public String getReferenceBonLivraison() {
        return referenceBonLivraison;
    }

    public void setReferenceBonLivraison(String referenceBonLivraison) {
        this.referenceBonLivraison = referenceBonLivraison;
    }

    public String getReferenceFactureDefinitive() {
        return referenceFactureDefinitive;
    }

    public void setReferenceFactureDefinitive(String referenceFactureDefinitive) {
        this.referenceFactureDefinitive = referenceFactureDefinitive;
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

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public Long getHt() {
        return ht;
    }

    public void setHt(Long ht) {
        this.ht = ht;
    }

    public Long getRemiseMontant() {
        return remiseMontant;
    }

    public void setRemiseMontant(Long remiseMontant) {
        this.remiseMontant = remiseMontant;
    }

    public Long getNet() {
        return net;
    }

    public void setNet(Long net) {
        this.net = net;
    }

    public Long getTva() {
        return tva;
    }

    public void setTva(Long tva) {
        this.tva = tva;
    }

    public Long getTtc() {
        return ttc;
    }

    public void setTtc(Long ttc) {
        this.ttc = ttc;
    }

    public Date getWhenDone() {
        return whenDone;
    }

    public void setWhenDone(Date whenDone) {
        this.whenDone = whenDone;
    }
}
