package utils;

/**
 * Created by brabo on 8/11/16.
 */
public class GetReference {

    public String getType(String reference) {
        return reference.substring(0, 2);
    }

    public String getNumero(String reference) {
        return reference.substring(2, 6);
    }

    public String getMois(String reference) {
        return reference.substring(6, 8);
    }

    public String getAnnee(String reference) {
        return reference.substring(8, 12);
    }
    
}
