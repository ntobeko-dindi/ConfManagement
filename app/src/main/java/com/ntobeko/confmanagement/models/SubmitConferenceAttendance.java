package com.ntobeko.confmanagement.models;

import com.ntobeko.confmanagement.Enums.ConferenceAttendanceStatus;

public class SubmitConferenceAttendance {
    public String userId, conferenceId, registrationType;
    Boolean isAbstractSubmission;
    public ConferenceAttendanceStatus status;

    public SubmitConferenceAttendance(String conferenceId, String registrationType, Boolean isAbstractSubmission) {
        this.conferenceId = conferenceId;
        this.registrationType = registrationType;
        this.isAbstractSubmission = isAbstractSubmission;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(String conferenceId) {
        this.conferenceId = conferenceId;
    }

    public String isCoAuthor() {
        return registrationType;
    }

    public void setRegistrationType(String registrationType) {
        this.registrationType = registrationType;
    }

    public ConferenceAttendanceStatus getStatus() {
        return status;
    }

    public void setStatus(ConferenceAttendanceStatus status) {
        this.status = status;
    }

    public String getRegistrationType() {
        return registrationType;
    }

    public Boolean getAbstractSubmission() {
        return isAbstractSubmission;
    }

    public void setAbstractSubmission(Boolean abstractSubmission) {
        isAbstractSubmission = abstractSubmission;
    }
}
