package family.helpful.persist.resource;

import family.helpful.persist.message.model.ProblemTitleMessage;
import family.helpful.persist.message.model.User;
import family.helpful.persist.message.model.UserMessage;
import family.helpful.persist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserResource {

    @Autowired
    UserRepository userRepository;


    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User getUser(@PathVariable String username) {
        User user = userRepository.findByUsername(username);
        return user;
    }
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void createUser(@RequestBody User user) {

        userRepository.save(user);
    }
    @GetMapping(value = "/all/{amount}/{channel}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public UserMessage getAll(@PathVariable int amount, @PathVariable String channel) {
        Pageable pageWithAmountofElements = PageRequest.of(0, amount);

        UserMessage userMessage= new UserMessage();


        userMessage.setUserList(userRepository.getAllWithAmountAndChannel(
                pageWithAmountofElements
                , channel));



        return userMessage;
    }

}
