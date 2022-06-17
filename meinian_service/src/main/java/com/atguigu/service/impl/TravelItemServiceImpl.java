package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.TravelItemDao;
import com.atguigu.entity.PageResult;
import com.atguigu.pojo.TravelItem;
import com.atguigu.service.TravelItemService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// dubbo的Service注解表示要把它发布成一个服务，注册到zk中
@Service(interfaceClass = TravelItemService.class)
@Transactional // 声明式事务，所有方法都加上事务
public class TravelItemServiceImpl implements TravelItemService {

    @Autowired // Dao不是启动的远程服务，就是一个java工程，所以直接用Autowired注入即可
    TravelItemDao travelItemDao;

    //ctrl+i实现方法
    @Override
    public void add(TravelItem travelItem) {
        travelItemDao.add(travelItem);
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        //用分页插件实现分页查询

        //开启分页功能(自动帮我们在sql上加limit)(第一个参数是查第几页，第二个参数是每页几条记录)
        //底层生成 limit ?,?     第一个问号表示开始索引，第二个问号表示查询条数
        //底层生成 limit (currentPage-1)*pageSize,pageSize
        PageHelper.startPage(currentPage,pageSize);

        //queryString是查询条件，这边只做有一个查询条件的（或者没有查询条件，也就是queryString为null）
        //page是查询到的当前页面的数据,还封装了很多属性
        Page page = travelItemDao.findPage(queryString);


        //page.getTotal()是总记录数，page.getResult()是当前页的数据集合
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void delete(Integer id) { //自由行id
        //查自由行关联表中是否存在关联数据，如果存在，就抛异常，不进行删除
        long count = travelItemDao.findCountByTravelitem(id);

        if (count>0){
            //存在关联数据
            throw new RuntimeException("删除自由行失败，因为存在关联数据。先解除关系，再删除");//异常抛给了调用它的TravelItemController，在那边trycatch处理
        }

        travelItemDao.delete(id);
    }

    @Override
    public TravelItem getById(Integer id) {
        return travelItemDao.getById(id);
    }

    @Override
    public void edit(TravelItem travelItem) {
        travelItemDao.edit(travelItem);
    }

    @Override
    public List<TravelItem> findAll() {
        return travelItemDao.findAll();
    }
}
