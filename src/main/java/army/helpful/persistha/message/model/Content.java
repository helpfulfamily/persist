package army.helpful.persistha.message.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class Content extends BasicModel {
    @Column(name = "text")
    private String text;
    @JsonIgnoreProperties("contents")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "title_id")
    private Title title;


    private boolean firstContent;

    public Content() {
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
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

}
