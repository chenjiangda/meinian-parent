package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.TravelItem;
import com.atguigu.service.TravelItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.util.List;

// 组合注解：@Controller + @ResponseBody
@RestController
@RequestMapping("/travelItem")
public class TravelItemController {

    @Reference // 远程调用服务，不是用Autowired了，用的是Reference
    TravelItemService travelItemService;

    // 所有增删改的结果都用Result类封装返回，分页查询的结果用PageResult类封装返回

    //新建自由行方法
    //表单项参数名称（我认为是穿过来的json数据的key）必须和实体对象的属性名称一致，提供对应set方法，框架创建对象并封装数据。
    @RequestMapping("/add.do")
    @PreAuthorize("hasAuthority('TRAVELITEM_ADD')")//权限校验 用户登录后有TRAVELITEM_ADD权限才能执行add操作
    public Result add(@RequestBody TravelItem travelItem){//@RequestBody表示从请求体中获取数据
        //框架会帮我们把json数据封装给bean对象
        try {
            travelItemService.add(travelItem); //ctrl+alt+t快速try catch
            return new Result(true, MessageConstant.ADD_TRAVELITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            // 注意有异常了不要把异常抛出去，也是要返回一个result
            return new Result(false, MessageConstant.ADD_TRAVELITEM_FAIL);
        }


    }

    //分页查询自由行方法（可以不加.do，如果没写扩展名会默认自动加上.do）
    @RequestMapping("/findPage")
    @PreAuthorize("hasAuthority('TRAVELITEM_QUERY')")//权限校验
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        //严格上来说都要加trycatch，正常运行返回啥，出异常返回啥，只是老师这边忘记加了，
        //但是你会发现这是重复代码，以后会有统一处理的方法

        // travelItemService.findPage方法返回的是分页对象PageResult
        PageResult pageResult = travelItemService.findPage(queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),queryPageBean.getQueryString());

        // 分页对象只需要封装了总记录数和当前页结果这两项数据，就能在前端通过饿了么插件使用分页显示
        return pageResult;
    }

    //删除某一行自由行
    //(别忘了.do可以不用加哦)
    @RequestMapping("/delete")
    @PreAuthorize("hasAuthority('TRAVELITEM_DELETE')")//权限校验，使用TRAVELITEM_DELETE123测试
    public Result delete(@RequestParam("id") Integer id){

        try {
            travelItemService.delete(id);
            return new Result(true,MessageConstant.DELETE_TRAVELITEM_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_TRAVELITEM_FAIL);
        }

    }

    @RequestMapping("/getById")
    public Result getById(@RequestParam("id") Integer id){ //直接Integer id也行，id和传过来的普通参数的key一样即可
        try {
            TravelItem travelItem = travelItemService.getById(id);
            return new Result(true,MessageConstant.QUERY_TRAVELITEM_SUCCESS,travelItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_TRAVELITEM_FAIL);
        }
    }

    @RequestMapping("/edit")
    @PreAuthorize("hasAuthority('TRAVELITEM_EDIT')")//权限校验
    public Result edit(@RequestBody TravelItem travelItem){
        try {
            travelItemService.edit(travelItem);
            return new Result(true,MessageConstant.EDIT_TRAVELITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_TRAVELITEM_FAIL);
        }
    }

    @RequestMapping("/findAll")
    public Result findAll(){
        //这边不用分页，直接查询所有(为了省事，想看分页怎么做请看别的方法)
        List<TravelItem> list =  travelItemService.findAll();
        return new Result(true,MessageConstant.QUERY_TRAVELITEM_SUCCESS,list);
    }



}
