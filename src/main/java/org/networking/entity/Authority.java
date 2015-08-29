package org.networking.entity;

import javax.persistence.*;

/**
 * Created by Gino on 8/29/2015.
 */
@Entity
@Table(name = "AUTHORITY")
public class Authority extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column
    private String role;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
