package com.zefra.mapper;

import com.zefra.pojo.Account;
import com.zefra.pojo.ExceptionContext;
import com.zefra.pojo.ExceptionTags;
import com.zefra.pojo.ExceptionTitle;
import org.apache.ibatis.annotations.*;

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
    @Select("select title from etitle where `id`=#{id} ")
    String selectTitleInEtitleById(int id);
    @Select("select tag from etags where `id`=#{id}")
    Long selectTagInEtagsById(int id);
    @Select("select context from econtext where `id`=#{id} ")
    String selectContextInEcontextById(int id);
    @Select("select `time` from etags")
    List<Float> selectTimeInEtags();
    @Select("select * from etags where tag&(#{tag})>0 ")
    List<ExceptionTags> selectByBitAndInEtags(long tag);
    @Select("select * from etags where tag&(#{tag})>0 and tag&(#{tag2})>0  ")
    List<ExceptionTags> selectByBitAnd2InEtags(@Param("tag")long tag,@Param("tag2")long tag2);
    @Select("select id from etags where tag&(#{tag})>0")
    List<Integer> selectIdByBitAndInEtags(long tag);
    @Select("select id from etitle where `title`=#{title}")
    List<Integer> selectIdFromEtitleByTitle(String title);
    @Select("select context from econtext where `id`=#{id}")
    List<String> selectcontextFromEcontextById(int id);
    @Update("update econtext set context = #{context} where id = #{id}")
    void updateContextinEcontextById(@Param("context")String context,@Param("id")int id);
    @Update("update etags set tag = #{tags} where id = #{id}")
    void updateTagsInEtagsById(@Param("tags")long tags,@Param("id")int id);
    @Update("update etags set `time` = #{time} where id = #{id}")
    void updateTimeInEtagsById(@Param("time")float time,@Param("id")int id);
    @Update("update etitle set title = #{title} where id = #{id}")
    void updateTitleInEtitleById(@Param("title")String title,@Param("id")int id);
    @Insert("insert into etags(`tag`,`time`) values(#{tags},#{time})")
    void insertTableToTags(@Param("tags")long tags,@Param("time")float time);
    @Insert("insert into etitle(`title`) values(#{title})")
    void insertTableToETitle(@Param("title")String title);
    @Insert("insert into econtext(`context`) values(#{context})")
    void insertTableToContext(@Param("context")String context);
    @Delete("delete from exceptiontext where id =#{id} ")
    void deleteTableById(int id);
    @Delete("delete from etags where id =#{id} ")
    void deleteEtagsById(int id);
    @Delete("delete from etitle where id =#{id} ")
    void deleteEtitleById(int id);
    @Delete("delete from econtext where id =#{id} ")
    void deleteEcontextById(int id);
}
