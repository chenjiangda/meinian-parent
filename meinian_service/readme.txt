由于meinian_service会依赖meinian_dao，所以
meinian_dao的resources下的配置文件和日志文件也会被依赖过来，
所以meinian_service的resources下就不用写已经写过的那些配置文件了（当然你再加一遍也无所谓，可加可不加的）

applicationContext-tx.xml是事务的配置文件
applicationContext-service.xml是配置dubbo的内容