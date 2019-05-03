package family.helpful.persist.resource;



import family.helpful.persist.message.model.ProblemContentMessage;
import family.helpful.persist.message.model.SolutionContent;
import family.helpful.persist.message.model.SolutionContentMessage;
import family.helpful.persist.message.model.SolutionTitleMessage;

import family.helpful.persist.repository.SolutionContentRepository;
import family.helpful.persist.repository.SolutionTitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
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

    @GetMapping(value = "/all/{amount}/{channel}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public SolutionTitleMessage getAll(@PathVariable int amount, @PathVariable String channel) {
        Pageable pageWithAmountofElements = PageRequest.of(0, amount);

        SolutionTitleMessage titleMessage= new SolutionTitleMessage();

        titleMessage.setSolutionTitleList(solutionTitleRepository.getAllWithAmountAndChannel(
                                            pageWithAmountofElements
                                            , channel));

        return titleMessage;
    }

    @GetMapping(value = "/contents/{name}/{amount}")
    public SolutionContentMessage getContentsByTitle(@PathVariable String name, @PathVariable int amount) {
        Pageable pageWithAmountofElements = PageRequest.of(amount/10, 10);

        SolutionContentMessage contentMessage= new SolutionContentMessage();

        try {
            name = java.net.URLDecoder.decode(name, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            // not going to happen - value came from JDK's own StandardCharsets
        }


        List<SolutionContent> contentList=  solutionContentRepository.findByTitleWithAmount(name, pageWithAmountofElements);

        contentMessage.setSolutionContentList(contentList);

        return contentMessage;
    }



}
