package utils;

import models.Facture;
import java.util.List;

public class FactureTotalPojo {
    private Long ht;
    private Long remise;
    private Long net;
    private Long tva;
    private Long ttc;


    public FactureTotalPojo(Long ht, Long remise, Long net, Long tva, Long ttc) {
        this.ht = ht;
        this.remise = remise;
        this.net = net;
        this.tva = tva;
        this.ttc = ttc;
    }

    public FactureTotalPojo() {
    }

    public FactureTotalPojo calcul(String referenceFactureProforma) {
        FactureTotalPojo factureTotalPojo = new FactureTotalPojo(0l, 0l, 0l, 0l, 0l);
        List<Facture> factures = new Facture().findListByReferenceFactureProforma(referenceFactureProforma);

        Long ht = 0l;

        for(Facture facture : factures) {
           if(facture.getPrix() != null && facture.getQuantite() != null) {
               ht = ht + facture.getPrixVente();
           }
        }

        if(factures.size() > 1) {
            Facture facture = factures.get(1);

            Long quantite = facture.getQuantite();

            ht = ht * quantite;

            remise = 0l;

            if(null != facture.getRemise()) {
                remise =  ht * facture.getRemise()/100;
                System.out.println("remise "+remise);
            }

            net = ht - remise;

            tva = net * 19/100;

            ttc = net + tva ;

            factureTotalPojo.setHt(ht);
            factureTotalPojo.setNet(net);
            factureTotalPojo.setRemise(remise);
            factureTotalPojo.setTva(tva);
            factureTotalPojo.setTtc(ttc);

        }


        return factureTotalPojo;

    }

    public Long getHt() {
        return ht;
    }

    public void setHt(Long ht) {
        this.ht = ht;
    }

    public Long getRemise() {
        return remise;
    }

    public void setRemise(Long remise) {
        this.remise = remise;
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
}
