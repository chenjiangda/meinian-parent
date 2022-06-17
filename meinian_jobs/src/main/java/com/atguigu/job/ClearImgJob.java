package com.atguigu.job;

import com.atguigu.constant.RedisConstant;
import com.atguigu.util.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/*没用注解配置这个类的bean，因为在applicationContext-jobs.xml中已经用xml的方式配置bean了*/
public class ClearImgJob {

    @Autowired
    JedisPool jedisPool;

    //清理图片
    public void clearImg(){

        //计算redis中两个集合的差值，获取垃圾图片名称
        // 需要注意：在比较的时候，数据多的放到前面，如果pic多，那么pic放到前面，db多，db放到前面
        Set<String> pics =  jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES,RedisConstant.SETMEAL_PIC_DB_RESOURCES);

        for (String pic : pics) {
            //获取的pics图片是垃圾图片的名字，需要从七牛云中删除
            QiniuUtils.deleteFileFromQiniu(pic);
            System.out.println("删除垃圾图片，图片名:pic = " + pic);
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,pic);

        }



    }

}
