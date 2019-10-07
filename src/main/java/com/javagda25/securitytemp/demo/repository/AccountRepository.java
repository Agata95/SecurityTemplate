package com.javagda25.securitytemp.demo.repository;

import com.javagda25.securitytemp.demo.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
//    Spring sam napisze metodę findByUsername
    Optional<Account> findByUsername(String username);
}
