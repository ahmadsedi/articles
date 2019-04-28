package com.ahmadsedi.shiro.web.repository;

import com.ahmadsedi.shiro.web.entity.Permission;
import com.ahmadsedi.shiro.web.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 *         Date: 3/5/19
 *         Time: 9:43 AM
 */

@Repository
public interface PermissionRepository extends CrudRepository<Permission, Long> {
    List<Permission> findByRole(Set<Role> roles);
}
