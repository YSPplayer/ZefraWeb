package com.zefra.mapper;

import com.zefra.pojo.ExceptionContext;
import com.zefra.pojo.ExceptionTags;
import com.zefra.pojo.ExceptionTitle;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ToolTextMapper extends TextMapper{
    @Select("select * from dtitle")
    List<ExceptionTitle> selectAllInTitle();//title对象
    @Select("select * from dtitle where id in (select id from dtags where (tag&(#{tag})))>0)")
    List<ExceptionTitle> selectAllInTitleLinkTagsId(long tag);
    @Select("<script>" + "select title from dtitle where id in "
            + "<foreach item='id' index='index' collection='list' open='(' separator=',' close=')'>"
            + "#{id}"
            + "</foreach>"
            + "</script>")
    List<String> selectTitleInTitleLinkTagsId2(List<Integer> ids);
    @Select("select * from dtags")
    List<ExceptionTags> selectAllInTags();//title对象
    @Select("select * from dcontext")
    List<ExceptionContext> selectAllInContext();//title对象
    @Select("select title from dtitle")
    List<String> selectTitleInTitle();
    @Select("select tag from dtags")
    List<Long> selectTagInTags();
    @Select("select title from dtitle where `id`=#{id} ")
    String selectTitleInTitleById(int id);
    @Select("select tag from dtags where `id`=#{id}")
    Long selectTagInTagsById(int id);
    @Select("<script>" +"select * from dtags where `id` in (select id from dtitle where title in"+
            "<foreach item='title' index='index' collection='list' open='(' separator=',' close=')'>"
            + "#{title}"
            + "</foreach>)"
            + "</script>")
    List<ExceptionTags> selectInTagsByLinkTitle(List<String> titles);
    @Select("<script>"  + "select title from dtitle where id in" +
            "<foreach item='id' index='index' collection='list' open='(' separator=',' close=')'>" +
            "#{id}"
            + "</foreach>"+
            " and locate(#{value}, title)>0"
            +"</script>")
    List<String> selectTitleFromTitleLikeValueInId(@Param("value")String value, @Param("list")List<Integer> ids);
    @Select("select context from dcontext where `id`=#{id} ")
    String selectContextInContextById(int id);
    @Select("select `time` from dtags")
    List<Float> selectTimeInTags();
    @Select("select * from dtags where tag&(#{tag})>0 ")
    List<ExceptionTags> selectByBitAndInTags(long tag);
    @Select("select * from dtags where tag&(#{tag})>0 and tag&(#{tag2})>0  ")
    List<ExceptionTags> selectByBitAnd2InTags(@Param("tag")long tag,@Param("tag2")long tag2);
    @Select("select id from dtags where tag&(#{tag})>0")
    List<Integer> selectIdByBitAndInTags(long tag);
    @Select("select id from dtitle where `title`=#{title}")
    List<Integer> selectIdFromTitleByTitle(String title);
    @Select("select context from dcontext where `id`=#{id}")
    List<String> selectcontextFromContextById(int id);
    @Select("select title from dtitle where locate(#{value}, title)>0")
    List<String> selectTitleFromTitleLikeValue(String value);
    @Update("update dcontext set context = #{context} where id = #{id}")
    void updateContextinContextById(@Param("context")String context,@Param("id")int id);
    @Update("update dtags set tag = #{tags} where id = #{id}")
    void updateTagsInTagsById(@Param("tags")long tags,@Param("id")int id);
    @Update("update dtags set `time` = #{time} where id = #{id}")
    void updateTimeInTagsById(@Param("time")float time,@Param("id")int id);
    @Update("update dtitle set title = #{title} where id = #{id}")
    void updateTitleInTitleById(@Param("title")String title,@Param("id")int id);
    @Insert("insert into dtags(`tag`,`time`) values(#{tags},#{time})")
    void insertTableToTags(@Param("tags")long tags,@Param("time")float time);
    @Insert("insert into dtitle(`title`) values(#{title})")
    void insertTableToTitle(@Param("title")String title);
    @Insert("insert into dcontext(`context`) values(#{context})")
    void insertTableToContext(@Param("context")String context);
    @Delete("delete from exceptiontext where id =#{id} ")
    void deleteTableById(int id);
    @Delete("delete from dtags where id =#{id} ")
    void deleteTagsById(int id);
    @Delete("delete from dtitle where id =#{id} ")
    void deleteTitleById(int id);
    @Delete("delete from dcontext where id =#{id} ")
    void deleteContextById(int id);
}
