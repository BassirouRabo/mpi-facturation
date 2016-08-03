package utils;


public class GenerateReference {

    /**
     * Génération référence facture proforma
     * @return
     */
    public static String generateReferenceFactureProforma() {
        return new GenerateRandom().generateRandomString();
    }

    /**
     * Génération référence bon de livraison
     * @param referenceFactureProforma
     * @return
     */
    public static String generateReferenceBonLivraison(String referenceFactureProforma) {
        return new GenerateRandom().generateRandomString();
    }

    /**
     * Génération référence facture définitive
     * @param referenceFactureProforma
     * @return
     */
    public static String generateReferenceFactureDefinitive(String referenceFactureProforma) {
        return new GenerateRandom().generateRandomString();
    }
}
