package com.ntobeko.confmanagement.models;

import com.ntobeko.confmanagement.Enums.ConferenceAttendanceStatus;

public class ConferenceAttendance {
    String id, userId, lastName, firstName, registeredDate;
    Boolean isCoAuthor;
    ConferenceAttendanceStatus status;

    public ConferenceAttendance() {

    }

    public ConferenceAttendance(String userId, String lastName, String firstName, ConferenceAttendanceStatus status, String registeredDate, Boolean isCoAuthor) {
        this.userId = userId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.status = status;
        this.registeredDate = registeredDate;
        this.isCoAuthor = isCoAuthor;
    }

    public ConferenceAttendance(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ConferenceAttendanceStatus getStatus() {
        return status;
    }

    public void setStatus(ConferenceAttendanceStatus status) {
        this.status = status;
    }

    public String getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(String registeredDate) {
        this.registeredDate = registeredDate;
    }

    public Boolean getIsCoAuthor() {
        return isCoAuthor;
    }

    public void setIsCoAuthor(Boolean isCoAuthor) {
        this.isCoAuthor = isCoAuthor;
    }

    public String getAttendanceId() {
        return id;
    }

    public void setAttendanceId(String id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
