package army.helpful.persistha.resource;



import army.helpful.persistha.actions.EnumActionTypes;
import army.helpful.persistha.message.model.Content;
import army.helpful.persistha.message.model.Title;
import army.helpful.persistha.message.model.TitleMessage;
import army.helpful.persistha.repository.ContentRepository;
import army.helpful.persistha.repository.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;




@RestController
@RequestMapping(value = "/title")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TitleResource {

    @Autowired
    TitleRepository titleRepository;
    @Autowired
    ContentRepository contentRepository;

    @GetMapping(value = "/all/{amount}")
    public TitleMessage getAll(@PathVariable int amount) {
        Pageable pageWithAmountofElements = PageRequest.of(0, amount);

        TitleMessage titleMessage= new TitleMessage();

        titleMessage.setTitleList(titleRepository.getAllWithAmount(pageWithAmountofElements));

        return titleMessage;
    }


    public TitleRepository getTitleRepository() {
        return titleRepository;
    }

    public void setTitleRepository(TitleRepository titleRepository) {
        this.titleRepository = titleRepository;
    }

    @GetMapping(value = "/contents/{name}/{amount}")
    public List<Content> getContentsByTitle(@PathVariable String name, @PathVariable int amount) {
        Pageable pageWithAmountofElements = PageRequest.of(amount/10, 10);
        List<Content> contentList=  contentRepository.findByTitleWithAmount(name, pageWithAmountofElements);
        return contentList;
    }


    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public void createTitle(@RequestBody Content content) {

        Title title= titleRepository.findByName(content.getTitle().getName());

            String description="";

            if(title==null){
                title=content.getTitle();

                title= titleRepository.save(title);
                description="success";
            }else{
                description="title_exists";
            }

            content.setTitle(title);

            content= contentRepository.save(content);


    }

}
