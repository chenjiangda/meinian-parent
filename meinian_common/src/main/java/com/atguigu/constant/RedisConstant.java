package com.atguigu.constant;

public class RedisConstant {
    //1.套餐图片的所有图片名称（七牛）
    public static final String SETMEAL_PIC_RESOURCES = "setmealPicResources";
    //2.套餐图片保存在数据库中的图片名称（数据库）
    public static final String SETMEAL_PIC_DB_RESOURCES = "setmealPicDbResources";

    //redis中会以1,2分别为key存储两个集合，集合里存的是各个图片的名字，只要上传到七牛云
    //的图片名字就会放入1对应的set，成功提交套餐游的对应图片文字才会放入2对应的set
    //所以1对应的set内容是>=2对应的set的

}
