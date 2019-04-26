package army.helpful.persistha.message.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User extends BasicModel {

    public String username;
    public String profilePhotoUrl;
    public String coverUrl;

    @Column(nullable=false,columnDefinition="bigint(20) default 0")
    Long currentThankAmount;

    @JsonIgnoreProperties("sender")
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> sentTransactions = new ArrayList<>();

    @JsonIgnoreProperties("receiver")
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> receivedTransactions = new ArrayList<>();

    @JsonIgnoreProperties("user")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProblemContent> problemContents = new ArrayList<>();

    @JsonIgnoreProperties("user")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SolutionContent> solutionContents = new ArrayList<>();

    @JsonIgnoreProperties("user")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProblemTitle> problemTitles = new ArrayList<>();

    @JsonIgnoreProperties("user")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SolutionTitle> solutionTitles = new ArrayList<>();

   public String toString(){

        return   this.getClass().getSimpleName();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public List<ProblemContent> getProblemContents() {
        return problemContents;
    }

    public void setProblemContents(List<ProblemContent> problemContents) {
        this.problemContents = problemContents;
    }

    public List<SolutionContent> getSolutionContents() {
        return solutionContents;
    }

    public void setSolutionContents(List<SolutionContent> solutionContents) {
        this.solutionContents = solutionContents;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public List<Transaction> getSentTransactions() {
        return sentTransactions;
    }

    public void setSentTransactions(List<Transaction> sentTransactions) {
        this.sentTransactions = sentTransactions;
    }

    public List<Transaction> getReceivedTransactions() {
        return receivedTransactions;
    }

    public void setReceivedTransactions(List<Transaction> receivedTransactions) {
        this.receivedTransactions = receivedTransactions;
    }

    public Long getCurrentThankAmount() {
        return currentThankAmount;
    }

    public void setCurrentThankAmount(Long currentThankAmount) {
        this.currentThankAmount = currentThankAmount;
    }

    public List<ProblemTitle> getProblemTitles() {
        return problemTitles;
    }

    public void setProblemTitles(List<ProblemTitle> problemTitles) {
        this.problemTitles = problemTitles;
    }

    public List<SolutionTitle> getSolutionTitles() {
        return solutionTitles;
    }

    public void setSolutionTitles(List<SolutionTitle> solutionTitles) {
        this.solutionTitles = solutionTitles;
    }
}
