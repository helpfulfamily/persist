package army.helpful.persistha.message.consumer;


import army.helpful.persistha.message.model.*;
import army.helpful.persistha.repository.ProblemContentRepository;
import army.helpful.persistha.repository.ProblemTitleRepository;
import army.helpful.persistha.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;


@EnableBinding({Processor.class})
public class UserListener
{
    @Autowired
    private Processor processor;

    @Autowired
    ProblemTitleRepository problemTitleRepository;

    @Autowired
    ProblemContentRepository problemContentRepository;
    @Autowired
    UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserListener.class);


    @StreamListener(target = Sink.INPUT,  condition = "headers['action'] == 'changeProfilePhotoUrl'")
    public void changeProfilePhotoUrl(Message<User> message) {

        User user= message.getPayload();
        User userUpdate= userRepository.findByUsername(user.username);
        userUpdate.setProfilePhotoUrl(user.getProfilePhotoUrl());
        userRepository.save(userUpdate);
    }
    @StreamListener(target = Sink.INPUT,  condition = "headers['action'] == 'changeCoverUrl'")
    public void changeCoverUrl(Message<User> message) {

        User user= message.getPayload();
        User userUpdate= userRepository.findByUsername(user.username);
        userUpdate.setCoverUrl(user.getCoverUrl());
        userRepository.save(userUpdate);
    }

}
