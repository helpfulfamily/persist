package family.helpful.persist.message.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Channel extends BasicModel {
    @Column(columnDefinition="bigint(20) default 0")
    Long currentThankAmount;

    @Column(columnDefinition="bigint(20) default 0")
    Long currentObserverAmount;

    @ManyToMany(mappedBy = "channels")
    private List<ProblemTitle> problemTitles = new ArrayList<>();

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
}
