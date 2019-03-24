package army.helpful.persistha.message.consumer;


import army.helpful.persistha.actions.EnumActionStatus;
import army.helpful.persistha.actions.EnumActionTypes;
import army.helpful.persistha.message.model.Content;
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
public class ProsoListener
{
    @Autowired
    private Processor processor;

    private static final Logger logger = LoggerFactory.getLogger(ProsoListener.class);

    @StreamListener(target = Sink.INPUT,  condition = "headers['action'] == 'publishContent'")
    public void publishContent(Message<Content> message) {
       Message resultMessage= MessageBuilder.withPayload(message.getPayload())
                               .setHeader( "action", EnumActionTypes.publishContent)
                               .setHeader( "actionStatus", EnumActionStatus.SUCCESS.name())
                               .build();
        System.out.println("d");
        processor.output().send(resultMessage);
    }


}
