package family.helpful.persist.message.consumer;


import family.helpful.persist.actions.EnumActionStatus;
import family.helpful.persist.message.ChannelUtil;
import family.helpful.persist.message.model.*;
import family.helpful.persist.repository.*;
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
public class DialogListener
{
    @Autowired
    private Processor processor;


    @Autowired
    DialogContentRepository dialogContentRepository;

    @Autowired
    UserRepository userRepository;


    private static final Logger logger = LoggerFactory.getLogger(DialogListener.class);

    @StreamListener(target = Sink.INPUT,  condition = "headers['action'] == 'PUBLISH_DIALOG_CONTENT'")
    public void publishContent(Message<DialogContent> message) {
        logger.info("PUBLISH_DIALOG_CONTENT:", message);

        DialogContent dialogContent= message.getPayload();
        User sender= dialogContent.getSender();
        sender= userRepository.findByUsername(sender.getUsername());

        dialogContent.setSender(sender);


        dialogContent.setCurrentThankAmount(0L);

        dialogContent= dialogContentRepository.save(dialogContent);

        //TODO: Websocket relation.

         Message resultMessage= MessageBuilder.withPayload(dialogContent)
                .setHeader( "action", "PUBLISH_DIALOG_CONTENT")
                .setHeader( "actionStatus", EnumActionStatus.SUCCESS.name())
                .build();

        processor.output().send(resultMessage);
    }


}
