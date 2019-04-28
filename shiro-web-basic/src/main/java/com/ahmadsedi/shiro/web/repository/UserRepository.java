package com.ahmadsedi.shiro.web.repository;

import com.ahmadsedi.shiro.web.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 *         Date: 3/3/19
 *         Time: 2:24 PM
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsernameAndPassword(String username, String password);

    User findByUsername(String username);
}
