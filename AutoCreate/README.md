======================
开发者小工具AutoCreate
======================

# 概述

* Java开发中经常会用到数据库操作，例如映射产生POJO对象（JavaBeans）,需要查找数据库，查表，查字段，再写Java文件，当表的和字段的数量一多，既繁琐又容易出错
* Java web开发中用到的脚本有时需要保密或是减少空间，需要合并和压缩

基于两个需求，结合ant、hibernate3、UglifyJS、yuicompressor写的简易工具。

# 用法：

## 图例

**运行Compress/build/build.xml**

![build.xml](http://i1.tietuku.com/1faec6d2ffe63f07.png)

**生成Agent.java**

![Agent.java](http://i1.tietuku.com/274253a992b8015f.png)

**压缩，简单易懂**

![compress](http://i1.tietuku.com/a9f73126663d6389.png)

## 基本配置

 1. 利用build.xml直接生成sandi-autoCreate.jar包，并自动存放在build文件夹下
 2. 在一个新的工程下引入sandi-autoCreate.jar，截图用的是Compress，并分别配置好配置文件：
 * build/config/build.properties——用户最主要需要改的地方
 * build/config/jdbc.properties——数据库配置，需要改
 * build/build.xml——总打包配置，一般不用改
 * build/hibernate/hibernate.reveng.xml——产生POJO的java文件位置，src中，用户根据需要改动
 * build/buildPojo.xml——产生POJO对象，一般不用改
 * build/buildCompress.xml——压缩文件，一般不要动它
 
## 配置细节
 
 **AutoCreate/src/cn/com/autoCreate/util/Constants.java作为.jar包的配置文件，主要提一下其中创建Pojo的变量配置：**
 
* hbm.xml目录声明需对应build/hibernate/hibernate.reveng.xml中的filterd的package，否则找不到
* cfg.xml目录声明对应build/hibernate/hibernate.cfg.xml产生的目录
* java目录声明对应实际项目包的名称设置

# 最后

* 配置要相互对应，其实是不想写死了
* UglifyJS用于压缩js,压缩率高于yuicompressor,yuicompressor用于合并js,合并和压缩css
* UglifyJS本来用Node.js写的，有人用java的脚本引擎做了封装:[链接](https://github.com/yuanyan/UglifyJS-java)，手动打成java包后报错，据说用jre1.6 R2后的编译没问题，不过用jre1.7还是报错，查了一下，把Array.js部分代码注释掉就可以，本工程只上传UglifyJS.jar。
* Compress中的sandi-autoCreate.jar只是表明摆放位置，用户需要重新生成并放在原位