package com.myself.springdemo.message;


import com.myself.springdemo.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RabbitMQService {

    private RabbitTemplate rabbit;
    private MessageConverter converter;

    @Autowired
    public RabbitMQService(RabbitTemplate rabbit ) {
        this.rabbit = rabbit;
        this.converter= rabbit.getMessageConverter();
    }

    public void sendUser(User user) {
        this.rabbit.convertAndSend(user);
        log.info("send message");
    }

    public User  receiveUser(){
       return (User)this.rabbit.receiveAndConvert("demo.cloud.users");
    }

}