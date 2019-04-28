package com.ahmadsedi.shiro.web;

/**
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 4/13/19
 * Time: 11:13 AM
 */

import com.ahmadsedi.shiro.web.AbstractShiroTest;
import com.ahmadsedi.shiro.web.entity.Permission;
import com.ahmadsedi.shiro.web.entity.Role;
import com.ahmadsedi.shiro.web.entity.User;
import com.ahmadsedi.shiro.web.repository.PermissionRepository;
import com.ahmadsedi.shiro.web.repository.RoleRepository;
import com.ahmadsedi.shiro.web.repository.UserRepository;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.subject.WebSubject;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration(value = "src/main/webapp")
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class WebShiroIntegrationTest extends AbstractShiroTest {

    @Autowired
    private SecurityManager securityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @PostConstruct
    public void init(){
        setSecurityManager(securityManager);
    }

    @Before
    public void inserAUser() {

        //truncate tables before any test
        userRepository.deleteAll();
        permissionRepository.deleteAll();
        roleRepository.deleteAll();

        //insert needed data
        Role role = new Role();
        role.setName("admin");
        roleRepository.save(role);
        HashSet roles = new HashSet();
        roles.add(role);

        Permission permission = new Permission();
        permission.setKey("admin");
        permission.setTitle("Administrator");
        permission.setRole(role);
        permissionRepository.save(permission);

        User user = new User();
        user.setUsername("admin");
        user.setPassword("123");
        User u = userRepository.findByUsername("admin");

        user.setRoles(roles);
        userRepository.save(user);
        Iterable<User> users = userRepository.findAll();
        users.forEach(us -> System.out.println(us));
    }

    @Test
    public void anonymousTest() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class, RETURNS_DEEP_STUBS);
        HttpServletResponse response = mock(HttpServletResponse.class, RETURNS_DEEP_STUBS);
        Subject currentUser = new WebSubject.Builder(getSecurityManager(), request, response).buildSubject();

        assertThat(currentUser.isAuthenticated()).isFalse();
    }

    @Test
    public void authenticationTest() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class, RETURNS_DEEP_STUBS);
        HttpServletResponse response = mock(HttpServletResponse.class, RETURNS_DEEP_STUBS);
        Subject currentUser = new WebSubject.Builder(getSecurityManager(), request, response).buildSubject();

        if (!currentUser.isAuthenticated()) {
            AuthenticationToken token = new UsernamePasswordToken("admin", "123");
            // token.setRememberMe(true);

            // login
            currentUser.login(token);

            assertThat(currentUser.isAuthenticated()).isTrue();
        }
    }

    @Test
    public void authorizationTest() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class, RETURNS_DEEP_STUBS);
        HttpServletResponse response = mock(HttpServletResponse.class, RETURNS_DEEP_STUBS);
        Subject currentUser = new WebSubject.Builder(getSecurityManager(), request, response).buildSubject();

        if (!currentUser.isAuthenticated()) {
            AuthenticationToken token = new UsernamePasswordToken("admin", "123");
            // token.setRememberMe(true);

            // login
            currentUser.login(token);

            assertThat(currentUser.isAuthenticated()).isTrue();
            assertThat(currentUser.hasRole("admin")).isTrue();
            assertThat(currentUser.isPermitted("admin")).isTrue();
            assertThat(currentUser.hasRole("editor")).isFalse();
            assertThat(currentUser.isPermitted("editor")).isFalse();
        }
    }

    @AfterClass
    public static void tearDownSubject() {
        //3. Unbind the subject from the current thread:
        tearDownShiro();
    }
}
