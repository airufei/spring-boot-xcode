## 1、spring-boot-xcode 简介
基于spring-boot的代码生成工具、可以根据生成模型生成多种多样的代码结构和代码内容。不依赖于基础框架或者注册中心，可以单独拉取项目进行使用。

## 2、快速上手
1、建立数据库（xcode）

2、导入SQL数据，在spring-boot 下文件夹下的xcode.sql

3、启动项目并访问项目（请求根路径在application-dev.yml中进行配置）

[http://localhost:8093/toLogin](http://localhost:8093/toLogin)

5、账号：18610000006 密码：abc123

6、进入主页选择 代码生成 菜单

7、配置数据表信息（选择查询列、查询条件，排序、字段类型、编辑页面等）

8、配置生成方案并生成代码（配置代码路径、包路径、访问路径、功能简介、代码坐作者等）

9、代码生成路径默认在项目所在盘更路径下的E:\opt\xmf\code

10、api包下的代码（除去api）拷贝到项目的xx-api com.xx.api下,test包拷贝到测试test包下

11、model包下的代码（除去model）拷贝到项目的spring-cloud-common com.xx.model下

11、base包下的代码（除去base）拷贝到项目的spring-boot-demo-service com.xmf.service下
