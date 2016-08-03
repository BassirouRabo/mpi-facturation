package utils;

import java.io.Serializable;


public class FacturePojo implements Serializable {
    private Long id;
    private String reference;
    private String numero;
    private String mois;
    private String annee;

    public FacturePojo(Long id, String reference, String numero, String mois, String annee) {
        this.id = id;
        this.reference = reference;
        this.numero = numero;
        this.mois = mois;
        this.annee = annee;
    }

    /**
     * Constructeur sans paramètre
     */
    public FacturePojo() {
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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getMois() {
        return mois;
    }

    public void setMois(String mois) {
        this.mois = mois;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }
}
