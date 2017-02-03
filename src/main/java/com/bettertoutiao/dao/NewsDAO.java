package com.bettertoutiao.dao;

import com.bettertoutiao.model.News;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by liuyang on 17/1/1.
 */
@Mapper
public interface NewsDAO {
    String TABLE_NAME = "news";
    String SELECT_FIELDS = "id, title, type, depart, channel, newsdate, content, url";

    @Select({"select", SELECT_FIELDS, " from ", TABLE_NAME, " order by newsdate desc limit ${0},#{1}"})
    List<News> selectByPage(int page, int size);

    List<News> selectByLimit(@Param("page") int page, @Param("size") int size);

}
