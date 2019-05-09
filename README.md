# CMS 完整能运行的后台管理系统，后台数据工程基于SpringMVC实现，前台界面工程基于Node.js+Vue.js+iView的架构实现。
## 工程结构介绍
```
 cms/
    * cms_data/ ---------- 后台数据工程
    * cms_web/  ---------- 前台界面工程
    * tb_menu_one.sql ---- 数据库初始化sql
 ```
 ## 关键技术功能介绍
 ### 一. 登录账号密码采用RSA非对称加密方式
  由后端工程下发公钥，前端工程使用公钥对账号密码进行加密再进行login操作，有效避免账号密码暴露。
  如下图所示：
  
![foryou](https://github.com/WrathLi/cms/blob/master/imgs/7.png)

 ### 二. 左侧菜单栏动态下发
  左侧菜单栏的显示根据不同用户分配的权限下发不同的菜单，采用二级菜单的结构，如图所示：
![foryou](https://github.com/WrathLi/cms/blob/master/imgs/1.png)

 ### 三. JWT做权限加密和超时管理
 ![foryou](https://github.com/WrathLi/cms/blob/master/imgs/8.png)
 ![foryou](https://github.com/WrathLi/cms/blob/master/imgs/9.png)
 
 ### 四. 菜单栏路径配置
 ![foryou](https://github.com/WrathLi/cms/blob/master/imgs/3.png)
 ![foryou](https://github.com/WrathLi/cms/blob/master/imgs/4.png)
 
 ### 五. 角色管理
  可以创建不同角色，对角色分配菜单栏的界面权限
 ![foryou](https://github.com/WrathLi/cms/blob/master/imgs/5.png)
 
 ### 六. 用户管理
  对用户进行编辑，冻结等操作，同时可以对不同用户分配角色，每个用户只能有一个角色。
 ![foryou](https://github.com/WrathLi/cms/blob/master/imgs/6.png)

github网速过慢，下面是码云上的仓库地址：
[https://gitee.com/WrathLi/cms-vue-iView/tree/master](https://gitee.com/WrathLi/cms-vue-iView/tree/master)
