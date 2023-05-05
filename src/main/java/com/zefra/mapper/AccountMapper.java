package com.zefra.mapper;

import com.zefra.pojo.Account;
import com.zefra.pojo.ExceptionTitle;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AccountMapper {
 @Select("select * from account")
 List<Account> selectAll();
 @Select("select * from account where email = #{email}")
 Account selectByEmail(String email);//查询指定账号的对象
 @Select(" select * from account where name = #{name}")
 Account selectByName(String name);//查询指定账户名的对象
 @Select("select password from account where name = #{name}")
 String selectPasswordByName(String name);//查询指定用户的密码
 @Insert("insert into account values(#{email},#{name},#{password})")
  void insertTable(@Param("email")String email,@Param("name")String name,@Param("password")String password);
}
