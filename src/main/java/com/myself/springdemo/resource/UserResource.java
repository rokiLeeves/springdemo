package com.myself.springdemo.resource;


import com.myself.springdemo.bean.User;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.List;

@Relation(value = "user", collectionRelation = "users")
public class UserResource extends RepresentationModel<UserResource> {

    @Getter
    private String name;

    @Getter
    private int age;

    @Getter
    private List<String> list;

    public UserResource(User user) {
        this.name = user.getName();
        this.age = user.getAge();
        // this.list = user.getList();
    }


}
