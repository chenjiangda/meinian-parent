day61创建了这个项目
    1.删除了父工程meinian-parent的src文件夹 - pom
    2.创建了子module：meinian_common（工具类） - jar
    3：子工程 meinian_pojo（实体类） - jar
    4：子工程 meinian_dao（Dao类） - jar
    5：子工程 meinian_interface（接口方法，用在dubbo数据调用） - jar
    6：子工程 meinian_service（Service类） - war - tomcat端口81
    7：子工程 meinian_web（表现层） - war - tomcat端口82
其中meinian_web是消费者，meinian_service是提供者，将来web调用service的远程接口（通过dubbo）。

day65为了创建定时任务新建了一个war项目meinian_jobs  -  tomcat端口83
(这个项目的tomcat启动后就会触发定时任务，去这个项目对应的tomcat面板即可看见打印数据，这个项目不启动定时任务就不开启)

day67进行移动端开发，新建了一个war项目meinian_mobile_web，需要依赖项目meinian_interface，tomcat端口80，这个项目和meinian_web
都是消费端，要用服务端提供的服务
