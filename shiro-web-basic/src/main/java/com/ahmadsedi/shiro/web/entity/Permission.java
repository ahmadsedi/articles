package com.ahmadsedi.shiro.web.entity;

import javax.persistence.*;

/**
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 *         Date: 3/3/19
 *         Time: 2:15 PM
 */
@Entity
@Table(name = "permission_tbl")
public class Permission {

    Long id;
    String key;
    String title;
    Role role;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @ManyToOne
    @JoinColumn(name = "ROLE_ID")
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
