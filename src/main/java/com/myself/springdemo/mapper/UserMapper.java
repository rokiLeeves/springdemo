package com.myself.springdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myself.springdemo.bean.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.executor.BatchResult;

import java.util.List;
import java.util.Map;


/**
 * @author 45319
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {


    @Select("SELECT * FROM user WHERE id = #{id}")
    User findUserByID(int id);

    @Select("SELECT * FROM user order by id desc limit #{skip},#{num}")
    List<User> findUserList(int num, int skip);

    //TODO:学习使用uuid
    @Insert("insert into user(id,name,age,email) values(#{id},#{name},#{age},#{email})")
    @SelectKey(statement = "select max(id)+1 from user order by age asc limit 1", keyProperty = "id", before = true, resultType = long.class)
    int insertUser(User user);

    @Flush
    List<BatchResult> flush();

    @Update("update user set age=age+#{ageAdd} where age >  #{age} ")
    int updateUser(int ageAdd);

    @Select("SELECT * FROM user")
    @MapKey("id")
    Map<Integer, User> selectAllUser();

    @Select("SELECT name,age,email,creationdate FROM user")
    List<User> selectUser(Page<User> page);


}
