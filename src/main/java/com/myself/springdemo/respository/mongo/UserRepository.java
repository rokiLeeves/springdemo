package com.myself.springdemo.respository.mongo;

import com.myself.springdemo.bean.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Integer> {

    Page<User> findByAgeGreaterThan(int age, Pageable page);


}
