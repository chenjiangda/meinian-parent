package test;

import org.junit.Test;

import java.util.Date;

public class Mytest01 {
    @Test
    public void test01(){
//        System.out.println("?");
////        Date date = new Date("2017-01-18");
//
////        System.out.println("date = " + date);
//
//        Date date1 = new Date();    // 调用无参数构造函数
//        System.out.println(date1.toString());    // 输出：Wed May 18 21:24:40 CST 2016
//        Date date2 = new Date(60000);    // 调用含有一个long类型参数的构造函数
//        System.out.println(date2);    // 输出：Thu Jan 0108:01:00 CST 1970
//        Date date3 = new Date("2021-10-12");
//        System.out.println(date3);



//        Date date3 = new Date("2021-10-12");//会报错
        Date date3 = new Date("2020/12/01");//这样才行
        System.out.println(date3);
    }
}
