package family.helpful.persist.message.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Channel extends BasicModel {
    @Column(columnDefinition="bigint(20) default 0", insertable = false)
    Long currentThankAmount;

    @Column(columnDefinition="bigint(20) default 0", insertable = false)
    Long currentObserverAmount;

    @JsonIgnoreProperties({"ownedChannels","channels"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnoreProperties( {"sentTransactions","receivedTransactions",
            "problemContents", "solutionContents", "problemTitles", "solutionTitles", "channels"})
    @ManyToMany(mappedBy = "channels", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private List<ProblemTitle> problemTitles = new ArrayList<>();

    @JsonIgnoreProperties("channel")
    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChannelContent> channelContents = new ArrayList<>();

    @ManyToMany(mappedBy = "channels")
    private List<SolutionTitle> solutionTitles = new ArrayList<>();

    @ManyToMany(mappedBy = "channels")
    private List<User> observers = new ArrayList<>();

    public List<ProblemTitle> getProblemTitles() {
        return problemTitles;
    }

    public void setProblemTitles(List<ProblemTitle> problemTitles) {
        this.problemTitles = problemTitles;
    }

    public Long getCurrentThankAmount() {
        return currentThankAmount;
    }

    public void setCurrentThankAmount(Long currentThankAmount) {
        this.currentThankAmount = currentThankAmount;
    }

    public List<SolutionTitle> getSolutionTitles() {
        return solutionTitles;
    }

    public void setSolutionTitles(List<SolutionTitle> solutionTitles) {
        this.solutionTitles = solutionTitles;
    }

    public List<User> getObservers() {
        return observers;
    }

    public void setObservers(List<User> observers) {
        this.observers = observers;
    }

    public Long getCurrentObserverAmount() {
        return currentObserverAmount;
    }

    public void setCurrentObserverAmount(Long currentObserverAmount) {
        this.currentObserverAmount = currentObserverAmount;
    }

    public List<ChannelContent> getChannelContents() {
        return channelContents;
    }

    public void setChannelContents(List<ChannelContent> channelContents) {
        this.channelContents = channelContents;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
