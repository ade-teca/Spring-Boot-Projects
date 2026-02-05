package com.keisar.Library.Management.Application.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter @Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  String username;
    private String password;
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles;

}
