package com.ntobeko.confmanagement.models;

import com.ntobeko.confmanagement.Enums.ProposalStatus;

public class AbstractModel {
    private String id, userId, researchTopic, abstractBody, theme, conferenceId, submissionDate, coAuthors, abstractPdfDownloadUrl, downloadProofOfPaymentUrl;
    private ProposalStatus status;

    public AbstractModel() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public AbstractModel(String researchTopic, String abstractBody, String theme, String conferenceId, String submissionDate, String coAuthors, ProposalStatus status) {
        this.researchTopic = researchTopic;
        this.abstractBody = abstractBody;
        this.theme = theme;
        this.conferenceId = conferenceId;
        this.submissionDate = submissionDate;
        this.coAuthors = coAuthors;
        this.status = status;
    }

    public String getAbstractPdfDownloadUrl() {
        return abstractPdfDownloadUrl;
    }

    public void setAbstractPdfDownloadUrl(String abstractPdfDownloadUrl) {
        this.abstractPdfDownloadUrl = abstractPdfDownloadUrl;
    }

    public String getDownloadProofOfPaymentUrl() {
        return downloadProofOfPaymentUrl;
    }

    public void setDownloadProofOfPaymentUrl(String downloadProofOfPaymentUrl) {
        this.downloadProofOfPaymentUrl = downloadProofOfPaymentUrl;
    }

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
    public ProposalStatus getStatus() {
        return status;
    }
    public void setStatus(ProposalStatus status) {
        this.status = status;
    }
    public String getCoAuthors() {
        return coAuthors;
    }
    public void setCoAuthors(String coAuthors) {
        this.coAuthors = coAuthors;
    }
    public String getAbstractId() {return id;}
    public void setAbstractId(String abstractId) {this.id = abstractId;}
}
