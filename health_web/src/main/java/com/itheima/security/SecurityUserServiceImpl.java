package com.itheima.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.UserService;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component("securityUserService")
public class SecurityUserServiceImpl implements UserDetailsService {
    @Reference
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (user == null) {
            return null;
        }
        //通过查询数据库获取用户的所有角色set去重
        Set<Role> roles = user.getRoles();
        /*记录权限集合*/
        List<GrantedAuthority> authorityList = new ArrayList<>();
        SimpleGrantedAuthority simpleGrantedAuthority;
        if (roles != null) {
            for (Role role : roles) {
                simpleGrantedAuthority = new SimpleGrantedAuthority(role.getKeyword());
                authorityList.add(simpleGrantedAuthority);
                Set<Permission> permissions = role.getPermissions();
                if (permissions != null) {
                    //添加权限
                    for (Permission permission : permissions) {
                        simpleGrantedAuthority = new SimpleGrantedAuthority(permission.getKeyword());
                        authorityList.add(simpleGrantedAuthority);
                    }
                }
            }
        }
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorityList);
        return userDetails;
    }
}
