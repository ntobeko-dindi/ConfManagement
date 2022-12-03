package com.ntobeko.confmanagement.models;

public class ConferenceApproval {
    private String conferenceId;
    private String decisionStatus;
    private String dateActioned;

    public ConferenceApproval(String conferenceId, String decisionStatus, String dateActioned) {
        this.conferenceId = conferenceId;
        this.decisionStatus = decisionStatus;
        this.dateActioned = dateActioned;
    }

    public String getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(String conferenceId) {
        this.conferenceId = conferenceId;
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
