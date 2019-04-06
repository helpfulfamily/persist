package army.helpful.persistha.message.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class ProblemTitle extends BasicModel {
    @JsonIgnoreProperties("problemTitle")
    @OneToMany(mappedBy = "problemTitle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProblemContent> problemContents = new ArrayList<>();
}
