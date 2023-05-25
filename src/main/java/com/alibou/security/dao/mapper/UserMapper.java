package com.alibou.security.dao.mapper;

import com.alibou.security.dao.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author mqz
 */
@Mapper
public interface UserMapper {


    @Insert("insert into _user(firstname, lastname, password, role, email) values (#{firstname}, #{lastname}, #{password}, #{role}, #{email})")
    void insert(User user);


    @Select("select * from _user where email = #{email}")
    User selectOne(String email);
}
