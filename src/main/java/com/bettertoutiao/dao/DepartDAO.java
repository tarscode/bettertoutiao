package com.bettertoutiao.dao;

import com.bettertoutiao.model.Depart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by liuyang on 17/2/3.
 */
@Mapper
public interface DepartDAO {
    String TABLE_NAME = "depart";
    String SELECT_FIELDS = "id, name, type, content, head_url";

    @Select({"select", SELECT_FIELDS, " from ", TABLE_NAME, " order by id"})
    List<Depart> getAllDeparts();

    @Select({"select", SELECT_FIELDS, " from ", TABLE_NAME, " order by id limit #{0},#{1}"})
    List<Depart> getDeparts(int page, int size);
}
