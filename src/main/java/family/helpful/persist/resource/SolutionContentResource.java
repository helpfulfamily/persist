package family.helpful.persist.resource;


import family.helpful.persist.repository.SolutionContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/content")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SolutionContentResource {

    @Autowired
    SolutionContentRepository solutionContentRepository;




}
