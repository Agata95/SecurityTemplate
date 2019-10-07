package com.javagda25.securitytemp.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/")
public class IndexController {


    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/tylkodlakozakow")
    public String tylkoDlaKozakow(){
        return "index";
    }

    @GetMapping("/login")
    public String indexZLogowaniem(){
        return "login-form";
    }

}
