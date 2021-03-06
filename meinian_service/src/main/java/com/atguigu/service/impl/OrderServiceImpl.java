package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.constant.MessageConstant;
import com.atguigu.dao.MemberDao;
import com.atguigu.dao.OrderDao;
import com.atguigu.dao.OrderSettingDao;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Member;
import com.atguigu.pojo.Order;
import com.atguigu.pojo.OrderSetting;
import com.atguigu.service.OrderService;
import com.atguigu.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    MemberDao memberDao;

    @Autowired
    OrderSettingDao orderSettingDao;

    /**
     * 1. 判断当前的日期是否可以预约(根据orderDate查询t_ordersetting, 能查询出来可以预约;查询不出来,不能预约)
     * 2. 判断当前日期是否预约已满(判断reservations（已经预约人数）是否等于number（最多预约人数）)
     * 3. 判断是否是会员(根据手机号码查询t_member)
     *     - 如果是会员(能够查询出来), 防止重复预约(根据member_id,orderDate,setmeal_id查询t_order)
     *     - 如果不是会员(不能够查询出来) ,自动注册为会员(直接向t_member插入一条记录)
     * 4.进行预约
     *       - 向t_order表插入一条记录
     *       - t_ordersetting表里面预约的人数reservations+1
     * @param map
     * @return
     */
    @Override
    public Result saveOrder(Map map) throws Exception {

        //1. 判断当前的日期是否可以预约(根据orderDate查询t_ordersetting, 能查询出来可以预约;查询不出来,不能预约)
        String orderDate = (String) map.get("orderDate");
        Integer setmealId = Integer.parseInt((String) map.get("setmealId"));


        //其实我感觉直接用字符串查就可以了啊，因为这边的字符串就是'2022-06-09'这种形式，我在sqlyog中试了，
        // 虽然数据库中对应表的字段是date类型的但是用字符串类型参数也能查出来的，但是老师还是把String转化成了Date

        Date date = DateUtils.parseString2Date(orderDate);

        OrderSetting orderSetting = orderSettingDao.getOrderSettingByOrderDate(date);

        if (orderDate==null){
            //预约设置中无顾客要预约的日子，所以不能预约
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }else {
            //2. 判断当前日期是否预约已满(判断reservations（已经预约人数）是否等于number（最多预约人数）)
            int number = orderSetting.getNumber();
            int reservations = orderSetting.getReservations();
            if (reservations>=number){
                //对应日期预约人数已满
                return new Result(false, MessageConstant.ORDER_FAIL);
            }

        }


        //3. 判断是否是会员(根据手机号码查询t_member)
        //    - 如果是会员(能够查询出来), 防止重复预约(根据member_id,orderDate,setmeal_id查询t_order)
        //    - 如果不是会员(不能够查询出来) ,自动注册为会员(直接向t_member插入一条记录)
        String telephone = (String) map.get("telephone");
        Member member = memberDao.getMemberByTelephone(telephone);
        if (member == null){
            //不存在就快速注册
            member = new Member();
            member.setName((String)map.get("name"));
            member.setSex((String)map.get("sex"));
            member.setPhoneNumber((String)map.get("telephone"));
            member.setIdCard((String)map.get("idCard"));
            member.setRegTime(new Date()); // 会员注册时间，当前时间
            //快速注册不用设置后面几项数据
            memberDao.add(member);//要进行主键回显

        }else {
            //检查是否重复预约
            //这个人是否在这一天已经预约了这个套餐

            //将findOrderByCondition方法封装为通用方法，可以实现动态SQL查询（不管按照啥查询条件查出来几条都放入list中）
            //查询条件封装成Order对象
            Order orderParam = new Order();
            orderParam.setOrderDate(date);
            orderParam.setSetmealId(setmealId);
            orderParam.setMemberId(member.getId());
            List<Order> orderList = orderDao.findOrderByCondition(orderParam);

            if (orderList!=null && orderList.size()>0){
                return new Result(false, MessageConstant.HAS_ORDERED);
            }

        }


        //4.（前面所有判断都通过了）进行预约
        //      - 向t_order表插入一条记录
        //      - t_ordersetting表里面预约的人数reservations+1
        Order order = new Order();

        order.setMemberId(member.getId());
        order.setOrderDate(date);
        order.setOrderType("微信预约");
        order.setOrderStatus("未出游");
        order.setSetmealId(setmealId);

        orderDao.add(order);//别忘了主键回填

        orderSetting.setReservations(orderSetting.getReservations()+1);
        orderSettingDao.editReservationsByOrderDate(orderSetting);

        return new Result(true,MessageConstant.ORDER_SUCCESS,order);
    }

    @Override
    public Map<String, Object> findById(Integer orderId) {

        try {
            Map<String, Object> map = orderDao.findById(orderId);

            Date date = (Date) map.get("orderDate");

            map.put("orderDate",DateUtils.parseDate2String(date));

            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
