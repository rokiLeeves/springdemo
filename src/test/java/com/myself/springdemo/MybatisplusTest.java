package com.myself.springdemo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myself.springdemo.bean.User;
import com.myself.springdemo.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class MybatisplusTest {


    @Qualifier("normalsqlSession")
    @Autowired
    private SqlSession sq;


    @Test
    public void mpTest() {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.lt("id", 10);
        query.orderByAsc("id");
        Page<User> page = new Page<>(2, 5);
        UserMapper userMapper = this.sq.getMapper(UserMapper.class);
        System.out.printf(String.valueOf(userMapper.selectPage(page, query)));
    }
}
