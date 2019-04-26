package army.helpful.persistha.message.consumer;


import army.helpful.persistha.actions.EnumActionStatus;

import army.helpful.persistha.message.model.ProblemContent;
import army.helpful.persistha.message.model.ProblemTitle;

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
        ProblemTitle title= problemTitleRepository.findByName(problemContent.getProblemTitle().getName());



        if(title==null){
            title=problemContent.getProblemTitle();

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
