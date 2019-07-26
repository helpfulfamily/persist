package family.helpful.persist.message.consumer;


import family.helpful.persist.actions.EnumActionStatus;
import family.helpful.persist.message.model.User;
import family.helpful.persist.message.model.UserJoinMessage;
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
public class OnlineUserListener
{
    @Autowired
    private Processor processor;




    private static final Logger logger = LoggerFactory.getLogger(OnlineUserListener.class);

    @StreamListener(target = Sink.INPUT,  condition = "headers['action'] == 'userChannelJoinPart'")
    public void userChannelJoinPart(Message<UserJoinMessage> userJoinMessageMessage) {
        logger.info("userChannelJoinPart:", userJoinMessageMessage.getPayload().getUser().username);


         Message resultMessage= MessageBuilder.withPayload(userJoinMessageMessage.getPayload())
                .setHeader( "action", "userChannelJoinPart")
                .setHeader( "actionStatus", EnumActionStatus.SUCCESS.name())
                .build();

        processor.output().send(resultMessage);
    }


}
