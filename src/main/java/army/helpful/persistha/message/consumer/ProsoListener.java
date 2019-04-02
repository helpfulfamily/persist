package army.helpful.persistha.message.consumer;


import army.helpful.persistha.actions.EnumActionStatus;
import army.helpful.persistha.actions.EnumActionTypes;
import army.helpful.persistha.message.model.*;
import army.helpful.persistha.repository.ContentRepository;
import army.helpful.persistha.repository.TitleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import java.util.List;


@EnableBinding({Processor.class})
public class ProsoListener
{
    @Autowired
    private Processor processor;

    @Autowired
    TitleRepository titleRepository;

    @Autowired
    ContentRepository contentRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProsoListener.class);

    @StreamListener(target = Sink.INPUT,  condition = "headers['action'] == 'publishContent'")
    public void publishContent(Message<Content> message) {
        logger.info("publishContent:", message);

        Content content= message.getPayload();
        Title title= titleRepository.findByName(content.getTitle().getName());



        if(title==null){
            title=content.getTitle();

            title= titleRepository.save(title);
            content.setFirstContent(true);

        }else{
            content.setFirstContent(false);
        }

        content.setTitle(title);

        content= contentRepository.save(content);


        //TODO: Websocket relation.

         Message resultMessage= MessageBuilder.withPayload(content)
                .setHeader( "action", EnumActionTypes.publishContent)
                .setHeader( "actionStatus", EnumActionStatus.SUCCESS.name())
                .build();

        processor.output().send(resultMessage);
    }


}
