package army.helpful.persistha.message.consumer;


import army.helpful.persistha.actions.EnumActionStatus;
import army.helpful.persistha.actions.EnumActionTypes;
import army.helpful.persistha.message.model.SolutionContent;
import army.helpful.persistha.message.model.SolutionTitle;
import army.helpful.persistha.repository.SolutionContentRepository;
import army.helpful.persistha.repository.SolutionTitleRepository;
import army.helpful.persistha.repository.UserRepository;
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
public class SolutionListener
{
    @Autowired
    private Processor processor;

    @Autowired
    SolutionTitleRepository solutionTitleRepository;

    @Autowired
    SolutionContentRepository solutionContentRepository;
    @Autowired
    UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(SolutionListener.class);

    @StreamListener(target = Sink.INPUT,  condition = "headers['action'] == 'publishSolutionContent'")
    public void publishSolutionContent(Message<SolutionContent> message) {
        logger.info("publishSolutionContent:", message);

        SolutionContent solutionContent= message.getPayload();
        SolutionTitle title= solutionTitleRepository.findByName(solutionContent.getSolutionTitle().getName());



        if(title==null){
            title=solutionContent.getSolutionTitle();

            title= solutionTitleRepository.save(title);
            solutionContent.setFirstContent(true);

        }else{
            solutionContent.setFirstContent(false);
        }

        solutionContent.setSolutionTitle(title);

        solutionContent= solutionContentRepository.save(solutionContent);

        //TODO: Websocket relation.

         Message resultMessage= MessageBuilder.withPayload(solutionContent)
                .setHeader( "action", "publishSolutionContent")
                .setHeader( "actionStatus", EnumActionStatus.SUCCESS.name())
                .build();

        processor.output().send(resultMessage);
    }
 

}
