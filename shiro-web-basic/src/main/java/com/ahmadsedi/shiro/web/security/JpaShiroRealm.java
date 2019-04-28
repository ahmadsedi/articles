package com.ahmadsedi.shiro.web.security;

import com.ahmadsedi.shiro.web.entity.Permission;
import com.ahmadsedi.shiro.web.entity.User;
import com.ahmadsedi.shiro.web.service.PermissionService;
import com.ahmadsedi.shiro.web.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 *         Date: 3/5/19
 *         Time: 9:22 AM
 */
@Component("jpaShiroRealm")
public class JpaShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        String username = (String) getAvailablePrincipal(principals);

        User user = userService.findByUsername(username);
        Set<String> roleNames = new HashSet<>();
        user.getRoles().forEach(role -> roleNames.add(role.getName()));

        List<Permission> permissions = permissionService.findByRole(user.getRoles());
        Set<String> permissionKeys = new HashSet<>();
        permissions.forEach(permission -> permissionKeys.add(permission.getKey()));

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        info.setStringPermissions(permissionKeys);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        String username = upToken.getUsername();
        AuthenticationInfo info = buildAuthenticationInfo(username, userService.findByUsername(username).getPassword().toCharArray());
        return info;
    }

    protected AuthenticationInfo buildAuthenticationInfo(String username, char[] password) {
        return new SimpleAuthenticationInfo(username, password, getName());
    }


    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}