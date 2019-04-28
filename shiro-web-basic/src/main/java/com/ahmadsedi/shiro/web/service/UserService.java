package com.ahmadsedi.shiro.web.service;

import com.ahmadsedi.shiro.web.entity.User;
import com.ahmadsedi.shiro.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 *         Date: 3/5/19
 *         Time: 9:30 AM
 */
@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

}
