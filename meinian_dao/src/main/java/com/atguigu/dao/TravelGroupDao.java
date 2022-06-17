package com.atguigu.dao;

import com.atguigu.pojo.TravelGroup;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TravelGroupDao {
    void add(TravelGroup travelGroup);

    void addTravelGroupAndTravelItem(Map<String, Integer> paramData);

    Page findPage(@Param("queryString") String queryString222);

    TravelGroup getById(@Param("id") Integer id);

    List<Integer> getTravelitemIdsByTravelGroupId(@Param("travelGroupId") Integer travelGroupId);

    void edit(TravelGroup travelGroup);

    void delete(Integer travelGroupId);

    List<TravelGroup> findAll();

    /**
     * 帮助封装套餐对象的travelGroups属性的方法
     * @param id 套餐的id
     * @return 这个套餐关联的多个跟团游的数据
     */
    List<TravelGroup> findTravelGroupById(Integer id);

}
