package com.atguigu.test;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;

/* 七牛云文件上传测试类
 * Zone.zone0:华东
 * Zone.zone1:华北
 * Zone.zone2:华南
 * 自动识别上传区域
 * Zone.autoZone
 */
public class TestQiniu {

    /**
     * 改四个地方即可
     */
    // 上传本地文件
    /*@Test
    public void uploadFile(){
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());//华南
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        //下面两个是你自己账号的秘钥
        String accessKey = "8hIiq0H2a0iy_vwwxVWlmXumbucHOsCqOPLQ-XlV";  //1.要改成自己的
        String secretKey = "-aXPCe6OYi1CyvqJecpc3cI6q1jQiWIp1A5CnU8m";  //2.要改成自己的
        //bucket是你自己创建的空间名字
        String bucket = "atguigumeinianjd";                             //3.要改成自己的
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png，可支持中文
        String localFilePath = "D:\\QiniuYunTest\\zhongguojie.jpeg";    //4.要改成自己的
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null; //key表示自定义文件名称，如果一样会覆盖
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);// FspfyEyKfuHZ0kcbXRIc5T9YhCax
            System.out.println(putRet.hash);// FspfyEyKfuHZ0kcbXRIc5T9YhCax
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }*/


    /*@Test
    public void deleteFile(){
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        //...其他参数参考类注释
        String accessKey = "8hIiq0H2a0iy_vwwxVWlmXumbucHOsCqOPLQ-XlV";
        String secretKey = "-aXPCe6OYi1CyvqJecpc3cI6q1jQiWIp1A5CnU8m";
        String bucket = "atguigumeinianjd";//七牛云上你创建的空间
        String key = "FjIeoSZvicblE6FBHu7yv1LK8Puv";//七牛云中存储的你要删的文件的名称
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }*/



}
