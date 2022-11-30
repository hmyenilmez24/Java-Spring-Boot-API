package com.example.demo.service.impl;

import com.example.demo.Repository.RoleRepository;
import com.example.demo.entity.Item;
import com.example.demo.entity.Role;
import com.example.demo.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role createRole(Role role) {
        if (role.getRoleName().equals("")){
            throw new NullPointerException();
        }
        try{
        return roleRepository.save(role);
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException();
        }catch (NullPointerException e){
            throw new NullPointerException();
        }catch (Exception e){
            throw new Error(e);
        }
    }
    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

}
