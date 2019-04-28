package com.ahmadsedi.shiro.web.repository;

import com.ahmadsedi.shiro.web.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 *         Date: 3/5/19
 *         Time: 9:29 AM
 */

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
}