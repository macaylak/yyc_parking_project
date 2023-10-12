/*
Copyright Ann Barcomb and Emily Marasco, 2021-2023
Licensed under GPL v3
See LICENSE.txt for more information.
*/

package edu.ucalgary.oop;
import java.util.regex.*;

public class Parking {

    /*
     * Standardize provided licence plate to a common format
     * @param licence - The provided licence plate
     * @throws IllegalArgumentException if licence doesn't fit Alberta standards
     */
    public static String standardizeAndValidateLicence(String licence) throws IllegalArgumentException {
        // Standardize licence by making all upper case and removing spacing & punctuation
        licence = licence.toUpperCase();
        licence = licence.replaceAll("[^\\w]", "");
    
        // Alberta licence plates can be up to 7 characters consisting of only letters and
        // numbers. Our parking system doesn't allow licence plates from other parts of Canada,
        // or from other countries.
        Pattern pattern = Pattern.compile("^[A-Z0-9]{1,7}$");
        Matcher matcher = pattern.matcher(licence);
        if (matcher.matches()) {
            return licence;
        }
    
        String error = String.format("Licence plate '%s' is not a valid licence plate in Alberta.", licence);
        throw new IllegalArgumentException(error);
    }

    public static boolean isValidLicence(String licence) {
        try {
            standardizeAndValidateLicence(licence);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    
    }
    
}
