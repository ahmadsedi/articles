package com.ahmadsedi.shiro.web.entity;

import javax.persistence.*;

/**
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 *         Date: 3/3/19
 *         Time: 2:15 PM
 */

@Entity
@Table(name = "role_tbl")
public class Role {
    Long id;

    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
