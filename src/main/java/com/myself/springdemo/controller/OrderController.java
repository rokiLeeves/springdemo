package com.myself.springdemo.controller;


import com.myself.springdemo.bean.User;
import com.myself.springdemo.message.RabbitMQService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@NoArgsConstructor
@RestController
@RequestMapping("order")
public class OrderController {

    RabbitMQService ms;

    @Autowired
    public OrderController(RabbitMQService jms) {
        this.ms = jms;
    }

    @PostMapping("send/{name}/{age}")
    public User sendUserMessage(@PathVariable String name, @PathVariable int age) {
        User user = new User();
        user.setName(name);
        user.setAge(age);
        this.ms.sendUser(user);
        return user;
    }


}
