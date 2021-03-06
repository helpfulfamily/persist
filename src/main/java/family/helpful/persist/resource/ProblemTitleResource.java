package family.helpful.persist.resource;



import family.helpful.persist.message.model.ProblemContent;
import family.helpful.persist.message.model.ProblemContentMessage;
import family.helpful.persist.message.model.ProblemTitleMessage;
import family.helpful.persist.repository.ProblemContentRepository;
import family.helpful.persist.repository.ProblemTitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;




@RestController
@RequestMapping(value = "/problemtitle")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProblemTitleResource {

    @Autowired
    ProblemTitleRepository problemTitleRepository;
    @Autowired
    ProblemContentRepository problemContentRepository;
    @GetMapping(value = "/all/{amount}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ProblemTitleMessage getAll(@PathVariable int amount) {
        Pageable pageWithAmountofElements = PageRequest.of(0, amount);

        ProblemTitleMessage titleMessage= new ProblemTitleMessage();



            titleMessage.setProblemTitleList(problemTitleRepository.getAllWithAmount(pageWithAmountofElements));


        return titleMessage;
    }

    @GetMapping(value = "/all/{amount}/{channel}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ProblemTitleMessage getAll(@PathVariable int amount, @PathVariable String channel) {
        Pageable pageWithAmountofElements = PageRequest.of(0, amount);

        ProblemTitleMessage titleMessage= new ProblemTitleMessage();


               titleMessage.setProblemTitleList(problemTitleRepository.getAllWithAmountAndChannel(
                    pageWithAmountofElements
                    , channel));



        return titleMessage;
    }



    @GetMapping(value = "/contents/{name}/{amount}")
    public ProblemContentMessage getContentsByTitle(@PathVariable String name, @PathVariable int amount) {
        Pageable pageWithAmountofElements = PageRequest.of(amount/10, 10);

        ProblemContentMessage contentMessage= new ProblemContentMessage();
        try {
              name = java.net.URLDecoder.decode(name, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            // not going to happen - value came from JDK's own StandardCharsets
        }


        List<ProblemContent> contentList=  problemContentRepository.findByTitleWithAmount(name, pageWithAmountofElements);

        contentMessage.setProblemContentList(contentList);

        return contentMessage;
    }



}
