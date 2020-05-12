package oauthteste.example.oauthteste;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import org.apache.commons.beanutils.PropertyUtils;

@RestController
public class Controller {

    @GetMapping("/")
    public String home() {
        return "Home";
    }

    @GetMapping("/restricted")
    public Object restricted() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object details = authentication.getPrincipal();

        return listRedirect(details);
    }

    @RequestMapping("/list")
    public RedirectView listRedirect(Object details) {
        RedirectView list = new RedirectView();

        String name = "";
        String mail = "";
        String img = "";

        try {
            Object propValue = PropertyUtils.getProperty(details, "claims");
            name = PropertyUtils.getProperty(propValue, "name").toString();
            mail = PropertyUtils.getProperty(propValue, "email").toString();
            img = PropertyUtils.getProperty(propValue, "picture").toString();
        } catch (Exception e) { e.printStackTrace(); }

        list.setUrl("http://www.google.com");
        list.setUrl("http://localhost:3000/list?name=" + name + "&email=" + mail + "&picture=" + img);
        return list;
    }


}

