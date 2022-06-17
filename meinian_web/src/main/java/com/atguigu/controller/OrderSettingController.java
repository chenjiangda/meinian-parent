package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.pojo.OrderSetting;
import com.atguigu.service.OrderSettingService;
import com.atguigu.util.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orderSetting")
public class OrderSettingController {

    @Reference
    OrderSettingService orderSettingService;

    @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile") MultipartFile excelFile){//形参名和传过来的name对应的话@RequestParam可不写
        //这个工具类的方法已经把excel文件里的数据一条一条读成了一个list了
        try {
            List<String[]> list = POIUtils.readExcel(excelFile);

            //接下来要把excel中读出来的数据list保存到数据库里面
            List<OrderSetting> listData = new ArrayList<>();
            for (String[] strArray : list) {
                //dateStr的格式类似:2020/12/01
                String dateStr = strArray[0];
                String numberStr = strArray[1];
                OrderSetting orderSetting = new OrderSetting();
                orderSetting.setOrderDate(new Date(dateStr));
                orderSetting.setNumber(Integer.parseInt(numberStr));
//                orderSetting.setReservations(0);
                //此时每个ordersetting的date属性值是类似这种格式的：Tue Dec 01 00:00:00 CST 2020
                listData.add(orderSetting);
            }

            orderSettingService.addBatch(listData);

            return new Result(true, MessageConstant.UPLOAD_SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.UPLOAD_FAIL);
        }
    }


    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String date){
        //为什么是Map，因为要返回leftobj需要的那种数据格式，配合组件能直接作用于前端界面

        //前台要的数据格式
        /*this.leftobj = [
            { date: 1, number: 120, reservations: 1 },
            { date: 3, number: 120, reservations: 1 },
            { date: 4, number: 120, reservations: 120 },
            { date: 6, number: 120, reservations: 1 },
            { date: 8, number: 120, reservations: 1 }
        ];*/

        try {
            List<Map> list = orderSettingService.getOrderSettingByMonth(date);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }

    /**
     * 接数据
     *       var param = {
     *           value:value,
     *           orderDate:str
     *       }
     * @param map
     * @return
     */
    @RequestMapping("/editNumberByOrderDate")
    public Result editNumberByOrderDate(@RequestBody Map map){ //map是万能的，直接用map来接,接到前端传过来的param参数

        try {
            orderSettingService.editNumberByOrderDate(map);
            return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }

    }

}
