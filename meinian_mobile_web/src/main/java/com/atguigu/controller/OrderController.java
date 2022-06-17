package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisMessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    OrderService orderService;

    @Autowired
    JedisPool jedisPool;

    /**
     * 由于页面表单的数据来自多张表的数据操作，不用pojo对象接收不完整，就用map接收
     * @param map
     * @return
     */
    @RequestMapping("/saveOrder")
    public Result saveOrder(@RequestBody Map map){
        try {
            //map = {setmealId=3, idCard=330227199908222016, sex=1, name=test, validateCode=1234, telephone=18888888888, orderDate=2022-06-09}
//            System.out.println("map = " + map);

            /*
            点击提交预约, 把用户信息 提交到服务器
            在Controller里面
                  - 获得用户信息
                  - 校验验证码(redis里面存的和用户输入的比较)
                  - 调用业务, 进行预约, 响应

             */

            String telephone = (String) map.get("telephone");
            String validateCode = (String) map.get("validateCode");
            String redisCode = jedisPool.getResource().get(telephone+ RedisMessageConstant.SENDTYPE_ORDER);

            if (redisCode == null || !redisCode.equals(validateCode)){
                //验证码输错了或者过期了
                return new Result(false, MessageConstant.VALIDATECODE_ERROR);
            }
            Result result = orderService.saveOrder(map);

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDER_FAIL);
        }

    }


    /**
     * 需要封装返回的数据
     * <p>会员姓名：{{orderInfo.member}}</p>
     * <p>旅游套餐：{{orderInfo.setmeal}}</p>
     * <p>旅游日期：{{orderInfo.orderDate}}</p>
     * <p>预约类型：{{orderInfo.orderType}}</p>
     * @param orderId 订单order的id
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(Integer orderId){

        Map<String, Object> map = orderService.findById(orderId);
        return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);

    }
}
