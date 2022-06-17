package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Address;
import com.atguigu.service.AddressService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Reference
    AddressService addressService;


    @RequestMapping("/findAllMaps")
    public Map findAllMaps(){

        //根据前端ajax中的代码来封装对应的数据进行返回，返回的数据就是success的data（map格式到前端转化成了json）

        Map map = new HashMap();

        List<Address> list = addressService.findAllMaps();

        List<Map> gridnMaps = new ArrayList(); //标记地址的经纬度

        List<Map> nameMaps = new ArrayList<>(); //标记地址名称

        for (Address address : list) {
            //nameMaps中第X个名字对应gridnMaps中第X对经纬度

            String addressName = address.getAddressName();
            Map<String,String> mapName = new HashMap<>();
            mapName.put("addressName",addressName);
            nameMaps.add(mapName);

            Map<String,String> gridnMap = new HashMap<>();
            gridnMap.put("lng",address.getLng());
            gridnMap.put("lat",address.getLat());
            gridnMaps.add(gridnMap);
        }

        map.put("gridnMaps",gridnMaps);
        map.put("nameMaps",nameMaps);

        //不封装Result了，前端map.html页面中的回调函数success中拿到的data就是这边传回去的map转化成的json
        return map;

    }


    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = addressService.findPage(queryPageBean);
        return pageResult;
    }

    @RequestMapping("/addAddress")
    public Result addAddress(@RequestBody Address address){
        try {
            addressService.add(address);
            return new Result(true, MessageConstant.ADD_ADDRESS_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_ADDRESS_FAIL);
        }
    }

    @RequestMapping("/deleteById")
    public Result deleteById(Integer id){
        try {
            addressService.deleteById(id);
            return new Result(true,MessageConstant.DELETE_ADDRESS_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_ADDRESS_FAIL);
        }
    }


}
