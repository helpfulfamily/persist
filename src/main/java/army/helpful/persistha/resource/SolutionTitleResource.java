package army.helpful.persistha.resource;



import army.helpful.persistha.message.model.SolutionContent;
import army.helpful.persistha.message.model.SolutionContentMessage;
import army.helpful.persistha.message.model.SolutionTitleMessage;

import army.helpful.persistha.repository.SolutionContentRepository;
import army.helpful.persistha.repository.SolutionTitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/solutiontitle")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SolutionTitleResource {

    @Autowired
    SolutionTitleRepository solutionTitleRepository;
    @Autowired
    SolutionContentRepository solutionContentRepository;

    @GetMapping(value = "/all/{amount}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public SolutionTitleMessage getAll(@PathVariable int amount) {
        Pageable pageWithAmountofElements = PageRequest.of(0, amount);

        SolutionTitleMessage titleMessage= new SolutionTitleMessage();

        titleMessage.setSolutionTitleList(solutionTitleRepository.getAllWithAmount(pageWithAmountofElements));

        return titleMessage;
    }



    @GetMapping(value = "/contents/{name}/{amount}")
    public SolutionContentMessage getContentsByTitle(@PathVariable String name, @PathVariable int amount) {
        Pageable pageWithAmountofElements = PageRequest.of(amount/10, 10);

        SolutionContentMessage contentMessage= new SolutionContentMessage();

        List<SolutionContent> contentList=  solutionContentRepository.findByTitleWithAmount(name, pageWithAmountofElements);

        contentMessage.setSolutionContentList(contentList);

        return contentMessage;
    }



}
