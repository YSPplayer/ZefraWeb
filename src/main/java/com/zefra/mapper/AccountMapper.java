package com.zefra.mapper;

import com.zefra.pojo.Account;
import org.apache.ibatis.annotations.Param;

public interface AccountMapper {
 Account selectByEmail(String email);//查询指定账号的对象
 Account selectByName(String name);//查询指定账户名的对象
 String selectPasswordByName(String name);//查询指定用户的密码
  void insertTable(@Param("email")String email,@Param("name")String name,@Param("password")String password);
}
