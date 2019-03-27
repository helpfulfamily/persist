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

        Content content= message.getPayload();
        Title title= titleRepository.findByName(content.getTitle().getName());

        String description="";

        if(title==null){
            title=content.getTitle();

            title= titleRepository.save(title);
            description="success";
        }else{
            description="title_exists";
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
   /* TODO
    @StreamListener(target = Sink.INPUT,  condition = "headers['action'] == 'getAllWithAmount'")
    public void getAllWithAmount(Message<SearchCriterias> message) {
        SearchCriterias searchCriterias= message.getPayload();
        Pageable pageWithAmountofElements = PageRequest.of(0, searchCriterias.getAmount());

        TitleMessage titleMessage= new TitleMessage();

        titleMessage.setTitleList(titleRepository.getAllWithAmount(pageWithAmountofElements));


        Message resultMessage= MessageBuilder.withPayload(titleMessage)
                .setHeader( "action", EnumActionTypes.getAllWithAmount)
                .setHeader( "actionStatus", EnumActionStatus.SUCCESS.name())
                .build();

        processor.output().send(resultMessage);

    }
    @StreamListener(target = Sink.INPUT,  condition = "headers['action'] == 'findByTitleWithAmount'")
    public void findByTitleWithAmount(Message<SearchCriterias> message) {
        SearchCriterias searchCriterias= message.getPayload();
        Pageable pageWithAmountofElements = PageRequest.of(searchCriterias.getAmount()/10, 10);

        ContentMessage contentMessage= new ContentMessage();

        List<Content> contentList=  contentRepository.findByTitleWithAmount(searchCriterias.getName(), pageWithAmountofElements);

        contentMessage.setContentList(contentList);

        Message resultMessage= MessageBuilder.withPayload(contentMessage)
                .setHeader( "action", EnumActionTypes.findByTitleWithAmount)
                .setHeader( "actionStatus", EnumActionStatus.SUCCESS.name())
                .build();

        processor.output().send(resultMessage);


    }
    */

}
