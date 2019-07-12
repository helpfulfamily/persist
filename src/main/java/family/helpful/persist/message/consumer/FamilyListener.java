package family.helpful.persist.message.consumer;


import family.helpful.persist.actions.EnumActionStatus;
import family.helpful.persist.message.model.Family;
import family.helpful.persist.message.model.User;
import family.helpful.persist.repository.FamilyRepository;
import family.helpful.persist.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;



@EnableBinding({Processor.class})
public class FamilyListener
{
    @Autowired
    private Processor processor;

    @Autowired
    FamilyRepository familyRepository;

    @Autowired
    UserRepository userRepository;



    private static final Logger logger = LoggerFactory.getLogger(FamilyListener.class);

    @StreamListener(target = Sink.INPUT,  condition = "headers['action'] == 'createFamily'")
    public void createFamily(Message<Family> familyObjectMessage) {
        logger.info("createFamily:", familyObjectMessage);

        Family familyObject= familyObjectMessage.getPayload();
        User user= familyObject.getUser();
        user= userRepository.findByUsername(user.getUsername());

        familyObject.setUser(user);

        familyObject= familyRepository.save(familyObject);


         Message resultMessage= MessageBuilder.withPayload(familyObject)
                .setHeader( "action", "createFamily")
                .setHeader( "actionStatus", EnumActionStatus.SUCCESS.name())
                .build();

        processor.output().send(resultMessage);
    }


}
