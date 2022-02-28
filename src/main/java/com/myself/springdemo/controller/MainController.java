package com.myself.springdemo.controller;


import com.myself.springdemo.bean.User;
import com.myself.springdemo.resource.UserResource;
import com.myself.springdemo.resourceassembler.UserAssembler;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@NoArgsConstructor
@RestController
@RequestMapping("/user")
//@SessionAttributes("user")
public class MainController {

    // private TestRepository tr;

//    @Autowired
//    public MainController(TestRepository tr) {
//        this.tr = tr;
//    }

    @PostMapping(value = "/newuser", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        return user;
    }


//    @GetMapping("/get")
//    public User getUser(User usr) {
//        User u = new User();
//        Map<String, Object> user = this.tr.save(u);
//        u.setName(usr.getName());
//        return u;
//    }

//    @GetMapping("/getUser")
//    public User getU(int id) {
//        User u = new User();
//        u.setId(id);
//        Map<String, Object> user = this.tr.save(u);
//        return u;
//    }


    @GetMapping(value = "/delete/{uid}/{type}", produces = "application/json")
    public CollectionModel<UserResource> deleteUser(@PathVariable("uid") int uid,
                                                    @PathVariable("type") String type,
                                                    @MatrixVariable("sex") String sex,
                                                    @MatrixVariable(value = "age", pathVar = "type") int age) {
        User user = new User();
//        user.list.add(Calendar.getInstance().get(Calendar.SECOND) + ":");
//        user.list.add(uid + "");
//        user.list.add(type);
//        user.list.add(sex);
//        user.list.add(String.valueOf(age));
        user.setId(11L);
        ArrayList<User> users = new ArrayList<>();

        User user2 = new User();
        user2.setAge(10);
        user2.setName("123");
        user2.setId(19L);

        users.add(user);
        users.add(user2);
        CollectionModel<UserResource> ur = new UserAssembler().toCollectionModel(users);
        //ur.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MainController.class).getU()).withRel("getuser"));

        return ur;
    }

}
