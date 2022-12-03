package com.ntobeko.confmanagement.models;

public class ConferenceAttendanceApproval {
    private String attendanceId, decisionStatus, dateActioned;

    public ConferenceAttendanceApproval(String attendanceId, String decisionStatus, String dateActioned) {
        this.attendanceId = attendanceId;
        this.decisionStatus = decisionStatus;
        this.dateActioned = dateActioned;
    }

    public String getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(String attendanceId) {
        this.attendanceId = attendanceId;
    }

    public String getDecisionStatus() {
        return decisionStatus;
    }

    public void setDecisionStatus(String decisionStatus) {
        this.decisionStatus = decisionStatus;
    }

    public String getDateActioned() {
        return dateActioned;
    }

    public void setDateActioned(String dateActioned) {
        this.dateActioned = dateActioned;
    }
}
