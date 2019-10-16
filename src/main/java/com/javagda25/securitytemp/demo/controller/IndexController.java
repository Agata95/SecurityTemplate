package com.javagda25.securitytemp.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping(path = "/")
public class IndexController {


    @GetMapping("/")
//    wyciągamy nazwę zalogowanego użytkownika przez Principal:
    public String index(Model model, Principal authentication) {
        if (authentication != null) {
            model.addAttribute("user", authentication.getName());
        }
        return "index";
    }

    //    gdy nie jesteśmy zalogowani nie da się wejść do danej strony,
//    tylko po zalogowaniu można na tym mappingu przeglądać strony
    @GetMapping("/tylkodlakozakow")
    public String tylkoDlaKozakow() {
        return "index";
    }

    @GetMapping("/login")
    public String indexZLogowaniem() {
        return "login-form";
    }

}
