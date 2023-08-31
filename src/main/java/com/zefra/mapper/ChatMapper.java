package com.zefra.mapper;

import com.zefra.pojo.Chat;
import com.zefra.pojo.ExceptionTitle;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.sql.Timestamp;
import java.util.List;

public interface ChatMapper {
    @Insert("insert into chat(`url`,`name`,`time`,`txt`) values(#{url},#{name},#{time},#{txt})")
    void insertChat(@Param("url")String url,@Param("name")String name
            , @Param("time") Timestamp time, @Param("txt")String txt);
    @Select("select * from chat limit 5 offset #{index}")
    List<Chat> selectChatLimit(int index);
    @Select("select * from chat")
    List<Chat> selectChat();
    @Select("select id from chat order by id desc limit 1;")
    List<Integer> selectLastId();
    @Delete("delete from chat where id =#{id} ")
    void deleteChatById(int id);
}
