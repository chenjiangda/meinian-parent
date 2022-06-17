package com.atguigu.dao;

import com.atguigu.pojo.Setmeal;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SetmealDao {
    void addSetmealAndTravelGroup(Map<String, Integer> map);

    void add(Setmeal setmeal);

    Page findPage(@Param("queryString") String queryString);

    List<Setmeal> getSetmeal();

    Setmeal findById(Integer id);

    Setmeal getSetmealById(Integer id);

    List<Map> getSetmealReport();
}
