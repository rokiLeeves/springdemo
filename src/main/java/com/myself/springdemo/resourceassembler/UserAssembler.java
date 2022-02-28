package com.myself.springdemo.resourceassembler;


import com.myself.springdemo.bean.User;
import com.myself.springdemo.controller.MainController;
import com.myself.springdemo.resource.UserResource;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class UserAssembler extends RepresentationModelAssemblerSupport<User, UserResource> {

    public UserAssembler() {
        super(MainController.class, UserResource.class);
    }

    @Override
    public CollectionModel<UserResource> toCollectionModel(Iterable<? extends User> entities) {
        return super.toCollectionModel(entities);
    }

    @Override
    protected UserResource instantiateModel(User entity) {
        return new UserResource(entity);
    }

    @Override
    public UserResource toModel(User entity) {
        return this.createModelWithId(entity.getId(), entity);
    }

//    @Override
//    protected UserResource createModelWithId(Object id, User entity) {
//
//        UserResource instance = this.instantiateModel(entity);
//        int uid=(int)id;
//        instance.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MainController.class).getU(uid)).slash(id).withRel("getuser"));
//        return instance;
//    }
}
