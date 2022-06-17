package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.TravelGroupDao;
import com.atguigu.entity.PageResult;
import com.atguigu.pojo.TravelGroup;
import com.atguigu.service.TravelGroupService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = TravelGroupService.class)
@Transactional
public class TravelGroupServiceImpl implements TravelGroupService {

    @Autowired
    TravelGroupDao travelGroupDao;


    @Override
    public void add(Integer[] travelItemIds, TravelGroup travelGroup) {
        travelGroupDao.add(travelGroup); // id是数据库自己分配的（自增主键）
        Integer travelGroupId = travelGroup.getId(); // 表单传过来的时候没有id，所以在添加的时候需要获取到增加后的id放在travelGroup中
        // 如果自由行勾选的id数组travelItemIds值为[1,2,3]
        // 对应的一个跟团游travelGroup的id为1，那么要往中间表t_travelgroup_travelitem中存三条记录1,1    1,2     1,3
        setTravelGroupAndTravelItem(travelGroupId,travelItemIds);
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);

        Page page = travelGroupDao.findPage(queryString);
        //page.getTotal()是总记录数，page.getResult()是这一页的数据集合
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public TravelGroup getById(Integer id) {
        return travelGroupDao.getById(id);
    }

    @Override
    public List<Integer> getTravelitemIdsByTravelGroupId(Integer travelGroupId) {
        return travelGroupDao.getTravelitemIdsByTravelGroupId(travelGroupId);
    }

    @Override
    public void edit(Integer[] travelItemIds, TravelGroup travelGroup) {
        travelGroupDao.edit(travelGroup);
        Integer travelGroupId = travelGroup.getId();

        //先删除对应的中间表关联数据
        travelGroupDao.delete(travelGroupId);

        //再重新增加对应的中间表关联数据
        setTravelGroupAndTravelItem(travelGroupId,travelItemIds);

    }

    @Override
    public List<TravelGroup> findAll() {
        return travelGroupDao.findAll();
    }

    private void setTravelGroupAndTravelItem(Integer travelGroupId, Integer[] travelItemIds) {
        if (travelItemIds != null && travelItemIds.length>0){
            //准备dao层需要的参数，利用Map集合作为参数传递数据

            for (Integer travelItemId : travelItemIds) {
                Map<String,Integer> paramData = new HashMap<>();
                paramData.put("travelGroupId",travelGroupId);
                paramData.put("travelItemId",travelItemId);
                travelGroupDao.addTravelGroupAndTravelItem(paramData);
            }
        }
    }

}
