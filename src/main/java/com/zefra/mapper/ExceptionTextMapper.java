package com.zefra.mapper;

import com.zefra.pojo.Account;
import com.zefra.pojo.ExceptionText;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ExceptionTextMapper {
    @Select("select * from exceptiontext")
    List<ExceptionText> selectAll();//获取所有的对象
}
