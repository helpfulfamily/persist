package family.helpful.persist.message.model;

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

    @Column(columnDefinition="bigint(20) default 0", insertable = false)
    Long currentThankAmount;

    @ManyToMany(cascade = {
            CascadeType.REFRESH

    })
    @JoinTable(name = "st_tag",
            joinColumns = @JoinColumn(name = "st_id"),
            inverseJoinColumns = @JoinColumn(name = "channel_id")
    )
    private List<Channel> channels = new ArrayList<>();

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

    public List<Channel> getChannels() {
        return channels;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }
}
