package com.example.demo.service;

import com.example.demo.entity.Item;
import com.example.demo.entity.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    User newUser(User user);
    List<User> getUsers();
    List<User> getUsersWithFirstName(String username);
    User addRoleToUser(Long userid, Long roleid);
    User addItemToUser(Long userid, Long itemid);
    Set<Item> getUserItems(Long userid);

    Boolean forgotPass(String email);

    Boolean resetPass(String email, int code, String newPass);

}
