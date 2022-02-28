package com.myself.springdemo;

import com.myself.springdemo.bean.User;
import com.myself.springdemo.respository.mongo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
class MongoDBTests {

    @Autowired
    UserRepository ur;


    @Disabled
    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable page = PageRequest.of(10, 5, sort);
        Page<User> pages = ur.findByAgeGreaterThan(10, page);
        log.info("show:");
        pages.forEach(x -> log.info(String.valueOf(x)));
    }

    @Disabled
    @Test
    public void insertTest() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            User u = new User();
            u.setId((long) i);
            u.setName("name" + i);
            u.setAge(i);
            u.setEmail("email@" + i);
            users.add(u);
        }
        log.info(String.valueOf(ur.insert(users)));
    }
}
