package com.xiaoyi.config;

import com.google.common.base.Splitter;
import com.xiaoyi.pojo.User;
import com.xiaoyi.service.UserService;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

//自定义的UserRealm
public class UserRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;
    @Override
    //授权
    //从认证中拿到当前登录的对象
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("执行了=>授权doGetAuthorizationInfo");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        User currentUser= (User)subject.getPrincipal();
        for (String role : Splitter.on("|").splitToList(currentUser.getPerms())) {
            info.addStringPermission(role);
        }

        return info;
    }
    @Override
    //认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了=>认证doGetAuthenticationInfo");


        UsernamePasswordToken userToken=(UsernamePasswordToken) token;
        //连接真实数据库
        User user = userService.queryUserByName(userToken.getUsername());
        if(user==null){//没有这个人
            return null;//unknownAccountException
        }
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.setAttribute("loginUser",user);

        //密码认证  shiro做
        return new SimpleAuthenticationInfo(user,user.getPwd(),"");


    }
}
