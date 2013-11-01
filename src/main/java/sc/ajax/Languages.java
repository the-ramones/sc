package sc.ajax;

/**
 * Supported languages holder
 *
 * @author Paul Kulitski
 */
public class Languages {

    public static String DEFAULT = "en";
    public static final String RU = "ru";
    public static final String EN = "en";
    public static final String BE = "be";

    public static boolean supports(String language) {
        if (language.trim().equalsIgnoreCase(RU)) {
            return true;
        }
        if (language.trim().equalsIgnoreCase(EN)) {
            return true;
        }
        if (language.trim().equalsIgnoreCase(BE)) {
            return true;
        }
        if (language.trim().equalsIgnoreCase(DEFAULT)) {
            return true;
        }
        return false;
    }
    
    public static void setDefault(String lang) {
         if (lang.length() == 2) {
             DEFAULT = lang.toLowerCase();
         }
    }
    
    public static String getDefault() {
        return DEFAULT;
    }
}
