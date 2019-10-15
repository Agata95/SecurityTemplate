package com.javagda25.securitytemp.demo.component;

import com.javagda25.securitytemp.demo.model.AccountRole;
import com.javagda25.securitytemp.demo.repository.AccountRepository;
import com.javagda25.securitytemp.demo.repository.AccountRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {
    private AccountRepository accountRepository;
    private AccountRoleRepository accountRoleRepository;

    @Autowired
    public DataInitializer(AccountRepository accountRepository, AccountRoleRepository accountRoleRepository) {
        this.accountRepository = accountRepository;
        this.accountRoleRepository = accountRoleRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        addDefaultRole("USER");
        addDefaultRole("ADMIN");
    }

    private void addDefaultRole(String role) {
        if (!accountRoleRepository.existsByName(role)) {
            AccountRole newRole = new AccountRole();
            newRole.setName(role);

            accountRoleRepository.save(newRole);
        }
    }
}
