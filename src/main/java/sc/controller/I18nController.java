package sc.controller;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for i18zed messages
 *
 * @author the-ramones
 */
@Controller
public class I18nController {

    @Autowired
    @Qualifier("messageSource")
    MessageSource messageSource;

    @RequestMapping(value = "i18n", method = RequestMethod.POST)
    public ResponseEntity<String> i18ze(@RequestParam(value = "code") String code, Locale locale) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=utf-8");
        if (code != null && !code.isEmpty()) {
            System.out.println("MESSAGE: " + messageSource.getMessage(code, null, locale));

            return new ResponseEntity<String>(messageSource.getMessage(code, null, locale), headers, HttpStatus.OK);
        }
        return new ResponseEntity<String>("", headers, HttpStatus.OK);
    }
}
