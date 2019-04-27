package family.helpful.persist.message.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class ProblemTitle extends BasicModel {
    @JsonIgnoreProperties("problemTitle")
    @OneToMany(mappedBy = "problemTitle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProblemContent> problemContents = new ArrayList<>();

    @JsonIgnoreProperties("problemTitles")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(columnDefinition="bigint(20) default 0")
    Long currentThankAmount;

    public Long getCurrentThankAmount() {
        return currentThankAmount;
    }

    public void setCurrentThankAmount(Long currentThankAmount) {
        this.currentThankAmount = currentThankAmount;
    }

    public List<ProblemContent> getProblemContents() {
        return problemContents;
    }

    public void setProblemContents(List<ProblemContent> problemContents) {
        this.problemContents = problemContents;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
