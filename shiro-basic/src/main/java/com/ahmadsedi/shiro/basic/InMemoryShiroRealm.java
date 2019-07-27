package com.ahmadsedi.shiro.basic;

/**
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 4/8/19
 * Time: 2:14 PM
 *
 * In Memory Shiro Realm Class
 */

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.*;

public class InMemoryShiroRealm extends AuthorizingRealm {

    private Map<String, String> credentials = new HashMap<>();
    private Map<String, Set<String>> roles = new HashMap<>();
    private Map<String, Set<String>> perm = new HashMap<>();

    {
        credentials.put("user", "password");
        credentials.put("user2", "password2");
        credentials.put("user3", "password3");

        roles.put("user", new HashSet<>(Arrays.asList("admin")));
        roles.put("user2", new HashSet<>(Arrays.asList("editor")));
        roles.put("user3", new HashSet<>(Arrays.asList("author")));

        perm.put("admin", new HashSet<>(Arrays.asList("*")));
        perm.put("editor", new HashSet<>(Arrays.asList("articles:*")));
        perm.put("author",
                new HashSet<>(Arrays.asList("articles:compose",
                        "articles:save")));

    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {

        UsernamePasswordToken uToken = (UsernamePasswordToken) token;

        if (uToken.getUsername() == null
                || uToken.getUsername().isEmpty()
                || !credentials.containsKey(uToken.getUsername())
                ) {
            throw new UnknownAccountException("username not found!");
        }


        return new SimpleAuthenticationInfo(
                uToken.getUsername(), credentials.get(uToken.getUsername()),
                getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Set<String> roleNames = new HashSet<>();
        Set<String> permissions = new HashSet<>();

        principals.forEach(p -> {
            Set<String> roles = getRoleNamesForUser((String) p);
            roleNames.addAll(roles);
            permissions.addAll(getPermissions(roles));

        });

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        info.setStringPermissions(permissions);
        return info;
    }

    protected Set<String> getRoleNamesForUser(String username) {
        if (!roles.containsKey(username)) {
            throw new RuntimeException("username not found!");
        }

        return roles.get(username);
    }

    protected Set<String> getPermissions(Collection<String> roleNames) {
        for (String role : roleNames) {
            if (!perm.containsKey(role)) {
                throw new RuntimeException("role not found!");
            }
        }

        Set<String> finalSet = new HashSet<>();
        for (String role : roleNames) {
            finalSet.addAll(perm.get(role));
        }

        return finalSet;
    }

}
