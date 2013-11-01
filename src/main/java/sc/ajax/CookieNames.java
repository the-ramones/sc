package sc.ajax;

/**
 * Cookie constants
 * 
 * @author Paul Kulitski
 */
public interface CookieNames {

    public static final String SC_AUTH_COOKIE_NAME = "SC_SESSION_COOKIE";
    public static final String SC_REMEMBERME_COOKIE_NAME = "SC_REMEMBER_ME_COOKIE";
    public static final String SC_USER_LANGUAGE = "SC_USER_LANGUAGE";
    public static int SESSION_EXPIRATION_TIME = 30 * 60;
    public static int LANG_EXPIRATION_TIME = 365 * 24 * 60 * 60;
}
