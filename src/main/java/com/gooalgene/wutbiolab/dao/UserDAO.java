package com.gooalgene.wutbiolab.dao;

import com.gooalgene.wutbiolab.entity.common.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User,Long> {
    User getByUsername(String username);
}
