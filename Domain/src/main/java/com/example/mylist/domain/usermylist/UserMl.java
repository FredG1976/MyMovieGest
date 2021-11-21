package com.example.mylist.domain.usermylist;

import javax.persistence.*;

@Entity
public class UserMl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private Boolean isAdmin = false;
    private String login;
    private String password;

    /* getters et setters */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        this.isAdmin = admin;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "UserMl{" +
                "id='" + id + '\'' +
                ", fullName = " + fullName +
                ", isAdmin = " + isAdmin +
                ", login ='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
