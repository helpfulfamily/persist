package army.helpful.persistha.message.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class SolutionTitle extends  BasicModel {
    @JsonIgnoreProperties("solutionTitle")
    @OneToMany(mappedBy = "solutionTitle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SolutionContent> solutionContents = new ArrayList<>();

    @JsonIgnoreProperties("solutionTitles")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable=false,columnDefinition="bigint(20) default 0")
    Long currentThankAmount;

    public Long getCurrentThankAmount() {
        return currentThankAmount;
    }

    public void setCurrentThankAmount(Long currentThankAmount) {
        this.currentThankAmount = currentThankAmount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
