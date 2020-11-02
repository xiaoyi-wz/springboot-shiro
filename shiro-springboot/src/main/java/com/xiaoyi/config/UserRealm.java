package com.xiaoyi.config;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
//自定义的UserRealm
public class UserRealm extends AuthorizingRealm {
    @Override
    //授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("执行了=>授权doGetAuthorizationInfo");
        return null;
    }

    @Override
    //认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了=>认证doGetAuthenticationInfo");
        //用户名 密码 数据库中取
        String name = "root";
        String password="123456";
        UsernamePasswordToken userToken=(UsernamePasswordToken) token;
        if(!userToken.getUsername().equals(name)) {
            return null;//抛出异常  unknownAccountException
        }
        //密码认证  shiro做
        return new SimpleAuthenticationInfo("",password,"");


    }
}
