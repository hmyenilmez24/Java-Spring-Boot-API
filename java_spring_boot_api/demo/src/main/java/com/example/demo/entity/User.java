package com.example.demo.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "userTable")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @SequenceGenerator(name = "user_seq_gen", sequenceName = "user_gen", initialValue = 100, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_gen")
    @Column
    private Long UserId;
    @Column(nullable = false,unique=true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String email;
    @Column
    private int resetCode;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Item> items = new HashSet<>();
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();


}
