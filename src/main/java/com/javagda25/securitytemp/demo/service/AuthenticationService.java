package com.javagda25.securitytemp.demo.service;

import com.javagda25.securitytemp.demo.model.Account;
import com.javagda25.securitytemp.demo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    //    interface zgłasza nam, że klasa jest servicem który będzie przyjmował i sprawdzał uzytkownika w bazie danych
//    spring musi załadować użytkownika, wynikiem jest UserDetails
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> optionalAccount = accountRepository.findByUsername(username);
        if (optionalAccount.isPresent()) {
//            encja bazodanowa:
            Account account = optionalAccount.get();
            return User.builder()
                    .username(account.getUsername())
                    .password(account.getPassword())
                    .roles("USER")
                    .build();

        }

        throw new UsernameNotFoundException("Username not found.");
    }
}
