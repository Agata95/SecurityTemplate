package com.javagda25.securitytemp.demo.service;

import com.javagda25.securitytemp.demo.model.Account;
import com.javagda25.securitytemp.demo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private AccountRepository accountRepository;
    private PasswordEncoder passwordEncoder;
    private AccountRoleService accountRoleService;

    @Autowired
    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder, AccountRoleService accountRoleService) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.accountRoleService = accountRoleService;
    }

    public boolean register(Account account) {
        if (accountRepository.existsByUsername(account.getUsername())) {
            return false;
        }
//        szyfrowanie hasła:
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setAccountRoles(accountRoleService.getDefaultRoles());

//        zapis do bazy:
        accountRepository.save(account);

        return false;
    }
}
