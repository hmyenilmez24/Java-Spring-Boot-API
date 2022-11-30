package com.example.demo.controller;
import com.example.demo.entity.Item;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/renewal-code/{email}")
    public ResponseEntity<Boolean> forgotPass(@PathVariable("email") String email){
        Boolean status = userService.forgotPass(email);
        return ResponseEntity.ok((status));
    }

    @PutMapping ("/new-password/{email}/{code}/{newPass}")
    public ResponseEntity<Boolean> resetPass(@PathVariable("email") String email, @PathVariable("code") int code,
                                             @PathVariable("newPass") String newPass){
        Boolean status = userService.resetPass(email, code, newPass);
        return ResponseEntity.ok((status));
    }

    @PostMapping("/new")
    public ResponseEntity<User> newUser(@RequestBody User user){
        User resultUser = userService.newUser(user);
        return ResponseEntity.ok(resultUser);
    }

    @GetMapping("")
    public ResponseEntity<List<User>> getUsers(){
        List<User> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/firstnames/{firstname}")
    public ResponseEntity<List<User>> getUsersWithFirstName(@PathVariable("firstname") String firstname){
        List<User> users = userService.getUsersWithFirstName(firstname);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userid}/items")
    public ResponseEntity<Set<Item>> getUserItems(@PathVariable("userid") Long userid){
        Set<Item> resultUser = userService.getUserItems(userid);
        return ResponseEntity.ok(resultUser);
    }

    @PutMapping("/{userid}/roles/{roleid}")
    public ResponseEntity<User> addRoleToUser(@PathVariable("userid") Long userid,
                                           @PathVariable("roleid") Long roleid
                                            ){
        User resultUser = userService.addRoleToUser(userid, roleid);
        return ResponseEntity.ok(resultUser);
    }

    @PutMapping("/{userid}/items/{itemid}")
    public ResponseEntity<User> addItemToUser(@PathVariable("userid") Long userid,
                           @PathVariable("itemid") Long itemid
    ){
        User resultUser = userService.addItemToUser(userid, itemid);
        return ResponseEntity.ok(resultUser);
    }

}
