package family.helpful.persist.message.consumer;


import family.helpful.persist.actions.EnumActionStatus;

import family.helpful.persist.message.model.ProblemContent;
import family.helpful.persist.message.model.ProblemTitle;

import family.helpful.persist.message.model.User;
import family.helpful.persist.repository.ProblemContentRepository;
import family.helpful.persist.repository.ProblemTitleRepository;
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
public class ProblemListener
{
    @Autowired
    private Processor processor;

    @Autowired
    ProblemTitleRepository problemTitleRepository;

    @Autowired
    ProblemContentRepository problemContentRepository;
    @Autowired
    UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProblemListener.class);

    @StreamListener(target = Sink.INPUT,  condition = "headers['action'] == 'publishProblemContent'")
    public void publishProblemContent(Message<ProblemContent> message) {
        logger.info("publishProblemContent:", message);

        ProblemContent problemContent= message.getPayload();
        User user= problemContent.getUser();
        user= userRepository.findByUsername(user.getUsername());

        problemContent.setUser(user);

        ProblemTitle title= problemTitleRepository.findByName(problemContent.getProblemTitle().getName());



        problemContent.setCurrentThankAmount(0L);

        if(title==null){

            title=problemContent.getProblemTitle();
            title.setCurrentThankAmount(0L);
            title.setUser(problemContent.getUser());
            title= problemTitleRepository.save(title);
            problemContent.setFirstContent(true);

        }else{
            problemContent.setFirstContent(false);
        }

        problemContent.setProblemTitle(title);

        problemContent= problemContentRepository.save(problemContent);

        //TODO: Websocket relation.

         Message resultMessage= MessageBuilder.withPayload(problemContent)
                .setHeader( "action", "publishProblemContent")
                .setHeader( "actionStatus", EnumActionStatus.SUCCESS.name())
                .build();

        processor.output().send(resultMessage);
    }


}