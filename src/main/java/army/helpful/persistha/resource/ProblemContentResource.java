package army.helpful.persistha.resource;


import army.helpful.persistha.repository.ProblemContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/problemcontent")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProblemContentResource {

    @Autowired
    ProblemContentRepository problemContentRepository;




}
