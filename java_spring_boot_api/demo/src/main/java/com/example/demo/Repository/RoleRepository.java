package com.example.demo.Repository;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {


}
