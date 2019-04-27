package family.helpful.persist.message.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import javax.persistence.*;


@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class ProblemContent extends BasicModel  {
    @Column(name = "text")
    private String text;


    @JsonIgnoreProperties("problemContents")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "solution_title_id")
    private ProblemTitle problemTitle;


    @JsonIgnoreProperties( {"sentTransactions","receivedTransactions",
            "problemContents", "solutionContents", "problemTitles", "solutionTitles"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    private boolean firstContent;

    @Column(columnDefinition="bigint(20) default 0")
    Long currentThankAmount;


    public ProblemContent() {
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ProblemTitle getProblemTitle() {
        return problemTitle;
    }

    public void setProblemTitle(ProblemTitle problemTitle) {
        this.problemTitle = problemTitle;
    }

    public boolean isFirstContent() {
        return firstContent;
    }

    public void setFirstContent(boolean firstContent) {
        this.firstContent = firstContent;
    }


    public String toString(){
     return   this.getClass().getSimpleName();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getCurrentThankAmount() {
        return currentThankAmount;
    }

    public void setCurrentThankAmount(Long currentThankAmount) {
        this.currentThankAmount = currentThankAmount;
    }
}
