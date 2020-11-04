package com.xiaoyi.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    //ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager")DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(securityManager);
        /*
        * anon:无需认证就可以访问
        * authc：必须认证才能访问
        * user：必须拥有  记住我 功能才能访问
        * perms：拥有对某个资源的权限才能访问
        * role： 拥有某个角色权限才能访问
        *
        * */
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        Map<String ,String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/user/add","perms[user:add]");
        filterChainDefinitionMap.put("/user/update","perms[user:update]");
        filterChainDefinitionMap.put("/user/edit","perms[user:edit]");
        filterChainDefinitionMap.put("/user/*","authc");
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        //设置登录的请求
        bean.setLoginUrl("/toLogin");
        bean.setUnauthorizedUrl("/noauth");

        return bean;
    }
    //DefaultWebSecurityManager
    @Bean("securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联UserRealm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    //创建realm对象  需要自定义
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }
}
