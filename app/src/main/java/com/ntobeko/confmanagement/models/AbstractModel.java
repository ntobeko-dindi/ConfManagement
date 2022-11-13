package com.ntobeko.confmanagement.models;

public class AbstractModel {
    private String researchTopic, abstractBody, theme, conferenceId, submissionDate;
    public String getResearchTopic() {
        return researchTopic;
    }
    public void setResearchTopic(String researchTopic) {
        this.researchTopic = researchTopic;
    }
    public String getAbstractBody() {
        return abstractBody;
    }
    public void setAbstractBody(String abstractBody) {
        this.abstractBody = abstractBody;
    }
    public String getTheme() {
        return theme;
    }
    public void setTheme(String theme) {
        this.theme = theme;
    }
    public String getConferenceId() {
        return conferenceId;
    }
    public void setConferenceId(String conferenceId) {
        this.conferenceId = conferenceId;
    }
    public String getSubmissionDate() {
        return submissionDate;
    }
    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }
}
