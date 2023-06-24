package com.zefra.mapper;

import com.zefra.pojo.ExceptionContext;
import com.zefra.pojo.ExceptionTags;
import com.zefra.pojo.ExceptionTitle;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TextMapper {
    List<ExceptionTitle> selectAllInTitle();
    List<ExceptionTitle> selectAllInTitleLinkTagsId(long tag);
    List<String> selectTitleInTitleLinkTagsId2(List<Integer> ids);
    List<ExceptionTags> selectAllInTags();//title对象
    List<ExceptionContext> selectAllInContext();//title对象
    List<String> selectTitleInTitle();
    List<Long> selectTagInTags();
    String selectTitleInTitleById(int id);
    Long selectTagInTagsById(int id);
    List<ExceptionTags> selectInTagsByLinkTitle(List<String> titles);
    List<String> selectTitleFromTitleLikeValueInId(@Param("value")String value, @Param("list")List<Integer> ids);
    String selectContextInContextById(int id);
    List<Float> selectTimeInTags();
    List<ExceptionTags> selectByBitAndInTags(long tag);
    List<ExceptionTags> selectByBitAnd2InTags(@Param("tag")long tag,@Param("tag2")long tag2);
    List<Integer> selectIdByBitAndInTags(long tag);
    List<Integer> selectIdFromTitleByTitle(String title);
    List<String> selectcontextFromContextById(int id);
    List<String> selectTitleFromTitleLikeValue(String value);
    void updateContextinContextById(@Param("context")String context,@Param("id")int id);
    void updateTagsInTagsById(@Param("tags")long tags,@Param("id")int id);
    void updateTimeInTagsById(@Param("time")float time,@Param("id")int id);
    void updateTitleInTitleById(@Param("title")String title,@Param("id")int id);
    void insertTableToTags(@Param("tags")long tags,@Param("time")float time);
    void insertTableToTitle(@Param("title")String title);
    void insertTableToContext(@Param("context")String context);
    void deleteTableById(int id);
    void deleteTagsById(int id);
    void deleteTitleById(int id);
    void deleteContextById(int id);

}
