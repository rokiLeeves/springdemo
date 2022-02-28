package com.myself.springdemo;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myself.springdemo.bean.User;
import com.myself.springdemo.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@FixMethodOrder(MethodSorters.JVM)
@Slf4j
@SpringBootTest
class MpPageAndVersonTets {

//
    @Autowired
    private UserMapper userMapper;

    @Autowired
    @Qualifier("sqlsessionBatch")
    private SqlSession sqlSession;


    @Autowired
    @Qualifier("sqlsessionStandard")
    private SqlSession sqlOp;


    @Test
    void inserVersionUser(){
        log.info("First");
        User[] users=new User[10];
        for (int i = 0; i < 10; i++) {
            User user=new User();
            user.setAge(i);
            user.setName("user"+i);
            users[i]=user;
        }
        UserMapper mapper = this.sqlOp.getMapper(UserMapper.class);
        for (User user : users) {
           mapper.insert(user);
        }
//        List<BatchResult> resultList = mapper.flush();
//        for (BatchResult re : resultList) {
//            log.info("getUpdateCounts:"+ Arrays.toString(re.getUpdateCounts()));
//        }

    }



    @Test
    void updateUserWithVersionInTrue(){
        log.info("Second");
        QueryWrapper<User> quer=new QueryWrapper<>();
        quer.orderByDesc("id");
        quer.last("limit 1");
        User u=this.userMapper.selectOne(quer);
        log.info("Version:"+u.getVersion());
        u.setName("updatename");
        this.userMapper.updateById(u);
        User u2=this.userMapper.selectOne(quer);
        log.info("update Version:"+u2.getVersion());
    }



    @Test
    void updateUserWithVersionInFalse(){
        log.info("Third");
        QueryWrapper<User> quer=new QueryWrapper<>();
        quer.orderByDesc("id");
        quer.last("limit 1");
        User u=this.userMapper.selectOne(quer);
        User u2=this.userMapper.selectOne(quer);
        log.info("Version:"+u.getVersion()+"|"+u2.getVersion());
        u.setName("updatename");
        u2.setName("updatename2");
        int num1=this.userMapper.updateById(u);
        int num2=this.userMapper.updateById(u2);
        log.info("UpdateResult:"+num1+"|"+num2);
        Assertions.assertEquals(1,num1);
        Assertions.assertEquals(1,num2);
    }






    @Disabled
    @Test
    public void testSelect() {
        log.info("----- selectAll method test ------");
        QueryWrapper<User> query = new QueryWrapper<>();
        query.lt("id", 9);
        Page<User> pages = new Page<>(3, 3);
        // UserMapper mapper = this.sqlSession.getMapper(UserMapper.class);
        Page<User> ipages1 = this.userMapper.selectPage(pages, query);
        log.info(String.valueOf(ipages1.getTotal()+"__"+ipages1.getSize()+"__"+ipages1.getPages()));



    }
}
