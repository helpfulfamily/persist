package army.helpful.persistha.resource;

import army.helpful.persistha.message.model.User;
import army.helpful.persistha.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping(value = "/user")
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
}
