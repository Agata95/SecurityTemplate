package com.javagda25.securitytemp.demo.service;

import com.javagda25.securitytemp.demo.model.Account;
import com.javagda25.securitytemp.demo.model.AccountRole;
import com.javagda25.securitytemp.demo.model.dto.AccountPasswordResetRequest;
import com.javagda25.securitytemp.demo.repository.AccountRepository;
import com.javagda25.securitytemp.demo.repository.AccountRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class AccountService {
    private AccountRepository accountRepository;
    private PasswordEncoder passwordEncoder;
    private AccountRoleService accountRoleService;
    private AccountRoleRepository accountRoleRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder, AccountRoleService accountRoleService, AccountRoleRepository accountRoleRepository) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.accountRoleService = accountRoleService;
        this.accountRoleRepository = accountRoleRepository;
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

    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    public void toggleLock(Long accountId) {
        if (accountRepository.existsById(accountId)) {
            Account account = accountRepository.getOne(accountId);
            account.setLocked(!account.isLocked());

            accountRepository.save(account);
        }
    }

    //    nie usuwamy kont admin
    public void remove(Long accountId) {
        if (accountRepository.existsById(accountId)) {
            Account account = accountRepository.getOne(accountId);

            if (!account.isAdmin()) {
                accountRepository.delete(account);
            }
        }
    }

    public Optional<Account> findById(Long accountId) {
        return accountRepository.findById(accountId);
    }

    public void resetPassword(AccountPasswordResetRequest request) {
        if (accountRepository.existsById(request.getAccountId())) {
            Account account = accountRepository.getOne(request.getAccountId());

//            należy pamiętać o zaszyfrowaniu hasła
            account.setPassword(passwordEncoder.encode(request.getResetpassword()));

            accountRepository.save(account);
        }
    }

    public void editRoles(Long accoutId, HttpServletRequest request) {
        if (accountRepository.existsById(accoutId)) {
            Account account = accountRepository.getOne(accoutId);

            Map<String, String[]> formParameters = request.getParameterMap();
            Set<AccountRole> newCollectionOfRoles = new HashSet<>();

            for (String roleName : formParameters.keySet()) {
                String[] values = formParameters.get(roleName);

                if (values[0].equals("on")) {
                    accountRoleRepository.findByName(roleName).ifPresent(newCollectionOfRoles::add);
                }
            }

            account.setAccountRoles(newCollectionOfRoles);

            accountRepository.save(account);
        }

    }
}
