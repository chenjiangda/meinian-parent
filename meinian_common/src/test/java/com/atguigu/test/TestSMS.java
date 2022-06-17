package com.atguigu.test;

import com.atguigu.util.HttpUtils;
import org.apache.http.HttpResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试山东鼎信的短信发送服务
 */
public class TestSMS {

    /*public static void main(String[] args) {
        String host = "http://dingxin.market.alicloudapi.com"; //固定的
        String path = "/dx/sendSms"; //固定的
        String method = "POST"; //固定的
        String appcode = "12ce9032dbf84211a1da54b32ba1fb46"; //自己的AppCode

        //设置请求头
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode); //固定的,注意空格

        //设置请求参数 ，三个参数名固定，mobile和param的参数值自己写
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", "18867628988"); //key固定，value自己写
        querys.put("param", "code:9999"); //key固定，value自己写，code:开头的，后面的验证码只支持数字和字母（给上门那个手机号发送的验证码的值）
        querys.put("tpl_id", "TP1711063"); //测试模板的id，固定的，如果要自定义短信模板请联系客服

        Map<String, String> bodys = new HashMap<String, String>();//请求体(没数据的，不用管它)



        try {
            *//**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             *//*
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

}
