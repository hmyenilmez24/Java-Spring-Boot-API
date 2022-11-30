package com.example.demo.service;

import com.example.demo.entity.Item;
import com.example.demo.entity.Role;

import java.util.List;

public interface RoleService {

    Role createRole(Role role);
    List<Role> getRoles();
}