package sc.resolver;

import java.util.Locale;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.util.WebUtils;
import sc.ajax.CookieNames;
import sc.ajax.Languages;

/**
 *
 * @author Paul Kulitski
 */
public class CustomCookieLocaleResolver implements LocaleResolver {

    private Locale defaultLocale;
    private String langCookieName;
    private final String REQUEST_LOCALE_ATTRIBUTE_NAME = "SC_REQUEST_ATTRIBUTE_LOCALE";
    protected final static Logger logger = LoggerFactory.getLogger(CustomCookieLocaleResolver.class);

    public CustomCookieLocaleResolver() {
        try {
            defaultLocale = Locale.forLanguageTag(Languages.DEFAULT);
        } catch (NullPointerException ex) {
            logger.debug("Cannot create a locale from sytstem contants. Falling back to 'ENGLISH' locale");
            defaultLocale = Locale.ENGLISH;
        }
        langCookieName = CookieNames.SC_USER_LANGUAGE;
    }

    public CustomCookieLocaleResolver(Locale defaultLocale) {
        this.defaultLocale = defaultLocale;
        langCookieName = CookieNames.SC_USER_LANGUAGE;
    }

    public Locale getDefaultLocale() {
        return defaultLocale;
    }

    public String getLangCookieName() {
        return langCookieName;
    }

    public void setLangCookieName(String langCookieName) {
        this.langCookieName = langCookieName;
    }

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        Locale locale = (Locale) request.getAttribute(REQUEST_LOCALE_ATTRIBUTE_NAME);
        if (locale != null) {
            return locale;
        }
        Cookie langCookie = WebUtils.getCookie(request, langCookieName);

        if (langCookie != null) {
            try {
                locale = Locale.forLanguageTag(langCookie.getValue());
            } catch (NullPointerException ex) {
                logger.debug("Cannot define locate from request cookie. Falling back to default locale");
            }
            if (locale != null) {
                request.setAttribute(REQUEST_LOCALE_ATTRIBUTE_NAME, locale);
                return locale;
            }
        }
        return determineDefaultLocale(request);
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        Cookie cookie;
        if (locale != null) {
            request.setAttribute(REQUEST_LOCALE_ATTRIBUTE_NAME, locale);
            cookie = new Cookie(langCookieName, locale.toString());
            cookie.setMaxAge(CookieNames.LANG_EXPIRATION_TIME);
            response.addCookie(cookie);
        } else {
            request.setAttribute(langCookieName, determineDefaultLocale(request));
            cookie = new Cookie(langCookieName, "");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }

    protected Locale determineDefaultLocale(HttpServletRequest request) {
        Locale defaultLocale = getDefaultLocale();
        if (defaultLocale == null) {
            try {
                defaultLocale = Locale.forLanguageTag(Languages.DEFAULT);
            } catch (NullPointerException ex) {
            }
            if (defaultLocale == null) {
                if (defaultLocale == null) {
                    defaultLocale = request.getLocale();
                }
            }
        }
        return defaultLocale;
    }
}
