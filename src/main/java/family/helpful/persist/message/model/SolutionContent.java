package family.helpful.persist.message.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class SolutionContent extends BasicModel    {
    @Column(name = "text")
    private String text;

    @JsonIgnoreProperties("contents")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "problem_title_id")
    private ProblemTitle problemTitle;

    @JsonIgnoreProperties("solutionContents")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "solution_title_id")
    private SolutionTitle solutionTitle;

    @JsonIgnoreProperties( {"sentTransactions","receivedTransactions",
            "problemContents", "solutionContents", "problemTitles", "solutionTitles"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id")
    private User user;


    @Column(columnDefinition="bigint(20) default 0")
    Long currentThankAmount;


    private boolean firstContent;

    public SolutionContent() {
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public SolutionTitle getSolutionTitle() {
        return solutionTitle;
    }

    public void setSolutionTitle(SolutionTitle solutionTitle) {
        this.solutionTitle = solutionTitle;
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
