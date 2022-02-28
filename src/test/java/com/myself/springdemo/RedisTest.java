package com.myself.springdemo;


import com.myself.springdemo.bean.User;
import com.myself.springdemo.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;

@SpringBootTest
@Slf4j
public class RedisTest {

    private RedisTemplate<String,Serializable> redisOp;
    private RedisUtil rdsUtil;

    @Autowired
    public RedisTest(@Qualifier("SerializableTemplate") RedisTemplate<String, Serializable> redisTemplate, RedisUtil rdsUtil){
        this.rdsUtil=rdsUtil;
        this.redisOp=redisTemplate;
    }


    @Test
    public void redisInsertTest() {

        ListOperations listOperations= this.redisOp.opsForList();
        for (int i = 0; i < 10000; i++) {
            String uuid=rdsUtil.generateOrderId();
            User user =new User();
            user.setId((long)i);
            user.setEmail("test@email.com");
            user.setAge(Integer.valueOf(uuid));
            listOperations.leftPush("users", user);
        }

    }

    @Test
    public void redisGetTest(){
       log.info (((User) this.redisOp.opsForValue().get("999920220222224013000001")).toString());
    }

    @Test
    public void redisGetListTest(){
        ListOperations listOperations = this.redisOp.opsForList();
        log.info (String.valueOf(listOperations.range("users", 10, 20)));
    }

    @Test
    public void redisDelete(){
        this.redisOp.delete("users");
    }

}
