package com.ntobeko.confmanagement.models;

import com.ntobeko.confmanagement.Enums.UserRoles;

public class User {
    private String firstName, lastName;
    private UserRoles userRole;
    private Login login;

    public User(){}

    public User(String firstName, String lastName, UserRoles userRole, Login login) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userRole = userRole;
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserRoles getUserRole(UserRoles attendee) {
        return userRole;
    }

    public void setUserRole(UserRoles userRole) {
        this.userRole = userRole;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }
}
