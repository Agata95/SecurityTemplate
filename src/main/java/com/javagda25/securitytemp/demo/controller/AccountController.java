package com.javagda25.securitytemp.demo.controller;

import com.javagda25.securitytemp.demo.model.Account;
import com.javagda25.securitytemp.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/user/")
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/register")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public String registrationForm(Model model, Account account) {
        model.addAttribute("newAccount", account);

        return "registration-form";
    }

    @PostMapping("/register")
    @PreAuthorize(value = "hasRole('ADMIN')")
//    po @Valid musi być BindingResult
    public String register(@Valid Account account,
                           BindingResult result,
                           String passwordConfirm,
                           Model model) {

        if (result.hasErrors()) {
            return registrationError(model, account, result.getFieldError().getDefaultMessage());
        }
//        todo: tworzenie konta
        if (!account.getPassword().equals(passwordConfirm)) {
            return registrationError(model, account, "Password do not match.");
        }

        if (!accountService.register(account)) {
            return registrationError(model, account, "User with given username already exists.");
        }

        return "redirect:/login";
    }

    private String registrationError(Model model, Account account, String s) {
        model.addAttribute("newAccount", account);
        model.addAttribute("errorMessage", s);

        return "registration-form";
    }
}
