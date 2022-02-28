package com.myself.springdemo.message;


import com.myself.springdemo.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

@Service
@Slf4j
public class Receiver {

    @Autowired
    private RabbitTemplate rabbit;
    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage() {
        User user =(User) this.rabbit.receiveAndConvert();
        log.info("Received <" + user.getName() + ">");
        this.latch.countDown();
    }

    public CountDownLatch getLatch() {
        return this.latch;
    }
}
