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
public class Title extends BasicModel {
    @JsonIgnoreProperties("title")
    @OneToMany(mappedBy = "title", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Content> contents = new ArrayList<>();

    public Title() {
    }
    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }
}
