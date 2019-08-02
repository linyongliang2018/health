package com.itheima.security;

import com.itheima.pojo.UserDemo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class SecurityUserServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDemo userDemo = findByUsername(username);
        List<GrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority sga = new SimpleGrantedAuthority("ROLE_ADMIN");
        authorities.add(sga);
        sga = new SimpleGrantedAuthority("add");
        authorities.add(sga);
        UserDetails userDetails = new User(userDemo.getUsername(), userDemo.getPassword(), authorities);
        return userDetails;
    }

    private UserDemo findByUsername(String username) {
        if ("admin".equals(username)) {
            UserDemo userDemo = new UserDemo();
            userDemo.setUsername(username);
            userDemo.setPassword("$2a$10$khPECbkhK1dHgZ7R8cR8r.NMZLuCbpSwielqVFkU3FrXtASre4wvC");
            return userDemo;
        }
        return null;
    }
}
