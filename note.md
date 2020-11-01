1 导入依赖
2 配置文件
3 helloworld

Spring Security~都有
```
Subject currentUser = SecurityUtils.getSubject();
Session session = currentUser.getSession();
currentUser.isAuthenticated()
currentUser.getPrincipal()
currentUser.hasRole("schwartz")
currentUser.isPermitted("winnebago:drive:eagle5")
urrentUser.logout();
```
