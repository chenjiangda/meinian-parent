package com.atguigu.dao;

import com.atguigu.pojo.TravelItem;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TravelItemDao {
    void add(TravelItem travelItem);

    /*mybatisx有时会爆红，没事*/
    Page findPage(@Param("queryString") String queryString);

    void delete(Integer id);

    TravelItem getById(Integer id);

    void edit(TravelItem travelItem);

    List<TravelItem> findAll();

    long findCountByTravelitem(Integer id);

    /**
     * 版主封装跟团游的travelItem属性的方法
     * @param id 跟团游的id
     * @return 这个跟团游关联的多个自由行数据
     */
    List<TravelItem> findTravelItemById(Integer id);
}
