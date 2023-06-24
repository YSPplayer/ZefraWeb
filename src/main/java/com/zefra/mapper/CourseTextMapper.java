package com.zefra.mapper;

import com.zefra.pojo.ExceptionContext;
import com.zefra.pojo.ExceptionTags;
import com.zefra.pojo.ExceptionTitle;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CourseTextMapper extends TextMapper{
    @Select("select * from ctitle")
    List<ExceptionTitle> selectAllInTitle();//title对象
    @Select("select * from ctitle where id in (select id from ctags where (tag&(#{tag})))>0)")
    List<ExceptionTitle> selectAllInTitleLinkTagsId(long tag);
    @Select("<script>" + "select title from ctitle where id in "
            + "<foreach item='id' index='index' collection='list' open='(' separator=',' close=')'>"
            + "#{id}"
            + "</foreach>"
            + "</script>")
    List<String> selectTitleInTitleLinkTagsId2(List<Integer> ids);
    @Select("select * from ctags")
    List<ExceptionTags> selectAllInTags();//title对象
    @Select("select * from ccontext")
    List<ExceptionContext> selectAllInContext();//title对象
    @Select("select title from ctitle")
    List<String> selectTitleInTitle();
    @Select("select tag from ctags")
    List<Long> selectTagInTags();
    @Select("select title from ctitle where `id`=#{id} ")
    String selectTitleInTitleById(int id);
    @Select("select tag from ctags where `id`=#{id}")
    Long selectTagInTagsById(int id);
    @Select("<script>" +"select * from ctags where `id` in (select id from ctitle where title in"+
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
    List<String> selectTitleFromTitleLikeValueInId(@Param("value")String value, @Param("list")List<Integer> ids);
    @Select("select context from ccontext where `id`=#{id} ")
    String selectContextInContextById(int id);
    @Select("select `time` from ctags")
    List<Float> selectTimeInTags();
    @Select("select * from ctags where tag&(#{tag})>0 ")
    List<ExceptionTags> selectByBitAndInTags(long tag);
    @Select("select * from ctags where tag&(#{tag})>0 and tag&(#{tag2})>0  ")
    List<ExceptionTags> selectByBitAnd2InTags(@Param("tag")long tag,@Param("tag2")long tag2);
    @Select("select id from ctags where tag&(#{tag})>0")
    List<Integer> selectIdByBitAndInTags(long tag);
    @Select("select id from ctitle where `title`=#{title}")
    List<Integer> selectIdFromTitleByTitle(String title);
    @Select("select context from ccontext where `id`=#{id}")
    List<String> selectcontextFromContextById(int id);
    @Select("select title from ctitle where locate(#{value}, title)>0")
    List<String> selectTitleFromTitleLikeValue(String value);
    @Update("update ccontext set context = #{context} where id = #{id}")
    void updateContextinContextById(@Param("context")String context,@Param("id")int id);
    @Update("update ctags set tag = #{tags} where id = #{id}")
    void updateTagsInTagsById(@Param("tags")long tags,@Param("id")int id);
    @Update("update ctags set `time` = #{time} where id = #{id}")
    void updateTimeInTagsById(@Param("time")float time,@Param("id")int id);
    @Update("update ctitle set title = #{title} where id = #{id}")
    void updateTitleInTitleById(@Param("title")String title,@Param("id")int id);
    @Insert("insert into ctags(`tag`,`time`) values(#{tags},#{time})")
    void insertTableToTags(@Param("tags")long tags,@Param("time")float time);
    @Insert("insert into ctitle(`title`) values(#{title})")
    void insertTableToTitle(@Param("title")String title);
    @Insert("insert into ccontext(`context`) values(#{context})")
    void insertTableToContext(@Param("context")String context);
    @Delete("delete from exceptiontext where id =#{id} ")
    void deleteTableById(int id);
    @Delete("delete from ctags where id =#{id} ")
    void deleteTagsById(int id);
    @Delete("delete from ctitle where id =#{id} ")
    void deleteTitleById(int id);
    @Delete("delete from ccontext where id =#{id} ")
    void deleteContextById(int id);
}
