package com.javagda25.securitytemp.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 4)
    private String username;

    @NotEmpty
    @Size(min = 4)
    private String password;

//    każda rola może być przypisana do każdej ilości użytkowników
//    Cascade ma odłączyć role, a nie ją usunąć
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @Cascade(value = org.hibernate.annotations.CascadeType.DETACH)
    private Set<AccountRole> accountRoles;
}
