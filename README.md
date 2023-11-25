## 简介
一个后端使用Jdk1.8+Springboot2.7x+Mysql8.0+Redis，
前端使用Vue2+Vite3+Element 的可快速启动的后台管理框架，
内置自研代码生成器，可一键生成前端Vue页面、后端CRUD和导入导出接口、动态菜单以及数据字典SQL语句等一条龙代码，
可免费用于商业。项目容易上手，功能丰富，完全开源，一切只为让你的开发更简单

该分支为单模块版本，多模块版本分支为：easyboot-multi-module，分支地址：https://gitee.com/thisZhuMy_admin/EasyBoot/tree/easyboot-multi-module/

## 快速体验
演示地址：https://www.easyboot.cn

游客账号：visitor 密码：visitor

## 仓库地址
前端地址：https://gitee.com/yunzhongshan/Easy-vue2-vite3

后端地址：https://gitee.com/thisZhuMy_admin/EasyBoot

## 如何启动
1.安装Mysql8.0

2.安装Redis

3.使用IDEA打开项目，下载依赖

4.IDEA插件市场中搜索lombok安装并重启IDEA

5.修改application-dev.yml中的mysql连接配置和redis连接配置

6.启动后端项目

7.启动前端项目

8.打开浏览器地址：http://localhost:9100

使用 账号：admin 密码：123456 访问系统

如果使用的是windows系统，还需要将application-dev.yml中的file-path配置和image-path配置修改为本地磁盘文件夹路径


## 已有功能
<ul>
    <li>动态验证码登录</li>
    <li>首页</li>
    <li>个人中心</li>
    <li>用户管理</li>
    <li>角色管理</li>
    <li>菜单管理</li>
    <li>部门管理</li>
    <li>岗位管理</li>
    <li>字典管理</li>
    <li>配置管理</li>
    <li>模板管理</li>
    <li>公告管理</li>
    <li>在线用户</li>
    <li>定时任务</li>
    <li>服务器监控</li>
    <li>Redis监控</li>
    <li>黑名单管理</li>
    <li>登录日志</li>
    <li>操作日志</li>
    <li>调度日志</li>
    <li>代码生成</li>
    <li>接口文档</li>

</ul>

## 后续规划
<ul>
    <li>纯单体改造为多模块单体</li>
    <li>升级到SpringBoot3x，使用Java17</li>
    <li>更多功能尽请期待......</li>
</ul>

## 页面展示
![image](doc/login.png)
![image](doc/admin-user.png)
![image](doc/role.png)
![image](doc/menu.png)
![image](doc/department.png)
![image](doc/post.png)
![image](doc/data-dict-domain.png)
![image](doc/sys-config-domain.png)
![image](doc/template-config.png)
![image](doc/notice.png)
![image](doc/online-user.png)
![image](doc/scheduled-task.png)
![image](doc/server.png)
![image](doc/redis.png)
![image](doc/blacklist.png)
![image](doc/login-log.png)
![image](doc/operation-log.png)
![image](doc/gen.png)
![image](doc/template-param-config.png)
![image](doc/doc.png)