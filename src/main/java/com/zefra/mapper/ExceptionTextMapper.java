package com.zefra.mapper;

import com.zefra.pojo.Account;
import com.zefra.pojo.ExceptionContext;
import com.zefra.pojo.ExceptionTags;
import com.zefra.pojo.ExceptionTitle;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ExceptionTextMapper extends TextMapper{
    @Select("select * from etitle")
    List<ExceptionTitle> selectAllInTitle();//title对象
    @Select("select * from etitle where id in (select id from etags where (tag&(#{tag})))>0)")
    List<ExceptionTitle> selectAllInTitleLinkTagsId(long tag);
    @Select("<script>" + "select title from etitle where id in "
            + "<foreach item='id' index='index' collection='list' open='(' separator=',' close=')'>"
            + "#{id}"
            + "</foreach>"
            + "</script>")
    List<String> selectTitleInTitleLinkTagsId2(List<Integer> ids);
    @Select("select * from etags")
    List<ExceptionTags> selectAllInTags();//title对象
    @Select("select * from econtext")
    List<ExceptionContext> selectAllInContext();//title对象
    @Select("select title from etitle")
    List<String> selectTitleInTitle();
    @Select("select tag from etags")
    List<Long> selectTagInTags();
    @Select("select title from etitle where `id`=#{id} ")
    String selectTitleInTitleById(int id);
    @Select("select tag from etags where `id`=#{id}")
    Long selectTagInTagsById(int id);
    @Select("<script>" +"select * from etags where `id` in (select id from etitle where title in"+
            "<foreach item='title' index='index' collection='list' open='(' separator=',' close=')'>"
            + "#{title}"
            + "</foreach>)"
            + "</script>")
    List<ExceptionTags> selectInTagsByLinkTitle(List<String> titles);
    @Select("<script>"  + "select title from etitle where id in" +
            "<foreach item='id' index='index' collection='list' open='(' separator=',' close=')'>" +
            "#{id}"
            + "</foreach>"+
            " and locate(#{value}, title)>0"
         +"</script>")
    List<String> selectTitleFromTitleLikeValueInId(@Param("value")String value,@Param("list")List<Integer> ids);
    @Select("select context from econtext where `id`=#{id} ")
    String selectContextInContextById(int id);
    @Select("select `time` from etags")
    List<Float> selectTimeInTags();
    @Select("select * from etags where tag&(#{tag})>0 ")
    List<ExceptionTags> selectByBitAndInTags(long tag);
    @Select("select * from etags where tag&(#{tag})>0 and tag&(#{tag2})>0  ")
    List<ExceptionTags> selectByBitAnd2InTags(@Param("tag")long tag,@Param("tag2")long tag2);
    @Select("select id from etags where tag&(#{tag})>0")
    List<Integer> selectIdByBitAndInTags(long tag);
    @Select("select id from etitle where `title`=#{title}")
    List<Integer> selectIdFromTitleByTitle(String title);
    @Select("select context from econtext where `id`=#{id}")
    List<String> selectcontextFromContextById(int id);
    @Select("select title from etitle where locate(#{value}, title)>0")
    List<String> selectTitleFromTitleLikeValue(String value);
    @Update("update econtext set context = #{context} where id = #{id}")
    void updateContextinContextById(@Param("context")String context,@Param("id")int id);
    @Update("update etags set tag = #{tags} where id = #{id}")
    void updateTagsInTagsById(@Param("tags")long tags,@Param("id")int id);
    @Update("update etags set `time` = #{time} where id = #{id}")
    void updateTimeInTagsById(@Param("time")float time,@Param("id")int id);
    @Update("update etitle set title = #{title} where id = #{id}")
    void updateTitleInTitleById(@Param("title")String title,@Param("id")int id);
    @Insert("insert into etags(`tag`,`time`) values(#{tags},#{time})")
    void insertTableToTags(@Param("tags")long tags,@Param("time")float time);
    @Insert("insert into etitle(`title`) values(#{title})")
    void insertTableToTitle(@Param("title")String title);
    @Insert("insert into econtext(`context`) values(#{context})")
    void insertTableToContext(@Param("context")String context);
    @Delete("delete from exceptiontext where id =#{id} ")
    void deleteTableById(int id);
    @Delete("delete from etags where id =#{id} ")
    void deleteTagsById(int id);
    @Delete("delete from etitle where id =#{id} ")
    void deleteTitleById(int id);
    @Delete("delete from econtext where id =#{id} ")
    void deleteContextById(int id);
}
