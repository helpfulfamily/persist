package army.helpful.persistha.message.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class SolutionContent extends BasicModel {
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

    @JsonIgnoreProperties("solutionContents")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;


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
}
