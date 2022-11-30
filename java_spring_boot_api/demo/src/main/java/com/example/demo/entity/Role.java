package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "roleTable")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @SequenceGenerator(name = "role_seq_gen", sequenceName = "role_gen", initialValue = 100, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq_gen")
    @Column
    private Long RoleId;
    @Column(nullable = false)
    private String roleName;


}
