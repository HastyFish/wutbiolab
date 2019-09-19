package com.gooalgene.wutbiolab.security;

import com.gooalgene.wutbiolab.dao.UserDAO;
import com.gooalgene.wutbiolab.entity.common.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.getByUsername(username);
        if (user == null) {
            return null;
        }
        String password = user.getPassword();
        Long id = user.getId();

        return new org.springframework.security.core.userdetails.User(username, password, true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"));
    }

}