package com.ahmadsedi;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 *         Date: 3/3/19
 *         Time: 10:51 AM
 */

public class TestSecurity {

    Logger log = LoggerFactory.getLogger("TestSecurity");

    @Before
    public void startUp(){
        Realm iniRealm = new IniRealm("classpath:shiro.ini");
        org.apache.shiro.mgt.SecurityManager securityManager = new DefaultSecurityManager(iniRealm);
        SecurityUtils.setSecurityManager(securityManager);
    }

    @Test
    public void testLogin(){
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            login("user", "password");
        }
        Assert.assertTrue(currentUser.isAuthenticated());
    }

    @Test
    public void testAuthorization1(){
        login("user", "password");
        Subject currentUser = SecurityUtils.getSubject();
        Assert.assertTrue(currentUser.hasRole("admin"));
        Assert.assertFalse(currentUser.hasRole("editor"));
        Assert.assertFalse(currentUser.hasRole("author"));
        Assert.assertTrue(currentUser.isPermitted("articles:update"));
    }

    @Test
    public void testAuthorization2(){
        login("user2", "password2");
        Subject currentUser = SecurityUtils.getSubject();
        Assert.assertFalse(currentUser.hasRole("admin"));
        Assert.assertTrue(currentUser.hasRole("editor"));
        Assert.assertFalse(currentUser.hasRole("author"));
        Assert.assertTrue(currentUser.isPermitted("articles:update"));
    }

    @Test
    public void testAuthorization3(){
        login("user3", "password3");
        Subject currentUser = SecurityUtils.getSubject();
        Assert.assertFalse(currentUser.hasRole("admin"));
        Assert.assertFalse(currentUser.hasRole("editor"));
        Assert.assertTrue(currentUser.hasRole("author"));
        Assert.assertTrue(currentUser.isPermitted("articles:save"));
        Assert.assertFalse(currentUser.isPermitted("articles:update"));
    }

    private void login(String username, String password){
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token
                = new UsernamePasswordToken(username, password);
        token.setRememberMe(true);
        try {
            currentUser.login(token);
        } catch (UnknownAccountException uae) {
            log.error("Username Not Found!", uae);
        } catch (IncorrectCredentialsException ice) {
            log.error("Invalid Credentials!", ice);
        } catch (LockedAccountException lae) {
            log.error("Your Account is Locked!", lae);
        } catch (AuthenticationException ae) {
            log.error("Unexpected Error!", ae);
        }
    }


}
