package com.ahmadsedi.shiro.web.service;

import com.ahmadsedi.shiro.web.entity.Permission;
import com.ahmadsedi.shiro.web.entity.Role;
import com.ahmadsedi.shiro.web.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 *         Date: 3/5/19
 *         Time: 9:42 AM
 */
@Service
@Transactional
public class PermissionService {
    @Autowired
    PermissionRepository permissionRepository;

    public List<Permission> findByRole(Set<Role> roles) {
        return permissionRepository.findByRole(roles);
    }
}
