确切来说他不应该叫entity类，应该叫vo类(用来接数据的)
他是和页面对接时候需要用到的数据封装

pojo里面的实体类主要是和数据库表对应的，这个entity里面的主要是和页面对应的

所有增删改的结果都用Result类封装返回
分页查询的结果用PageResult类封装返回