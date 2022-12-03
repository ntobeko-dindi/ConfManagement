package com.ntobeko.confmanagement.models;

public class AbstractApproval {
    private String abstractId;
    private String decisionStatus;
    private String dateActioned;

    public AbstractApproval(String abstractId, String decisionStatus, String dateActioned) {
        this.abstractId = abstractId;
        this.decisionStatus = decisionStatus;
        this.dateActioned = dateActioned;
    }

    public String getAbstractId() {
        return abstractId;
    }

    public void setAbstractId(String abstractId) {
        this.abstractId = abstractId;
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
