package com.zefra.mapper;

import com.zefra.pojo.Account;
import com.zefra.pojo.ExceptionText;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ExceptionTextMapper {
    @Select("select * from exceptiontext")
    List<ExceptionText> selectAll();//获取所有的对象
    @Insert("insert into exceptiontext(title,tags,time,context) values(#{title},#{tags},#{time},#{context})")
    void insertTable(@Param("title")String title, @Param("tags")String tags, @Param("time")float time,@Param("context")String context);
    @Delete("delete from exceptiontext where id =#{id} ")
    void deleteTableById(int id);
}
