package com.ntobeko.confmanagement.models;

public class Credential {
    private String siteName, userName, password;

    public Credential(String siteName, String userName, String password) {
        this.siteName = siteName;
        this.userName = userName;
        this.password = password;
    }

    public String getSiteName() {
        return siteName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Credential{" +
                "siteName='" + siteName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
