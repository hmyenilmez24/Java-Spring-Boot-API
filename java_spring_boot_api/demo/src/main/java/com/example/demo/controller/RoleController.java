package com.example.demo.controller;

import com.example.demo.entity.Item;
import com.example.demo.entity.Role;
import com.example.demo.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/new")
    public ResponseEntity<Role> createRole(@RequestBody Role role){
        Role resultRole = roleService.createRole(role);
        return ResponseEntity.ok(resultRole);
    }

    @GetMapping("")
    public ResponseEntity<List<Role>> getRoles(){
        List<Role> roles = roleService.getRoles();
        return ResponseEntity.ok(roles);
    }


}
