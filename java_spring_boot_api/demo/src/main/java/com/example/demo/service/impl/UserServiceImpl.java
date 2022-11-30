package com.example.demo.service.impl;

import com.example.demo.Repository.ItemRepository;
import com.example.demo.Repository.RoleRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.entity.Item;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.smtp.EmailDetails;
import com.example.demo.smtp.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ItemRepository itemRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final EmailService emailService;

    @Override
    public User newUser(User user) {
        if (user.getFirstName().equals("") ||
                user.getLastName().equals("") ||
                user.getUsername().equals("") ||
                user.getEmail().equals("") ||
                user.getPassword().equals("")
        ){
            throw new NullPointerException();
        }
        try{
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Random random = new Random();
        int randomCode = Integer.parseInt(String.format("%09d", random.nextInt(1000000000)));
        user.setResetCode(randomCode);
        return userRepository.save(user);
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException();
        }catch (NullPointerException e){
            throw new NullPointerException();
        }catch (Exception e){
            throw new Error(e);
        }
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getUsersWithFirstName(String firstName) {
        List<User> resultUser = userRepository.findByFirstName(firstName);

        if (resultUser.isEmpty()){
            throw new EntityNotFoundException();

        }else{
            return resultUser;
        }
    }

    @Override
    public User addRoleToUser(Long userid, Long roleid) {
        Optional<User> resultUser = userRepository.findById(userid);
        Optional<Role> resultRole = roleRepository.findById(roleid);

        if (resultUser.isPresent() && resultRole.isPresent()) {
            resultUser.get().getRoles().add(resultRole.get());
            return userRepository.save(resultUser.get());
        }else{
            throw new EntityNotFoundException();
        }
    }

    @Override
    public User addItemToUser(Long userid, Long itemid) {
        Optional<User> resultUser = userRepository.findById(userid);
        Optional<Item> resultItem = itemRepository.findById(itemid);

        if (resultUser.isPresent() && resultItem.isPresent()) {
            resultUser.get().getItems().add(resultItem.get());
            return userRepository.save(resultUser.get());
        }else{
            throw new EntityNotFoundException();
        }
    }

    @Override
    public Set<Item> getUserItems(Long userid) {
        Optional<User> resultUser = userRepository.findById(userid);
        if (resultUser.isPresent()) {
            return resultUser.get().getItems();
        }else{
            throw new EntityNotFoundException();
        }
    }

    public Boolean forgotPass(String email) {
        Random random = new Random();
        int resetCode = Integer.parseInt(String.format("%04d", random.nextInt(10000)));

        Optional<User> resultUser = userRepository.findByEmail(email);
        if (resultUser.isPresent()) {
            Boolean status
                    = emailService.sendSimpleMail(new EmailDetails(
                    email,
                    Integer.toString(resetCode),
                    "Reset Password Code"
            ));
            if(status){
                try{
                resultUser.get().setResetCode(resetCode);
                userRepository.save(resultUser.get());
                }catch (IllegalArgumentException e){
                    throw new IllegalArgumentException();
                }catch (Exception e){
                    throw new Error(e);
                }
            }
            return status;
        }
        return false;
    }

    @Override
    public Boolean resetPass(String email, int code, String newPass) {
        Optional<User> resultUser = userRepository.findByEmail(email);
        if (resultUser.isPresent() && resultUser.get().getResetCode() == code) {
            resultUser.get().setPassword(passwordEncoder.encode(newPass));

            Random random = new Random();
            int randomCode = Integer.parseInt(String.format("%09d", random.nextInt(1000000000)));
            resultUser.get().setResetCode(randomCode);
            try{
            userRepository.save(resultUser.get());
            }catch (IllegalArgumentException e){
                throw new IllegalArgumentException();
            }catch (Exception e){
                throw new Error(e);
            }
            return true;
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.error("User not found in DB");
            throw new UsernameNotFoundException("User not found in DB");
        } else {
            log.info("User found in DB: {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

}
