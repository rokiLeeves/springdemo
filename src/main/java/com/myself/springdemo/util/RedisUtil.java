package com.myself.springdemo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
public class RedisUtil {

    private RedisTemplate redisOp;

    @Value("${redis.uuidprefix}")
    private  String uuidprefix;

    @Autowired
    public  RedisUtil(RedisTemplate<String, Serializable> rt){
        this.redisOp=rt;
    }


    public long generate(String key,Date expireTime){
        RedisAtomicLong counter = new RedisAtomicLong(key, redisOp.getConnectionFactory());
        Long expire = counter.getExpire();
        if(expire==-1){
            counter.expireAt(expireTime);
        }
        return counter.incrementAndGet();
    }



    public String generateOrderId() {
        LocalDateTime now = LocalDateTime.now();
        String orderIdPrefix = uuidprefix+getOrderIdPrefix(now);
        String orderId = orderIdPrefix+String.format("%1$06d", generate(orderIdPrefix,getExpireAtTime(now)));
        return orderId;
    }

    public static String getOrderIdPrefix(LocalDateTime now){
        return   now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

        public Date getExpireAtTime(LocalDateTime now){
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = now.plusSeconds(20);
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        Date date = Date.from(zdt.toInstant());
        return date;
    }


}
