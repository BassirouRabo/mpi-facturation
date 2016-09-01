package utils;


import models.Facture;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class GenerateReference {

    /**
     * Get current Month
     * @return
     */
    private static String getMois() {
        Integer moisInteger = Calendar.getInstance().get(Calendar.MONTH);

        if (moisInteger < 10) {
            return "0" + moisInteger;
        } else {
            return Integer.toString(moisInteger);
        }
    }

    /**
     * Get current year
     *
     * @return
     */
    private static String getAnnee() {
        return Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
    }

    private static String getNumeroFactureProforma(String referenceFactureProforma) {
        return referenceFactureProforma.substring(2, 6);
    }

    private static String getMoisFactureProforma(String referenceFactureProforma) {
        return referenceFactureProforma.substring(6, 8);
    }

    private static String getAnneeFactureProforma(String referenceFactureProforma) {
        return referenceFactureProforma.substring(8, 12);
    }

    private static String getNumeroBonLivraison(String referenceBonLivraison) {
        return referenceBonLivraison.substring(2, 6);
    }

    private static String getMoisBonLivraison(String referenceBonLivraison) {
        return referenceBonLivraison.substring(6, 8);
    }

    private static String getAnneeBonLivraison(String referenceBonLivraison) {
        return referenceBonLivraison.substring(8, 12);
    }

    private static String getNumeroFactureDefinitive(String referenceFactureDefinitive) {
        return referenceFactureDefinitive.substring(2, 6);
    }

    private static String getMoisFactureDefinitive(String referenceFactureDefinitive) {
        return referenceFactureDefinitive.substring(6, 8);
    }

    private static String getAnneeFactureDefinitive(String referenceFactureDefinitive) {
        return referenceFactureDefinitive.substring(8, 12);
    }

    /**
     * Génération référence facture proforma
     * @return
     */
    public static String generateReferenceFactureProforma() {
        String mois = getMois();
        String annee = getAnnee();

        List<Facture> factures = new Facture().findListByFactureProforma();
        List<FacturePojo> facturePojos = new ArrayList<>();
        List<FacturePojo> facturePojosExiste = new ArrayList<>();

        if (factures == null) {
            return "FP" + "0001" + mois + "" + annee;
        } else {

            for (Facture facture : factures) {
                String referenceFactureProforma = facture.getReferenceFactureProforma();
                facturePojos.add(new FacturePojo(facture.getId(), referenceFactureProforma, getNumeroFactureProforma(referenceFactureProforma), getMoisFactureProforma(referenceFactureProforma), getAnneeFactureProforma(referenceFactureProforma)));
            }

            facturePojosExiste.addAll(facturePojos.stream().filter(facturePojo -> facturePojo.getMois().equals(getMois()) && facturePojo.getAnnee().equals(getAnnee())).collect(Collectors.toList()));

            if (facturePojosExiste.size() == 0) {
                return "FP" + "0001" + mois + "" + annee;
            } else {
                Integer numero = 0;
                String numeroString;
                for (FacturePojo facturePojo : facturePojosExiste) {
                    Integer numeroFacture = Integer.parseInt(facturePojo.getNumero());
                    if (numeroFacture > numero) {
                        numero = numeroFacture + 1;
                    }
                }

                if (numero < 10) {
                    numeroString = "000" + numero;
                } else if (numero < 100) {
                    numeroString = "00" + numero;
                } else if (numero < 1000) {
                    numeroString = "0" + numero;
                } else {
                    numeroString = "" + numero;
                }

                return "FP" + numeroString + mois + "" + annee;
            }

        }

    }

    /**
     * Génération référence bon de livraison
     * @return
     */
    public static String generateReferenceBonLivraison() {
        String mois = getMois();
        String annee = getAnnee();

        List<Facture> factures = new Facture().findListByBonLivraison();
        List<FacturePojo> facturePojos = new ArrayList<>();
        List<FacturePojo> facturePojosExiste = new ArrayList<>();

        if (factures == null) {
            return "BL" + "0001" + mois + "" + annee;
        } else {

            for (Facture facture : factures) {
                String referenceBonLivraison = facture.getReferenceBonLivraison();
                facturePojos.add(new FacturePojo(facture.getId(), referenceBonLivraison, getNumeroBonLivraison(referenceBonLivraison), getMoisBonLivraison(referenceBonLivraison), getAnneeBonLivraison(referenceBonLivraison)));
            }

            facturePojosExiste.addAll(facturePojos.stream().filter(facturePojo -> facturePojo.getMois().equals(getMois()) && facturePojo.getAnnee().equals(getAnnee())).collect(Collectors.toList()));

            if (facturePojosExiste.size() == 0) {
                return "BL" + "0001" + mois + "" + annee;
            } else {
                Integer numero = 0;
                String numeroString;
                for (FacturePojo facturePojo : facturePojosExiste) {
                    Integer numeroFacture = Integer.parseInt(facturePojo.getNumero());
                    if (numeroFacture > numero) {
                        numero = numeroFacture + 1;
                    }
                }

                if (numero < 10) {
                    numeroString = "000" + numero;
                } else if (numero < 100) {
                    numeroString = "00" + numero;
                } else if (numero < 1000) {
                    numeroString = "0" + numero;
                } else {
                    numeroString = "" + numero;
                }

                return "BL" + numeroString + mois + "" + annee;
            }

        }

    }

    /**
     * Génération référence facture definitive
     * @return
     */
    public static String generateReferenceFactureDefinitive() {
        String mois = getMois();
        String annee = getAnnee();

        List<Facture> factures = new Facture().findListByFactureDefinitive();
        List<FacturePojo> facturePojos = new ArrayList<>();
        List<FacturePojo> facturePojosExiste = new ArrayList<>();

        if (factures == null) {
            return "FD" + "0001" + mois + "" + annee;
        } else {

            for (Facture facture : factures) {
                String referenceFactureDefinitive = facture.getReferenceFactureDefinitive();
                facturePojos.add(new FacturePojo(facture.getId(), referenceFactureDefinitive, getNumeroFactureDefinitive(referenceFactureDefinitive), getMoisFactureDefinitive(referenceFactureDefinitive), getAnneeFactureDefinitive(referenceFactureDefinitive)));
            }

            facturePojosExiste.addAll(facturePojos.stream().filter(facturePojo -> facturePojo.getMois().equals(getMois()) && facturePojo.getAnnee().equals(getAnnee())).collect(Collectors.toList()));

            if (facturePojosExiste.size() == 0) {
                return "FD" + "0001" + mois + "" + annee;
            } else {
                Integer numero = 0;
                String numeroString;
                for (FacturePojo facturePojo : facturePojosExiste) {
                    Integer numeroFacture = Integer.parseInt(facturePojo.getNumero());
                    if (numeroFacture > numero) {
                        numero = numeroFacture + 1;
                    }
                }

                if (numero < 10) {
                    numeroString = "000" + numero;
                } else if (numero < 100) {
                    numeroString = "00" + numero;
                } else if (numero < 1000) {
                    numeroString = "0" + numero;
                } else {
                    numeroString = "" + numero;
                }

                return "FD" + numeroString + mois + "" + annee;
            }

        }

    }
}
