package com.zefra.mapper;

import com.zefra.pojo.Account;
import com.zefra.pojo.ExceptionContext;
import com.zefra.pojo.ExceptionTags;
import com.zefra.pojo.ExceptionTitle;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ExceptionTextMapper {
    @Select("select * from etitle")
    List<ExceptionTitle> selectAllInEtitle();//title对象
    @Select("select * from etitle where id in (select id from etags where (tag&(#{tag})))>0)")
    List<ExceptionTitle> selectAllInEtitleLinkEtagsId(long tag);
    @Select("<script>" + "select title from etitle where id in "
            + "<foreach item='id' index='index' collection='list' open='(' separator=',' close=')'>"
            + "#{id}"
            + "</foreach>"
            + "</script>")
    List<String> selectTitleInEtitleLinkEtagsId2(List<Integer> ids);
    @Select("select * from etags")
    List<ExceptionTags> selectAllInTags();//title对象
    @Select("select * from econtext")
    List<ExceptionContext> selectAllInContext();//title对象
    @Select("select title from etitle")
    List<String> selectTitleInEtitle();
    @Select("select tag from etags")
    List<Long> selectTagInEtags();
    @Select("select time from etags")
    List<Float> selectTimeInEtags();
    @Select("select * from etags where tag&(#{tag})>0 ")
    List<ExceptionTags> selectByBitAndInEtags(long tag);
    @Select("select * from etags where tag&(#{tag})>0 and tag&(#{tag2})>0  ")
    List<ExceptionTags> selectByBitAnd2InEtags(@Param("tag")long tag,@Param("tag2")long tag2);
    @Select("select id from etags where tag&(#{tag})>0")
    List<Integer> selectIdByBitAndInEtags(long tag);
    @Insert("insert into etitle values(#{id},#{title})")
    void insertTableToTitle(@Param("id")int id,@Param("title")String title);
    @Insert("insert into  econtext values(#{id},#{context})")
    void insertTableToContext(@Param("id")int id,@Param("context")String context);
    @Insert("insert into etags values(#{id},#{tags},#{time})")
    void insertTableToTags(@Param("id")int id,@Param("tags")long tags,@Param("time")float time);
    @Delete("delete from exceptiontext where id =#{id} ")
    void deleteTableById(int id);
}
