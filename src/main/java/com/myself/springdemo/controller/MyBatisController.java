package com.myself.springdemo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myself.springdemo.bean.User;
import com.myself.springdemo.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mybatis")
@Slf4j
public class MyBatisController {

    private final SqlSessionTemplate st;
    private final SqlSession sqlSession;

    @Autowired
    private UserMapper userM ;

    @Autowired()
    public MyBatisController(SqlSessionTemplate st, @Qualifier("sqlsessionStandard") SqlSession sqlsessionStandard) {
        this.st = st;
        this.sqlSession = sqlsessionStandard;
    }


    @PostMapping("/user/new")
    public String createUser(@RequestBody List<User> users) throws Exception {
        UserMapper mapper = this.sqlSession.getMapper(UserMapper.class);
        for (User user : users) {
            log.info(String.valueOf(mapper.insertUser(user)));
        }
        List<BatchResult> resultList = mapper.flush();
        for (BatchResult re : resultList) {
            log.info(re.getSql());
            log.info(Arrays.toString(re.getUpdateCounts()));
        }
        return null;
    }


    @PostMapping("/user/{index}")
    public IPage<User> getUser(@PathVariable int index) {
        List<User> users = new ArrayList<>();
        if (index > 0) {
            QueryWrapper<User> query = new QueryWrapper<>();
            query.lt("id", index);
            Page<User> pages = new Page<>((index/3), 3);
           // UserMapper mapper = this.sqlSession.getMapper(UserMapper.class);
            IPage<User> ipages1 = this.userM.selectPage(pages, query);
            log.info(String.valueOf(ipages1.getTotal()+"__"+ipages1.getSize()+"__"+ipages1.getPages()));
            return ipages1;
        }
        return null;
    }

    @GetMapping("/users")
    public Map<Integer, User> getUserById() {
        log.info("age +2 ");
        this.sqlSession.update("updateUser", 2);
        return this.sqlSession.selectMap("selectUser", "id");
    }
}
