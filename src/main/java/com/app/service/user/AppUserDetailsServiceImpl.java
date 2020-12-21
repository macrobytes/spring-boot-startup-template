package com.app.service.user;

import java.util.Optional;

import com.app.dao.user.AppUserDetails;
import com.app.dao.user.UserModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AppUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserModel> user = userService.findByUsername(username);
        if (user.isPresent()) {
            return new AppUserDetails(user.get());
        }
        throw new UsernameNotFoundException("unable to find user: " + username);
    }

}
